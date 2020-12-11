package com.team12.quanlykhohang_nhom12.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.team12.quanlykhohang_nhom12.Adapter.HangHoaAdapter;
import com.team12.quanlykhohang_nhom12.R;

public class NewProductActivity extends AppCompatActivity {
    Button btnThem;
    Button btnCH;
    ImageView imgHH;
    EditText edtTenHang, edtSoLuong, edtDonVi;
    HangHoaAdapter adapter;
    DatabaseReference HANGHOA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);
        //them
        btnThem = (Button) findViewById(R.id.btnThem);
        edtTenHang = (EditText) findViewById(R.id.edtTenhang);
        edtSoLuong = (EditText) findViewById(R.id.edtSoLuong);
        edtDonVi = (EditText) findViewById(R.id.edtDonVi);
        //test
        btnCH = (Button) findViewById(R.id.btnCH);
        imgHH = (ImageView) findViewById(R.id.imgHH);

        adapter = new HangHoaAdapter();
        HANGHOA = FirebaseDatabase.getInstance().getReference();
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String TenHangHoa = edtTenHang.getText().toString();
                String SoLuong = edtSoLuong.getText().toString();
                String DonVi = edtDonVi.getText().toString();
                HangHoaAdapter adapter;
                adapter = new HangHoaAdapter(TenHangHoa, SoLuong, DonVi);
                HANGHOA.child("HangHoa").push().setValue(adapter);

                Toast.makeText(NewProductActivity.this, "Thêm hàng hóa thành công", Toast.LENGTH_LONG).show();
            }
        });

        //
        btnCH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermissions();
            }
        });
    }

    //
    private void requestPermissions(){

    }
}