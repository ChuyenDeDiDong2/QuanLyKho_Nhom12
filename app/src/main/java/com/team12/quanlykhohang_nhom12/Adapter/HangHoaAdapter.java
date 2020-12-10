package com.team12.quanlykhohang_nhom12.Adapter;

public class HangHoaAdapter {
    private String sTenHang, sDonVi, sSoLuong;

    public HangHoaAdapter() {
    }

    public HangHoaAdapter(String sTenHang, String sDonVi, String sSoLuong) {
        this.sTenHang = sTenHang;
        this.sDonVi = sDonVi;
        this.sSoLuong = sSoLuong;
    }

    public String getsTenHang() {
        return sTenHang;
    }

    public void setsTenHang(String sTenHang) {
        this.sTenHang = sTenHang;
    }

    public String getsDonVi() {
        return sDonVi;
    }

    public void setsDonVi(String sDonVi) {
        this.sDonVi = sDonVi;
    }

    public String getsSoLuong() {
        return sSoLuong;
    }

    public void setsSoLuong(String sSoLuong) {
        this.sSoLuong = sSoLuong;
    }

    @Override
    public String toString() {
        return "HangHoaAdapter{" +
                "sTenHang='" + sTenHang + '\'' +
                ", sDonVi='" + sDonVi + '\'' +
                ", sSoLuong='" + sSoLuong + '\'' +
                '}';
    }
}

