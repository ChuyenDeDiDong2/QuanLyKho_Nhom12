package com.team12.quanlykhohang_nhom12.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.team12.quanlykhohang_nhom12.Adapter.DanhSachHopDongAdapter;
import com.team12.quanlykhohang_nhom12.Library.ModelHopDong;
import com.team12.quanlykhohang_nhom12.R;

import java.util.ArrayList;

public class HopDongCuaToiActivity extends AppCompatActivity {
    private RecyclerView rcdanhsachdanghopdong;
    Toolbar toolbar;
    private DanhSachHopDongAdapter danhSachHopDongAdapter;
    private FirebaseAuth firebaseAuth;
    private ArrayList<ModelHopDong> danhsachlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hop_dong_cua_toi);
        toolbar = findViewById(R.id.toolbar);
        rcdanhsachdanghopdong = findViewById(R.id.rcdanhsachdanghopdong);
        rcdanhsachdanghopdong.setLayoutManager(new LinearLayoutManager(HopDongCuaToiActivity.this, LinearLayoutManager.VERTICAL, false));
        firebaseAuth = FirebaseAuth.getInstance();
        setSupportActionBar(toolbar);
        setControl();
        loadinput();
    }
    private void loadinput() {
        danhsachlist = new ArrayList<>();
        //Hiển thị slider
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tb_HopDongChinhThuc");
        reference.orderByChild("uid").equalTo(firebaseAuth.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //before getting reset list
                        danhsachlist.clear();
                        for (DataSnapshot ds: snapshot.getChildren()){
                            ModelHopDong modelHopDong = ds.getValue(ModelHopDong.class);
                            danhsachlist.add(modelHopDong);
                        }
                        danhSachHopDongAdapter = new DanhSachHopDongAdapter(HopDongCuaToiActivity.this, danhsachlist);
                        rcdanhsachdanghopdong.setAdapter(danhSachHopDongAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
    //ánh xạ
    private void setControl() {

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Danh sách hợp đồng");

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