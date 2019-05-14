package com.example.lifemanagement;

public class Chitieu {
    private String ngayghi;
    private String loaichitieu;
    private int giatri;
    private String tenchitieu;
    private String ghichu;
    private String trangthai;

    public Chitieu(){};

    public Chitieu(String ngayghi, String loaichitieu, int giatri, String tenchitieu, String ghichu, String trangthai) {
        this.ngayghi = ngayghi;
        this.loaichitieu = loaichitieu;
        this.giatri = giatri;
        this.tenchitieu = tenchitieu;
        this.ghichu = ghichu;
        this.trangthai = trangthai;
    }

    public String getNgayghi() {
        return ngayghi;
    }

    public void setNgayghi(String ngayghi) {
        this.ngayghi = ngayghi;
    }

    public String getLoaichitieu() {
        return loaichitieu;
    }

    public void setLoaichitieu(String loaichitieu) {
        this.loaichitieu = loaichitieu;
    }

    public int getGiatri() {
        return giatri;
    }

    public void setGiatri(int giatri) {
        this.giatri = giatri;
    }

    public String getTenchitieu() {
        return tenchitieu;
    }

    public void setTenchitieu(String tenchitieu) {
        this.tenchitieu = tenchitieu;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }
}
