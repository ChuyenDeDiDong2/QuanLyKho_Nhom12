package com.team12.quanlykhohang_nhom12.Library;

public class MauHopDong {
    private String sTenMauHD, sDieuKhoan, sDonVi, sDonGia;

    public MauHopDong() {
    }

    public MauHopDong(String sTenMauHD, String sDieuKhoan, String sDonVi, String sDonGia) {
        this.sTenMauHD = sTenMauHD;
        this.sDieuKhoan = sDieuKhoan;
        this.sDonVi = sDonVi;
        this.sDonGia = sDonGia;
    }

    public String getsTenMauHD() {
        return sTenMauHD;
    }

    public void setsTenMauHD(String sTenMauHD) {
        this.sTenMauHD = sTenMauHD;
    }

    public String getsDieuKhoan() {
        return sDieuKhoan;
    }

    public void setsDieuKhoan(String sDieuKhoan) {
        this.sDieuKhoan = sDieuKhoan;
    }

    public String getsDonVi() {
        return sDonVi;
    }

    public void setsDonVi(String sDonVi) {
        this.sDonVi = sDonVi;
    }

    public String getsDonGia() {
        return sDonGia;
    }

    public void setsDonGia(String sDonGia) {
        this.sDonGia = sDonGia;
    }
}
