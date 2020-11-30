package com.team12.quanlykhohang_nhom12.Adapter;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

public class UserAdapter {

    public String tenDN, email, ten, diachi, sotk;

    public UserAdapter() {

    }

    public UserAdapter(String tenDN, String email, String ten, String diachi, String sotk) {
        this.tenDN = tenDN;
        this.email = email;
        this.ten = ten;
        this.diachi = diachi;
        this.sotk = sotk;
    }
}
