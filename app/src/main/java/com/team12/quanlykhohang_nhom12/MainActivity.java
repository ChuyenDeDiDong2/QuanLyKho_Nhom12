package com.team12.quanlykhohang_nhom12;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.team12.quanlykhohang_nhom12.Activity.AddStaftActivity;
import com.team12.quanlykhohang_nhom12.Activity.HomeActivity;
import com.team12.quanlykhohang_nhom12.Activity.HomeToRentActivity;
import com.team12.quanlykhohang_nhom12.Activity.LoginActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);

    }
}