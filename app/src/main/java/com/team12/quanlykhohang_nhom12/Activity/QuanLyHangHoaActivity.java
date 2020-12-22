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
import android.widget.TextView;

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

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

public class QuanLyHangHoaActivity extends AppCompatActivity {
    ImageButton btnAddStation;
    private String khohangId;
    private String hanghoaId;
    Toolbar toolbar;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private RecyclerView danhsachhanghoa;
    private ArrayList<ModelHangHoa> hanghoalist;
    private HangHoaAdapter hangHoaAdapter;
    private TextView tv_total_product, tv_total_price;

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

        totalsoluong();
        totalgia();

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
    private void totalsoluong() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tb_Users");
        reference.child(firebaseAuth.getUid()).child("KhoHang").child(khohangId).child("HangHoa")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int sum=0;
                        for (DataSnapshot ds: snapshot.getChildren()){
                            Map<String, Object> map =  (Map<String, Object>)ds.getValue();
                            Object soluong = map.get("soluong");
                            int pValue  = Integer.parseInt(String.valueOf(soluong));
                            sum += pValue;

                            tv_total_product.setText(String.valueOf(sum));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }
    private void totalgia() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tb_Users");
        reference.child(firebaseAuth.getUid()).child("KhoHang").child(khohangId).child("HangHoa")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int sum=0;
                        for (DataSnapshot ds: snapshot.getChildren()){
                            Map<String, Object> map =  (Map<String, Object>)ds.getValue();
                            Object soluong = map.get("soluong");
                            Object dongia = map.get("dongia");
                            int pValue  = Integer.parseInt(String.valueOf(soluong));
                            int dgValue  = Integer.parseInt(String.valueOf(dongia));
                            sum += (pValue*dgValue);
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
        tv_total_product = findViewById(R.id.tv_total_product);
        tv_total_price = findViewById(R.id.tv_total_price);
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