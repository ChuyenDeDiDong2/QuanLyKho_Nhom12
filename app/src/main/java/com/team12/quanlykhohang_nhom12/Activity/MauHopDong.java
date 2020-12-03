package com.team12.quanlykhohang_nhom12.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.team12.quanlykhohang_nhom12.MainActivity;
import com.team12.quanlykhohang_nhom12.R;

import java.util.ArrayList;

public class MauHopDong extends AppCompatActivity {

    private Spinner spinnerNhaKho;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private ArrayList<String> arrayList = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mau_hopdong);

        spinnerNhaKho = findViewById(R.id.spinnerNhaKho);
        showDataSpinner();
    }

    private void showDataSpinner() {
        databaseReference.child("KhoHang").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for(DataSnapshot item : snapshot.getChildren()){
                    arrayList.add(item.child("KhoHang").getValue(String.class));
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(MauHopDong.this,R.layout.style_spinner,arrayList);
                spinnerNhaKho.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
