package com.team12.quanlykhohang_nhom12.Notifications;

public class Response {
    private Data data;
    private String to;

    public Response() {
    }

    public Response(Data data, String to) {
        this.data = data;
        this.to = to;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
