package com.team12.quanlykhohang_nhom12.Library;

public class Users {
    private String Email, SoDT, DiaChi, Type, SoTK, Ten;

    public Users() {
    }

    public Users(String email, String soDT, String diaChi, String type, String soTK, String ten) {
        Email = email;
        SoDT = soDT;
        DiaChi = diaChi;
        Type = type;
        SoTK = soTK;
        Ten = ten;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getSoDT() {
        return SoDT;
    }

    public void setSoDT(String soDT) {
        SoDT = soDT;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getSoTK() {
        return SoTK;
    }

    public void setSoTK(String soTK) {
        SoTK = soTK;
    }

    public String getTen() {
        return Ten;
    }

    public void setTen(String ten) {
        Ten = ten;
    }
}
