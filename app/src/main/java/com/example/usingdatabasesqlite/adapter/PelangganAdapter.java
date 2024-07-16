package com.example.usingdatabasesqlite.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.usingdatabasesqlite.R;
import com.example.usingdatabasesqlite.model.PelangganModel;

import java.util.List;

public class PelangganAdapter extends BaseAdapter {
    Activity activity;
    LayoutInflater inflater;
    List<PelangganModel> pelanggans;

    public PelangganAdapter(Activity activity, List<PelangganModel> pelanggans) {
        this.activity = activity;
        this.pelanggans = pelanggans;
    }

    @Override
    public int getCount() {
        return pelanggans.size();
    }

    @Override
    public Object getItem(int position) {
        return pelanggans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(inflater == null) {
            inflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
        }
        if(convertView == null && inflater != null) {
            convertView = inflater.inflate(R.layout.list_pelanggan, null);
        }
        if(convertView != null) {
            TextView tvId = convertView.findViewById(R.id.tv_id);
            TextView tvNama = convertView.findViewById(R.id.tv_nama);
            TextView tvJenis = convertView.findViewById(R.id.tv_jenis);

            PelangganModel pelanggan = pelanggans.get(position);
            tvId.setText(pelanggan.getId());
            tvNama.setText(pelanggan.getNama());
            tvJenis.setText(pelanggan.getJenis());
        }

        return convertView;
    }
}
