package com.team12.quanlykhohang_nhom12.Adapter;

public class KhoAdapter {
    private String tenkho;
    private String sodt;
    private String email;
    private String succhua;
    private String diachi;

    public KhoAdapter(String tenkho, String sodt, String email, String succhua, String diachi) {
        this.tenkho = tenkho;
        this.sodt = sodt;
        this.email = email;
        this.succhua = succhua;
        this.diachi = diachi;
    }

    public KhoAdapter() {
    }

    public String getTenkho() {
        return tenkho;
    }

    public void setTenkho(String tenkho) {
        this.tenkho = tenkho;
    }

    public String getSodt() {
        return sodt;
    }

    public void setSodt(String sodt) {
        this.sodt = sodt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSucchua() {
        return succhua;
    }

    public void setSucchua(String succhua) {
        this.succhua = succhua;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }
}
