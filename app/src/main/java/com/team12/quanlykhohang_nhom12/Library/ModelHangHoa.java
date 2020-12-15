package com.team12.quanlykhohang_nhom12.Library;

public class ModelHangHoa {
    private String hanghoaId;
    private String loaihang,xuatxu,soluong,hinhanhhang,uid;

    public ModelHangHoa(String hanghoaId, String loaihang, String xuatxu, String soluong, String hinhanhhang, String uid) {
        this.hanghoaId = hanghoaId;
        this.loaihang = loaihang;
        this.xuatxu = xuatxu;
        this.soluong = soluong;
        this.hinhanhhang = hinhanhhang;
        this.uid = uid;
    }

    public String getHanghoaId() {
        return hanghoaId;
    }

    public void setHanghoaId(String hanghoaId) {
        this.hanghoaId = hanghoaId;
    }

    public String getLoaihang() {
        return loaihang;
    }

    public void setLoaihang(String loaihang) {
        this.loaihang = loaihang;
    }

    public String getXuatxu() {
        return xuatxu;
    }

    public void setXuatxu(String xuatxu) {
        this.xuatxu = xuatxu;
    }

    public String getSoluong() {
        return soluong;
    }

    public void setSoluong(String soluong) {
        this.soluong = soluong;
    }

    public String getHinhanhhang() {
        return hinhanhhang;
    }

    public void setHinhanhhang(String hinhanhhang) {
        this.hinhanhhang = hinhanhhang;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
