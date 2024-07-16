package com.example.usingdatabasesqlite;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.usingdatabasesqlite.adapter.PelangganAdapter;
import com.example.usingdatabasesqlite.model.PelangganModel;
import com.example.usingdatabasesqlite.services.PelangganServices;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PelangganListActivity extends AppCompatActivity {

    ListView listView;
    AlertDialog.Builder dialog;
    List<PelangganModel> pelanggans = new ArrayList<>();
    PelangganAdapter pelangganAdapter;
    PelangganServices db=new PelangganServices(this);
    Button btnTambah;
    TextInputEditText tieCari;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.pelanggan_list);

        listView = findViewById(R.id.lv_pelanggan);
        btnTambah = findViewById(R.id.btn_tambah);
        tieCari = findViewById(R.id.tie_cari);

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pelanggan = new Intent(PelangganListActivity.this, PelangganActivity.class);
                startActivity(pelanggan);
            }
        });

        tieCari.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                getData(tieCari.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        pelangganAdapter = new PelangganAdapter(PelangganListActivity.this, pelanggans);
        listView.setDivider(null);
        listView.setAdapter(pelangganAdapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final String idx = pelanggans.get(position).getId();
                final String nama = pelanggans.get(position).getNama();
                final String jenis = pelanggans.get(position).getJenis();

                final CharSequence[] dialogitem = {"Edit", "Delete", "Copy"};
                dialog = new AlertDialog.Builder(PelangganListActivity.this);
                dialog.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Intent pelanggan = new Intent(PelangganListActivity.this, PelangganActivity.class);
                                pelanggan.putExtra("id", idx);
                                pelanggan.putExtra("nama", nama);
                                pelanggan.putExtra("jenis", jenis);
                                startActivity(pelanggan);
                                break;
                            case 1:
                                confirmHapus(idx);
//                                getData("");
                                break;
                            case 2:
                                copyToClipboard(idx);
//                                getData("");
                                tieCari.setText("");
                                break;
                        }
                    }
                }).show();
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        pelanggans.clear();

        if(tieCari.getText().toString().isEmpty()) {
            getData();
        } else {
            getData(tieCari.getText().toString());
        }
    }

    public void getData() {
        ArrayList<HashMap<String, String>> listData = db.getAll();

        pelanggans.clear();
        for (int i = 0; i < listData.size(); i++) {
            String id = listData.get(i).get("id");
            String nama = listData.get(i).get("nama");
            String jenis = listData.get(i).get("jenis");

            PelangganModel pelanggan = new PelangganModel();
            pelanggan.setId(id);
            pelanggan.setNama(nama);
            pelanggan.setJenis(jenis);
            pelanggans.add(pelanggan);
        }

        pelangganAdapter.notifyDataSetChanged();
    }

    public void getData(String cari) {
        ArrayList<HashMap<String, String>> listData = db.getByNama(cari);

        pelanggans.clear();
        for (int i = 0; i < listData.size(); i++) {
            PelangganModel pelanggan = new PelangganModel();
            pelanggan.setId(listData.get(i).get("id"));
            pelanggan.setNama(listData.get(i).get("nama"));
            pelanggan.setJenis(listData.get(i).get("jenis"));
            pelanggans.add(pelanggan);
        }

        pelangganAdapter.notifyDataSetChanged();
    }

    private void copyToClipboard(String idx) {
        String getId = idx;
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipdata = ClipData.newPlainText("copy data", getId);
        clipboard.setPrimaryClip(clipdata);
        Toast.makeText(getApplicationContext(), "ID copied to clipboard", Toast.LENGTH_SHORT).show();
    }

    private void confirmHapus(String idx) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Konfirmasi Hapus")
                .setMessage("Apakah Anda yakin ingin menghapus data ini?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.delete(idx);
                        pelanggans.clear();
                        getData();
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setCancelable(false).show();
    }
}