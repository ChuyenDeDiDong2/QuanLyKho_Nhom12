package com.team12.quanlykhohang_nhom12.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.team12.quanlykhohang_nhom12.Fragment.CapPhatFragment;
import com.team12.quanlykhohang_nhom12.Fragment.PhongBanFragment;
import com.team12.quanlykhohang_nhom12.Fragment.TaiKhoanKhoFragment;
import com.team12.quanlykhohang_nhom12.Fragment.VaiTroFragment;
import com.team12.quanlykhohang_nhom12.Fragment.TaiKhoanFragment;
import com.team12.quanlykhohang_nhom12.R;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawer;private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    LinearLayout btnCapPhat, btnThongKe, btnVanPhongPham;
    private String khohangId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        khohangId = getIntent().getStringExtra("khohangId");
        drawer = findViewById(R.id.drawer_layout);
        ImageSlider imageSlider = findViewById(R.id.slider);
        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel("https://sec-warehouse.vn/wp-content/uploads/2020/08/kho-hoa-chat-800x400.jpg", ""));
        slideModels.add(new SlideModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ6Y0EVQGEcoPtGKXtXaPIU_mysnqzzT14qZg&usqp=CAU", ""));
        slideModels.add(new SlideModel("https://vilas.edu.vn/wp-content/uploads/2019/05/flow.jpg", ""));
        slideModels.add(new SlideModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSh-P8PfdVxPiswy3TyUqklTPnOF9QkqKEYMg&usqp=CAU", ""));
        imageSlider.setImageList(slideModels, true);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState == null) {
        }

        setCantrol();
        setEVent();
        loadKhoHangDetail();
    }

    private void setEVent() {
        //su kien khi nhan vao icon tren trang chu
        //----trang cap phat
        btnCapPhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        //----trang thong ke
        btnThongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        //----trang nhan vien
        btnVanPhongPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, QuanLyHangHoaActivity.class);
                intent.putExtra("khohangId",khohangId);
                startActivity(intent);
            }
        });
    }
    private void loadKhoHangDetail() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tb_Users");
        reference.child(firebaseAuth.getUid()).child("KhoHang").child(khohangId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //get data
                        String khohangId = ""+snapshot.child("khohangId").getValue();
                        String tenkho = ""+snapshot.child("tenkho").getValue();
                        String diachikhohang = ""+snapshot.child("diachikhohang").getValue();
                        String dientichkho = ""+snapshot.child("dientichkho").getValue();
                        String dienthoaikho = ""+snapshot.child("dienthoaikho").getValue();
                        String giachothue = ""+snapshot.child("giachothue").getValue();
                        String tinhtrangkho = ""+snapshot.child("tinhtrangkho").getValue();
                        String ghichukhho = ""+snapshot.child("ghichukhho").getValue();
                        String giamgiaAvailable = ""+snapshot.child("giamgiaAvailable").getValue();
                        String giamoi = ""+snapshot.child("giamoi").getValue();
                        String phantramkm = ""+snapshot.child("phantramkm").getValue();
                        String hinhanhkho = ""+snapshot.child("hinhanhkho").getValue();
                        String timstamp = ""+snapshot.child("timstamp").getValue();
                        String uid = ""+snapshot.child("uid").getValue();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void setCantrol() {
        //getCantroll
        btnCapPhat = findViewById(R.id.btnaLlocation);
        btnThongKe = findViewById(R.id.btnStatistics);

        btnVanPhongPham = findViewById(R.id.btnStationery);
        khohangId = getIntent().getStringExtra("khohangId");
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Vui lòng đợi");
        progressDialog.setCanceledOnTouchOutside(false);


    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }

    }
    //Navigation code
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.mn_home:
                break;
            case R.id.mn_thong_tin_tk:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TaiKhoanKhoFragment()).commit();
                break;
            case R.id.mn_allocation:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CapPhatFragment()).commit();
                break;

            case R.id.mn_san_pham:
                Intent intent = new Intent(this, QuanLyHangHoaActivity.class);
                startActivity(intent);
                drawer.closeDrawers();
                return  true;

            case R.id.mn_infor:
                Intent info = new Intent(this, ThongTinAppActivity.class);
                startActivity(info);

                drawer.closeDrawers();
                return true;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_allocation, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_seach:
                Toast.makeText(this, "Search button selected", Toast.LENGTH_SHORT).show();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }


}