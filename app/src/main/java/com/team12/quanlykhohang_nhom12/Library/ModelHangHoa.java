package com.team12.quanlykhohang_nhom12.Library;

public class ModelHangHoa {
    private String hanghoaId, timstamphh, uid, tensanpham, donvi, hinhanhhang, ghichu, dongia, soluong;
    //private int dongia, soluong;
    public ModelHangHoa() {
    }

    public ModelHangHoa(String hanghoaId, String timstamphh, String uid, String tensanpham, String donvi, String hinhanhhang, String ghichu, String dongia, String soluong) {
        this.hanghoaId = hanghoaId;
        this.timstamphh = timstamphh;
        this.uid = uid;
        this.tensanpham = tensanpham;
        this.donvi = donvi;
        this.hinhanhhang = hinhanhhang;
        this.ghichu = ghichu;
        this.dongia = dongia;
        this.soluong = soluong;
    }

    public String getHanghoaId() {
        return hanghoaId;
    }

    public void setHanghoaId(String hanghoaId) {
        this.hanghoaId = hanghoaId;
    }

    public String getTimstamphh() {
        return timstamphh;
    }

    public void setTimstamphh(String timstamphh) {
        this.timstamphh = timstamphh;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTensanpham() {
        return tensanpham;
    }

    public void setTensanpham(String tensanpham) {
        this.tensanpham = tensanpham;
    }

    public String getDonvi() {
        return donvi;
    }

    public void setDonvi(String donvi) {
        this.donvi = donvi;
    }

    public String getHinhanhhang() {
        return hinhanhhang;
    }

    public void setHinhanhhang(String hinhanhhang) {
        this.hinhanhhang = hinhanhhang;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

    public String getDongia() {
        return dongia;
    }

    public void setDongia(String dongia) {
        this.dongia = dongia;
    }

    public String getSoluong() {
        return soluong;
    }

    public void setSoluong(String soluong) {
        this.soluong = soluong;
    }
}
