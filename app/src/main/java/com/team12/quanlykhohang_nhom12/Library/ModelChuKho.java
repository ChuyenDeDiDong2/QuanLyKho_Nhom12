package com.team12.quanlykhohang_nhom12.Library;

public class ModelChuKho {
    private String uid, name, phone, email, sotaikhoan, tentaikhoan, diachi, tinhthanh, accountType, online, open, noibat, profileImage, typingTo;

    public ModelChuKho() {
    }

    public ModelChuKho(String uid, String name, String phone, String email, String sotaikhoan, String tentaikhoan, String diachi, String tinhthanh, String accountType, String online, String open, String noibat, String profileImage, String typingTo) {
        this.uid = uid;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.sotaikhoan = sotaikhoan;
        this.tentaikhoan = tentaikhoan;
        this.diachi = diachi;
        this.tinhthanh = tinhthanh;
        this.accountType = accountType;
        this.online = online;
        this.open = open;
        this.noibat = noibat;
        this.profileImage = profileImage;
        this.typingTo = typingTo;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSotaikhoan() {
        return sotaikhoan;
    }

    public void setSotaikhoan(String sotaikhoan) {
        this.sotaikhoan = sotaikhoan;
    }

    public String getTentaikhoan() {
        return tentaikhoan;
    }

    public void setTentaikhoan(String tentaikhoan) {
        this.tentaikhoan = tentaikhoan;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getTinhthanh() {
        return tinhthanh;
    }

    public void setTinhthanh(String tinhthanh) {
        this.tinhthanh = tinhthanh;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getNoibat() {
        return noibat;
    }

    public void setNoibat(String noibat) {
        this.noibat = noibat;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getTypingTo() {
        return typingTo;
    }

    public void setTypingTo(String typingTo) {
        this.typingTo = typingTo;
    }
}
