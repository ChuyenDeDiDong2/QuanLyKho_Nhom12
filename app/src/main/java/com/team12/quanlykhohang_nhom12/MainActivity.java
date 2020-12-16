package com.team12.quanlykhohang_nhom12;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.team12.quanlykhohang_nhom12.Activity.DangNhapActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(MainActivity.this, DangNhapActivity.class);
        startActivity(intent);

    }
}