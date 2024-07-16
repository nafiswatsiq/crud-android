package com.example.usingdatabasesqlite.services;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class PelangganServices extends SQLiteOpenHelper {
    public PelangganServices(Context context) {
        super(context, Services.NAMA_DATABASE, null, Services.VERSI_DATABASE);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlPelanggan = "CREATE TABLE " + Services.TABEL_PELANGGAN + " (id VARCHAR(10), nama TEXT, jenis TEXT)";
        db.execSQL(sqlPelanggan);

//        String sqlJenis = "CREATE TABLE " + Services.TABEL_JENIS + " (id INTEGER PRIMARY KEY AUTOINCREMENT, nama_rek TEXT)";
//        db.execSQL(sqlJenis);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Services.TABEL_PELANGGAN);
//        db.execSQL("DROP TABLE IF EXISTS " + Services.TABEL_JENIS);
        onCreate(db);
    }

    public void insert(String id, String nama, String jenis) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO " + Services.TABEL_PELANGGAN + " (id, nama, jenis) VALUES ('" + id + "', '" + nama + "', '" + jenis + "')");
//        db.close();
    }

    public void update(String id, String nama, String jenis) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE " + Services.TABEL_PELANGGAN + " SET nama = '" + nama + "', jenis = '" + jenis + "' WHERE id = '" + id + "'");
//        db.close();
    }

    public void delete(String id) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + Services.TABEL_PELANGGAN + " WHERE id = '" + id + "'");
//        db.close();
    }

    public ArrayList<HashMap<String, String>> getAll() {
        ArrayList<HashMap<String, String>> data = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        String sql = "SELECT * FROM " + Services.TABEL_PELANGGAN;
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<>();
                map.put("id", cursor.getString(0));
                map.put("nama", cursor.getString(1));
                map.put("jenis", cursor.getString(2));
                data.add(map);
            } while (cursor.moveToNext());
        }
        cursor.close();
//        db.close();
        return data;
    }

    public ArrayList<HashMap<String, String>> getByNama(String nama) {
        ArrayList<HashMap<String,String>> data = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        String sql = "SELECT * FROM " + Services.TABEL_PELANGGAN + " WHERE nama LIKE '%" + nama + "%'";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<>();
                map.put("id", cursor.getString(0));
                map.put("nama", cursor.getString(1));
                map.put("jenis", cursor.getString(2));
                data.add(map);
            } while (cursor.moveToNext());
        }
        cursor.close();
//        db.close();
        return data;
    }

    public  boolean isExists(String id) {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM " + Services.TABEL_PELANGGAN + " WHERE id = '" + id + "'";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.getCount() > 0) {
            cursor.close();
//            db.close();
            return true;
        } else {
            cursor.close();
//            db.close();
            return false;
        }
    }
}
