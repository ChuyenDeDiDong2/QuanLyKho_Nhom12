package com.team12.quanlykhohang_nhom12.Library;

public class KhoHang {
    private String sDiaChi, sSDTKho, sDienTich, sTenKho;
    private String bHinhAnhKho;

    public KhoHang() {
    }

    public KhoHang(String sDiaChi, String sSDTKho, String sDienTich, String sTenKho, String  bHinhAnhKho) {
        this.sDiaChi = sDiaChi;
        this.sSDTKho = sSDTKho;
        this.sDienTich = sDienTich;
        this.sTenKho = sTenKho;
        this.bHinhAnhKho = bHinhAnhKho;
    }

    public String getsDiaChi() {
        return sDiaChi;
    }

    public void setsDiaChi(String sDiaChi) {
        this.sDiaChi = sDiaChi;
    }

    public String getsSDTKho() {
        return sSDTKho;
    }

    public void setsSDTKho(String sSDTKho) {
        this.sSDTKho = sSDTKho;
    }

    public String getsDienTich() {
        return sDienTich;
    }

    public void setsDienTich(String sDienTich) {
        this.sDienTich = sDienTich;
    }

    public String getsTenKho() {
        return sTenKho;
    }

    public void setsTenKho(String sTenKho) {
        this.sTenKho = sTenKho;
    }

    public String isbHinhAnhKho() {
        return bHinhAnhKho;
    }

    public void setbHinhAnhKho(String bHinhAnhKho) {
        this.bHinhAnhKho = bHinhAnhKho;
    }
}
