package com.team12.quanlykhohang_nhom12.Library;

public class ModelToCao {
    private String noidungtocao, tennguoigui, hangkho, timestamp, hisUid, myUid, uid;

    public ModelToCao() {
    }

    public ModelToCao(String noidungtocao, String tennguoigui, String hangkho, String timestamp, String hisUid, String myUid, String uid) {
        this.noidungtocao = noidungtocao;
        this.tennguoigui = tennguoigui;
        this.hangkho = hangkho;
        this.timestamp = timestamp;
        this.hisUid = hisUid;
        this.myUid = myUid;
        this.uid = uid;
    }

    public String getNoidungtocao() {
        return noidungtocao;
    }

    public void setNoidungtocao(String noidungtocao) {
        this.noidungtocao = noidungtocao;
    }

    public String getTennguoigui() {
        return tennguoigui;
    }

    public void setTennguoigui(String tennguoigui) {
        this.tennguoigui = tennguoigui;
    }

    public String getHangkho() {
        return hangkho;
    }

    public void setHangkho(String hangkho) {
        this.hangkho = hangkho;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getHisUid() {
        return hisUid;
    }

    public void setHisUid(String hisUid) {
        this.hisUid = hisUid;
    }

    public String getMyUid() {
        return myUid;
    }

    public void setMyUid(String myUid) {
        this.myUid = myUid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
