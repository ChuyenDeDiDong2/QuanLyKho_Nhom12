package com.team12.quanlykhohang_nhom12.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.team12.quanlykhohang_nhom12.Library.Constants_kho;
import com.team12.quanlykhohang_nhom12.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class
DangKyTaoHopDongActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView tvtenuser,tvsotienuoctinh,tvsothangdachon, tvsodienthoaiuser, tvtenkhodangky, tvgiakhothuemotthang, ngaydangkyhopdong, spChonthangmuonthue;
    String hisUid;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private String khohangId;
    private ImageView ivmauhopdong;
    private Button btndangkythue;
    String timestamp = ""+System.currentTimeMillis();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangky_tao_hop_dong);
        toolbar = findViewById(R.id.toolbar);

        tvtenuser = findViewById(R.id.tvtenuser);
        tvsodienthoaiuser = findViewById(R.id.tvsodienthoaiuser);
        tvtenkhodangky = findViewById(R.id.tvtenkhodk);
        tvgiakhothuemotthang = findViewById(R.id.tvgiakhothuemotthang);
        ngaydangkyhopdong = findViewById(R.id.ngaydangkyhopdong);
        spChonthangmuonthue = findViewById(R.id.spChonthangmuonthue);
        tvsotienuoctinh = findViewById(R.id.tvsotienuoctinh);
        btndangkythue = findViewById(R.id.btndangkythue);


        firebaseAuth = FirebaseAuth.getInstance();
        hisUid = getIntent().getStringExtra("hisUid");
        khohangId = getIntent().getStringExtra("khohangId");
        loadThueinfo();
        loadKhoinfo();
        loadImage();
        setSupportActionBar(toolbar);
        setControl();// anh xa
        setEvent();//tat ca cac su kien

        //tinhtiongtien();

    }

    private void setEvent() {
        //su kien chon thang muon thue
        spChonthangmuonthue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                tinhtrangDialog();
            }
        });
        btndangkythue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addHopDong();
                progressDialog.dismiss();
            }
        });
    }
    //chon thang muon thue
    private void tinhtrangDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Tình trạng kho")
                .setItems(Constants_kho.options4, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String tinhtrang = Constants_kho.options4[i];
                        spChonthangmuonthue.setText(tinhtrang);
                        tinhtiongtien();
                    }
                })

                .show();
    }
    //tinh tong tien
    private void tinhtiongtien(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tb_Users");
        reference.child(hisUid).child("KhoHang").child(khohangId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    String tenkho = ""+snapshot.child("tenkho").getValue();
                    String giachothue = ""+snapshot.child("giachothue").getValue();
                    String giamoi = ""+snapshot.child("giamoi").getValue();

                    int tamp = 0;
                    String thangthue = spChonthangmuonthue.getText().toString().trim();
                    tvtenkhodangky.setText(tenkho);
                    if (giamoi.equals("0")){
                        tamp = Integer.parseInt(thangthue) * Integer.parseInt(giachothue);
                        tvsotienuoctinh.setText(tamp);
                    }else {
                        tamp = Integer.parseInt(thangthue) * Integer.parseInt(giamoi);
                        tvsotienuoctinh.setText(tamp);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void setControl() {
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Vui lòng đợi");
        progressDialog.setCanceledOnTouchOutside(false);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Đăng ký thuê kho");

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
    private String tenThue, soDT;
    private void loadThueinfo(){//moc thoi gian
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(Long.parseLong(timestamp));
        String dateTime  = DateFormat.format("dd/MM/yyyy", cal).toString();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tb_Users");
        reference.child(firebaseAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                     tenThue = ""+snapshot.child("name").getValue();
                     soDT = ""+snapshot.child("phone").getValue();

                    tvtenuser.setText(tenThue);
                    tvsodienthoaiuser.setText(soDT);

                    ngaydangkyhopdong.setText(dateTime);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private String tenkho;
    private void loadKhoinfo(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tb_Users");
        reference.child(hisUid).child("KhoHang").child(khohangId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                     tenkho = ""+snapshot.child("tenkho").getValue();
                    String giachothue = ""+snapshot.child("giachothue").getValue();
                    String giamoi = ""+snapshot.child("giamoi").getValue();

                    tvtenkhodangky.setText(tenkho);
                    if (giamoi.equals("0")){
                        tvgiakhothuemotthang.setText(giachothue+"vnd");
                    }else {

                        tvgiakhothuemotthang.setText(giamoi+"vnd");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loadImage(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tb_Users");
        reference.child(hisUid).child("MauDieuKhoan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    String hinhMDK = ""+snapshot.child("hinhMDK").getValue();

                    try {
                        Picasso.get().load(hinhMDK).placeholder(R.drawable.google).into(ivmauhopdong);
                    }catch (Exception e){
                        ivmauhopdong.setImageResource(R.drawable.google);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void addHopDong() {
        progressDialog.setMessage("Đang thực hiện đăng kí hợp đồng");
        progressDialog.show();
        String timestamp = ""+System.currentTimeMillis();

        String tongtienthu = tvsotienuoctinh.getText().toString().trim();
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("khohangId", ""+timestamp);
            hashMap.put("tenthu", ""+ tenThue);
            hashMap.put("tenkho", ""+tenkho);
            hashMap.put("sodienthoai", ""+soDT);
            hashMap.put("tongtien", ""+tongtienthu);

            hashMap.put("hinhanhkho", "");//hinhanh
            hashMap.put("timstamp", ""+timestamp);//
            hashMap.put("uid", ""+firebaseAuth.getUid());//
            hashMap.put("hisUid", ""+hisUid);//
            //add to db
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tb_HopDong");
            reference.child(timestamp).setValue(hashMap)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            //
                            progressDialog.dismiss();
                            Toast.makeText(DangKyTaoHopDongActivity.this, "Đăng ký thành công...", Toast.LENGTH_SHORT).show();
                            //clearData();
                        }
                    })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //
                            progressDialog.dismiss();
                            Toast.makeText(DangKyTaoHopDongActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

    }


}