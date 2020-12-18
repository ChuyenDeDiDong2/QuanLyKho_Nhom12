package com.team12.quanlykhohang_nhom12.Library;

public class ModelUser {
    private String uid, name,phone ,email, sotaikhoan, tentaikhoan, diachi, accountType, online, open, noibat, profileImage, typingTo;

    public ModelUser() {
    }

    public ModelUser(String uid, String name, String phone, String email, String sotaikhoan, String tentaikhoan, String dichi, String accountType, String online, String open, String noibat, String profileImage, String typingTo) {
        this.uid = uid;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.sotaikhoan = sotaikhoan;
        this.tentaikhoan = tentaikhoan;
        this.diachi = dichi;
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

    public String getDichi() {
        return diachi;
    }

    public void setDichi(String dichi) {
        this.diachi = dichi;
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
