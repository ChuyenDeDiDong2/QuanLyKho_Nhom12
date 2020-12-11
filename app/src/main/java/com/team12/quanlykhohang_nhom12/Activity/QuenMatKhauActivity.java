package com.team12.quanlykhohang_nhom12.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.team12.quanlykhohang_nhom12.R;

public class QuenMatKhauActivity extends AppCompatActivity {
    private ImageView btnBack, ivprofile;
    private Button btnquenmatkhau;
    private EditText txtEmail;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quen_mat_khau);
        getControl();
        getEvent();
    }
    private void getEvent() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //su kien quay lai
                onBackPressed();
            }
        });
        btnquenmatkhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //su kien khi nhan vao nut dang ky
                recoverPassword();

            }
        });

    }
    private String email;
    private void recoverPassword() {
        email = txtEmail.getText().toString().trim();
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Gửi hướng dẫn để đặt lại mật khẩu...");
        progressDialog.show();

        firebaseAuth.sendPasswordResetEmail(email)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //
                        progressDialog.dismiss();
                        Toast.makeText(QuenMatKhauActivity.this, "\n" +
                                "Đã gửi hướng dẫn đặt lại mật khẩu đến email của bạn...", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //
                        progressDialog.dismiss();
                        Toast.makeText(QuenMatKhauActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private void getControl() {

        btnBack = findViewById(R.id.iv_quaylai);
        txtEmail = findViewById(R.id.txtEmailq);
        btnquenmatkhau = findViewById(R.id.btnquenmatkhau);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Vui lòng chờ trong giây lát");
        progressDialog.setCanceledOnTouchOutside(false);
    }
}