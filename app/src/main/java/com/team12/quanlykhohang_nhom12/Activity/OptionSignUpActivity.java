package com.team12.quanlykhohang_nhom12.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.team12.quanlykhohang_nhom12.R;

public class OptionSignUpActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView btntao_tai_khoan_chothu, btntao_tai_khoan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_sign_up);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setControls();
        setEvent();
    }

    private void setEvent() {
        btntao_tai_khoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OptionSignUpActivity.this, DangKyActivity.class);
                startActivity(intent);
            }
        });
        btntao_tai_khoan_chothu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OptionSignUpActivity.this, DangKyChoThueKhoActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setControls() {

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Đăng ký tài khoản");
        btntao_tai_khoan_chothu = findViewById(R.id.tvtao_tai_khoan_dang_kho);
        btntao_tai_khoan = findViewById(R.id.btntao_tai_khoan);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:  // Sự kiện nút back
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}