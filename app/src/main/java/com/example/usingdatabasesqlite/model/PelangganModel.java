package com.example.usingdatabasesqlite.model;

public class PelangganModel {
    private String _id;
    private String _nama;
    private String _jenis;

    public PelangganModel() {}

    public PelangganModel(String id, String nama, String jenis) {
        this._id = id;
        this._nama = nama;
        this._jenis = jenis;
    }

    public String getId() {
        return _id;
    }
    public void setId(String id) {
        this._id = id;
    }
    public String getNama() {
        return _nama;
    }
    public void setNama(String nama) {
        this._nama = nama;
    }
    public String getJenis() {
        return _jenis;
    }
    public void setJenis(String jenis) {
        this._jenis = jenis;
    }
}
