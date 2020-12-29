package com.team12.quanlykhohang_nhom12.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.team12.quanlykhohang_nhom12.Adapter.DanhSachHopDongAdapter;
import com.team12.quanlykhohang_nhom12.Adapter.ThongKeHopDongAdapter;
import com.team12.quanlykhohang_nhom12.Library.ModelHopDong;
import com.team12.quanlykhohang_nhom12.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

public class ThongKeDoanhThuThueKhoActivity extends AppCompatActivity {
    private RecyclerView rcdanhsachdanghopdong;
    Toolbar toolbar;
    private ThongKeHopDongAdapter thongKeHopDongAdapter;
    private FirebaseAuth firebaseAuth;
    private String khohangId;
    private ArrayList<ModelHopDong> danhsachlist;
    private TextView tv_total_price, tv_dientichdathue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke_doanh_thu_thue_kho);
        toolbar = findViewById(R.id.toolbar);
        rcdanhsachdanghopdong = findViewById(R.id.rcdanhsachdanghopdong);
        rcdanhsachdanghopdong.setLayoutManager(new LinearLayoutManager(ThongKeDoanhThuThueKhoActivity.this, LinearLayoutManager.VERTICAL, false));
        firebaseAuth = FirebaseAuth.getInstance();
        setSupportActionBar(toolbar);
        setControl();
        loadinput();
        totalgia();
        totaldientichdathue();
    }
    private void loadinput() {
        danhsachlist = new ArrayList<>();
        //Hiển thị slider
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tb_HopDongChinhThuc");
        reference.orderByChild("khohangId").equalTo(khohangId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //before getting reset list
                        danhsachlist.clear();
                        for (DataSnapshot ds: snapshot.getChildren()){
                            ModelHopDong modelHopDong = ds.getValue(ModelHopDong.class);
                            danhsachlist.add(modelHopDong);
                        }
                        thongKeHopDongAdapter = new ThongKeHopDongAdapter(ThongKeDoanhThuThueKhoActivity.this, danhsachlist);
                        rcdanhsachdanghopdong.setAdapter(thongKeHopDongAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void totalgia() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tb_HopDongChinhThuc");
        reference.orderByChild("khohangId").equalTo(khohangId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int sum=0;
                        for (DataSnapshot ds: snapshot.getChildren()){
                            Map<String, Object> map =  (Map<String, Object>)ds.getValue();
                            Object dongia = map.get("tongtien");
                            int dgValue  = Integer.parseInt(String.valueOf(dongia));
                            sum += dgValue;
                            Locale localeVN = new Locale("vi", "VN");
                            NumberFormat vn = NumberFormat.getInstance(localeVN);


                            tv_total_price.setText(vn.format(sum)+" Vnd");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }
    private void totaldientichdathue() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tb_HopDongChinhThuc");
        reference.orderByChild("khohangId").equalTo(khohangId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int sum=0;
                        for (DataSnapshot ds: snapshot.getChildren()){
                            Map<String, Object> map =  (Map<String, Object>)ds.getValue();
                            Object dientich = map.get("dientichthue");
                            int dgValue  = Integer.parseInt(String.valueOf(dientich));
                            sum += dgValue;
                            tv_dientichdathue.setText(sum+" m2   ");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }
    private void setControl() {

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Thống kê");
        khohangId = getIntent().getStringExtra("khohangId");
        tv_total_price = findViewById(R.id.tv_total_price);
        tv_dientichdathue = findViewById(R.id.tv_dientichdathue);

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