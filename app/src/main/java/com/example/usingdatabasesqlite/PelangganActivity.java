package com.example.usingdatabasesqlite;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.usingdatabasesqlite.services.PelangganServices;
import com.google.android.material.textfield.TextInputEditText;

public class PelangganActivity extends AppCompatActivity {

    private TextInputEditText tieId, tieNama;
    private AutoCompleteTextView tieJenis;
    private Button btnSimpan;
    private PelangganServices db=new PelangganServices(this);

    String id, nama, jenis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pelanggan);

        tieId = findViewById(R.id.tie_id);
        tieNama = findViewById(R.id.tie_nama);
        tieJenis = findViewById(R.id.tie_jenis);
        btnSimpan = findViewById(R.id.btn_simpan);

        id = getIntent().getStringExtra("id");
        nama = getIntent().getStringExtra("nama");
        jenis = getIntent().getStringExtra("jenis");

        isiJenis();

        if(id == null || id.equals("")) {
            setTitle("Tambah Data");
            tieId.requestFocus();
        } else {
            setTitle("Ubah Data");
            tieId.setText(id);
            tieNama.setText(nama);
            tieJenis.setText(jenis);
        }

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id == null || id.equals("")) {
                    tambahData();
                } else {
                    ubahData(id);
                }
            }
        });
    }

    public void tambahData() {
        if (tieId.getText().toString().equals("") || tieNama.getText().toString().equals("") || tieJenis.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Data tidak boleh kosong!", Toast.LENGTH_SHORT).show();
        } else {
            if(!db.isExists(tieId.getText().toString())) {
                db.insert(tieId.getText().toString(), tieNama.getText().toString(), tieJenis.getText().toString());
                Toast.makeText(getApplicationContext(), "Data berhasil disimpan!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "ID Pelanggan sudah terdaftar!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void ubahData(String id) {
        if (tieId.getText().toString().equals("") || tieNama.getText().toString().equals("") || tieJenis.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Data tidak boleh kosong!", Toast.LENGTH_SHORT).show();
        } else {
            if (db.isExists(id)) {
                db.update(id, tieNama.getText().toString(), tieJenis.getText().toString());
                Toast.makeText(getApplicationContext(), "Data berhasil diubah!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "ID Pelanggan tidak terdaftar!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void isiJenis() {
        String[] jenis = new String[] {"PLN", "BPJS", "PDAM", "TELKOM", "TV Kabel", "Internet", "Asuransi", "Lainnya"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, jenis);
        tieJenis.setAdapter(adapter);
    }
}