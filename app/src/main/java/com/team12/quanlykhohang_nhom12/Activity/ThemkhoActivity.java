package com.team12.quanlykhohang_nhom12.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.team12.quanlykhohang_nhom12.Adapter.KhoAdapter;
import com.team12.quanlykhohang_nhom12.MainActivity;
import com.team12.quanlykhohang_nhom12.R;

public class ThemkhoActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnThemK;
    EditText edtTenKho, edtSDT, edtEmail, edtSucchua, edtDiachi;
    DatabaseReference myDatabaseReference;
    //KhoAdapter khoAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themkho);

        btnThemK = (Button) findViewById(R.id.btnThemK);
        edtTenKho = (EditText) findViewById(R.id.edtTenKho);
        edtSDT = (EditText) findViewById(R.id.edtSDT);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtSucchua = (EditText) findViewById(R.id.edtSucchua);
        edtDiachi = (EditText) findViewById(R.id.edtDiachi);

        //khoAdapter = new KhoAdapter();
        myDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Khohang");

        btnThemK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenkho = edtTenKho.getText().toString();
                String sodt = edtSDT.getText().toString();
                String email = edtEmail.getText().toString();
                String succhua = edtSucchua.getText().toString();
                String diachi = edtDiachi.getText().toString();

                KhoAdapter khoAdapter = new KhoAdapter(tenkho, sodt, email, succhua, diachi);
                myDatabaseReference.push().setValue(khoAdapter);

                Toast.makeText(ThemkhoActivity.this, "Nhập thông tin kho thành công!", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.banner:
                startActivity(new Intent(this, MainActivity.class));
                break;
            
        }
    }
}