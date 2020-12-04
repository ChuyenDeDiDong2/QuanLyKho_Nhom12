package com.team12.quanlykhohang_nhom12.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.team12.quanlykhohang_nhom12.R;

public class NhapHangActivity extends AppCompatActivity {

    Button btnNhap;
    EditText edtLoaiH, edtSoL, edtNoiNhap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhap_hang);

        btnNhap = (Button) findViewById(R.id.btnNhap);
        edtLoaiH = (EditText) findViewById(R.id.edtLoaiH);
        edtSoL = (EditText) findViewById(R.id.edtSoL);
        edtNoiNhap = (EditText) findViewById(R.id.edtNoiNhap);
    }
}