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
import com.team12.quanlykhohang_nhom12.Adapter.DanhSachDangKyThueAdapter;
import com.team12.quanlykhohang_nhom12.Library.ModelDangKyThue;
import com.team12.quanlykhohang_nhom12.R;

import java.util.ArrayList;

public class DanhSachDangKyActivity extends AppCompatActivity {
    private RecyclerView rscDanhSachDangKyThue;
    Toolbar toolbar;
    private DanhSachDangKyThueAdapter danhSachDangKyThueKhoAdapter;
    private FirebaseAuth firebaseAuth;
    private ArrayList<ModelDangKyThue> danhsachlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_dang_ky);
        toolbar = findViewById(R.id.toolbar);
        rscDanhSachDangKyThue = findViewById(R.id.rcdanhsachdangkythue);
        rscDanhSachDangKyThue.setLayoutManager(new LinearLayoutManager(DanhSachDangKyActivity.this, LinearLayoutManager.VERTICAL, false));
        firebaseAuth = FirebaseAuth.getInstance();
        setSupportActionBar(toolbar);
        setControl();
        loadinput();
    }
    //danh sach chưa phê duyệt cho thuê
    private void loadinput() {
        danhsachlist = new ArrayList<>();
        //Hiển thị slider
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tb_HopDong");
        reference.orderByChild("thongbaothue").equalTo(firebaseAuth.getUid()+"true")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //before getting reset list
                        danhsachlist.clear();
                        for (DataSnapshot ds: snapshot.getChildren()){
                            ModelDangKyThue modelDangKyThue = ds.getValue(ModelDangKyThue.class);
                            danhsachlist.add(modelDangKyThue);
                        }
                        danhSachDangKyThueKhoAdapter = new DanhSachDangKyThueAdapter(DanhSachDangKyActivity.this, danhsachlist);
                        rscDanhSachDangKyThue.setAdapter(danhSachDangKyThueKhoAdapter);
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
        getSupportActionBar().setTitle("Danh sách đăng ký thuê");

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