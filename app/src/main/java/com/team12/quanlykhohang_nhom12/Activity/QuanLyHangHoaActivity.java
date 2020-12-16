package com.team12.quanlykhohang_nhom12.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.team12.quanlykhohang_nhom12.Adapter.HangHoaAdapter;
import com.team12.quanlykhohang_nhom12.Adapter.KhoHangIDAdapter;
import com.team12.quanlykhohang_nhom12.Fragment.PhongBanFragment;
import com.team12.quanlykhohang_nhom12.Library.ModelHangHoa;
import com.team12.quanlykhohang_nhom12.Library.ModelKhoHang;
import com.team12.quanlykhohang_nhom12.R;

import java.util.ArrayList;

public class QuanLyHangHoaActivity extends AppCompatActivity {
    ImageButton btnAddStation;
    private String khohangId;
    Toolbar toolbar;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private RecyclerView danhsachhanghoa;
    private ArrayList<ModelHangHoa> hanghoalist;
    private HangHoaAdapter hangHoaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_hang_hoa);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setControls();
        setEvent();
        loadKhoHangDetail();
        loadKhoHang();

    }

    private void setEvent() {btnAddStation.setOnClickListener(new View.OnClickListener() {
        @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuanLyHangHoaActivity.this, NewProductActivity.class);
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
    private void loadKhoHang() {
        hanghoalist = new ArrayList<>();
        //get all product
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tb_Users");
        reference.child(firebaseAuth.getUid()).child("KhoHang").child(khohangId).child("HangHoa")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //before getting reset list
                        hanghoalist.clear();
                        for (DataSnapshot ds: snapshot.getChildren()){
                            ModelHangHoa modelHangHoa = ds.getValue(ModelHangHoa.class);
                            hanghoalist.add(modelHangHoa);
                        }
                        hangHoaAdapter = new HangHoaAdapter(QuanLyHangHoaActivity.this, hanghoalist);
                        danhsachhanghoa.setAdapter(hangHoaAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
    private void setControls() {

        khohangId = getIntent().getStringExtra("khohangId");
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Vui lòng đợi");
        progressDialog.setCanceledOnTouchOutside(false);

        btnAddStation = findViewById(R.id.btnAddStation);
        danhsachhanghoa = findViewById(R.id.rv__stationary_manager_list_product);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Danh sách hàng hóa ");

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