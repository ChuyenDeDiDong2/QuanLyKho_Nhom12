package com.team12.quanlykhohang_nhom12.Library;

public class ModelSlider {
    private String hinhanh, link, tieude, uid;

    public ModelSlider() {
    }

    public ModelSlider(String hinhanh, String link, String tieude, String uid) {
        this.hinhanh = hinhanh;
        this.link = link;
        this.tieude = tieude;
        this.uid = uid;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTieude() {
        return tieude;
    }

    public void setTieude(String tieude) {
        this.tieude = tieude;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
