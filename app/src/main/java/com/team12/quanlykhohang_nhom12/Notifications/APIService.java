package com.team12.quanlykhohang_nhom12.Notifications;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AAAA5wLOPS4:APA91bFrRxl5-TKKqQtt36Q9xsxE68Zehdol5v9Rn04zPfSoAOSAmrFc0az5dLDt4Qunfey58F_TmDxkGLbxDVIBmL6xZts3xTA7_--zmOWrHYdrROwJpGD51ryQLxxn07iNv70cQ1Sh"
    })
    @POST("fcm/send")
    Call<Response> sendNotification(@Body Sender body);
}
