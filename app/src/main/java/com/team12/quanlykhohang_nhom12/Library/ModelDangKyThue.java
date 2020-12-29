package com.team12.quanlykhohang_nhom12.Library;

public class ModelDangKyThue {
    private String khohangId, tenthue,diachi, email, tenkho,sodienthoai, tongtien, dientichthue, thoigianthue, thongbaothue, timstamp, uid, hisUid;

    public ModelDangKyThue() {
    }

    public ModelDangKyThue(String khohangId, String tenthue, String diachi, String email, String tenkho, String sodienthoai, String tongtien, String dientichthue, String thoigianthue, String thongbaothue, String timstamp, String uid, String hisUid) {
        this.khohangId = khohangId;
        this.tenthue = tenthue;
        this.diachi = diachi;
        this.email = email;
        this.tenkho = tenkho;
        this.sodienthoai = sodienthoai;
        this.tongtien = tongtien;
        this.dientichthue = dientichthue;
        this.thoigianthue = thoigianthue;
        this.thongbaothue = thongbaothue;
        this.timstamp = timstamp;
        this.uid = uid;
        this.hisUid = hisUid;
    }

    public String getKhohangId() {
        return khohangId;
    }

    public void setKhohangId(String khohangId) {
        this.khohangId = khohangId;
    }

    public String getTenthue() {
        return tenthue;
    }

    public void setTenthue(String tenthue) {
        this.tenthue = tenthue;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTenkho() {
        return tenkho;
    }

    public void setTenkho(String tenkho) {
        this.tenkho = tenkho;
    }

    public String getSodienthoai() {
        return sodienthoai;
    }

    public void setSodienthoai(String sodienthoai) {
        this.sodienthoai = sodienthoai;
    }

    public String getTongtien() {
        return tongtien;
    }

    public void setTongtien(String tongtien) {
        this.tongtien = tongtien;
    }

    public String getDientichthue() {
        return dientichthue;
    }

    public void setDientichthue(String dientichthue) {
        this.dientichthue = dientichthue;
    }

    public String getThoigianthue() {
        return thoigianthue;
    }

    public void setThoigianthue(String thoigianthue) {
        this.thoigianthue = thoigianthue;
    }

    public String getThongbaothue() {
        return thongbaothue;
    }

    public void setThongbaothue(String thongbaothue) {
        this.thongbaothue = thongbaothue;
    }

    public String getTimstamp() {
        return timstamp;
    }

    public void setTimstamp(String timstamp) {
        this.timstamp = timstamp;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getHisUid() {
        return hisUid;
    }

    public void setHisUid(String hisUid) {
        this.hisUid = hisUid;
    }
}
