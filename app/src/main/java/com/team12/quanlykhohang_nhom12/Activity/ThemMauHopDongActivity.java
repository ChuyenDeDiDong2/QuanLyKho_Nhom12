package com.team12.quanlykhohang_nhom12.Activity;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import com.team12.quanlykhohang_nhom12.Library.MauHopDong;
import com.team12.quanlykhohang_nhom12.R;

public class ThemMauHopDongActivity extends AppCompatActivity {

    Button btnThemMauHD;
    EditText edtTenMauHD, edtDieukhoan, edtDonGia, edtDonVi;
    MauHopDong mauhopDongAdapter;
    DatabaseReference mauHD;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_mau_hop_dong);

        btnThemMauHD = (Button) findViewById(R.id.btnThemMauHD);
        edtTenMauHD = (EditText) findViewById(R.id.edtTenMauHD);
        edtDieukhoan = (EditText) findViewById(R.id.edtDieukhoan);
        edtDonVi = (EditText) findViewById(R.id.edtDonVi);
        edtDonGia = (EditText) findViewById(R.id.edtDongia);

        mauhopDongAdapter = new MauHopDong();
        mauHD = FirebaseDatabase.getInstance().getReference();
        btnThemMauHD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenmauhd = edtTenMauHD.getText().toString();
                String dieukhoan = edtDieukhoan.getText().toString();
                String donvi = edtDonVi.getText().toString();
                String dongia = edtDonGia.getText().toString();

                MauHopDong mauHopDongAdapter;
                mauHopDongAdapter = new MauHopDong(tenmauhd,
                        dieukhoan,
                        donvi,
                        dongia);
                mauHD.child("MauHD").push().setValue(mauHopDongAdapter);

                Toast.makeText(ThemMauHopDongActivity.this, "Thêm mẫu thành công", Toast.LENGTH_LONG).show();
            }
        });
    }
}