package com.team12.quanlykhohang_nhom12;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.team12.quanlykhohang_nhom12.Activity.DangKyActivity;
import com.team12.quanlykhohang_nhom12.Activity.DangnhapActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(MainActivity.this, DangnhapActivity.class);
        startActivity(intent);

    }
}