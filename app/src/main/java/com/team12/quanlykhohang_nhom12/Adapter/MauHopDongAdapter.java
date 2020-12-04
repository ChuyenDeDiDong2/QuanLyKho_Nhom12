package com.team12.quanlykhohang_nhom12.Adapter;

public class MauHopDongAdapter {
    private String tenmauhd;
    private String dieukhoan;
    private String donvi;
    private String dongia;

    public MauHopDongAdapter(String dieuKhoan, String kho, String donvi, String gia, String thoiHan) {
    }

    public MauHopDongAdapter() {
        this.tenmauhd = tenmauhd;
        this.dieukhoan = dieukhoan;
        this.donvi = donvi;
        this.dongia = dongia;
    }

    public MauHopDongAdapter(String tenmauhd, String dieukhoan, String donvi, String dongia) {
    }

    public String getTenmauhd() {
        return tenmauhd;
    }

    public void setTenmauhd(String tenmauhd) {
        this.tenmauhd = tenmauhd;
    }

    public String getDieukhoan() {
        return dieukhoan;
    }

    public void setDieukhoan(String dieukhoan) {
        this.dieukhoan = dieukhoan;
    }

    public String getDonvi() {
        return donvi;
    }

    public void setDonvi(String donvi) {
        this.donvi = donvi;
    }

    public String getDongia() {
        return dongia;
    }

    public void setDongia(String dongia) {
        this.dongia = dongia;
    }
}
