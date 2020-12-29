package com.team12.quanlykhohang_nhom12.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.team12.quanlykhohang_nhom12.R;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Locale;

public class ChiTietHopDongActivity extends AppCompatActivity {

    Toolbar toolbar;
    private Locale localeVN = new Locale("vi", "VN");
    private NumberFormat vn = NumberFormat.getInstance(localeVN);
    String mahopdong;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private TextView tvmasohopdong, tvngaythangright, tvtendanhnghiepright,tvdiachiright, tvdienthoairight, tvemailright, tvgiayphepsoright, tvmasothueright, tvtendoanhnghiep;
    private TextView tvtennguoithueright, tvdiachithueright, tvdienthoaithueright, tvemailthueright;
    private TextView tvtenkhoright, tvthoigianthueright, tvsotienthueright, tvdientichthueright;
    private TextView tvnoidungdieukhoanhopdong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_hop_dong);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setControls();
        loadthongtinhopdong();
    }
    private String diachibena, diachibenb, dienthoaibena, dientichthue, emailbena, emailbenb, giayphepso;
    private String hisUid, khohangId, masothue, noidungdieukhoan, sodienthoaibenb, tendoanhnghiep, tenkho;
    private String tenthue, thoigianthue, thongbaothue, timstamp ,tongtien, uid;
    private void loadthongtinhopdong(){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tb_HopDongChinhThuc");
        reference.child(mahopdong).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds: snapshot.getChildren()){

                    diachibena = ""+snapshot.child("diachibena").getValue();
                    diachibenb = ""+snapshot.child("diachibenb").getValue();
                    dienthoaibena = ""+snapshot.child("dienthoaibena").getValue();
                    dientichthue = ""+snapshot.child("dientichthue").getValue();
                    emailbena = ""+snapshot.child("emailbena").getValue();
                    emailbenb = ""+snapshot.child("emailbenb").getValue();
                    giayphepso = ""+snapshot.child("giayphepso").getValue();
                    hisUid = ""+snapshot.child("hisUid").getValue();
                    khohangId = ""+snapshot.child("khohangId").getValue();
                    masothue = ""+snapshot.child("masothue").getValue();
                    noidungdieukhoan = ""+snapshot.child("noidungdieukhoan").getValue();
                    sodienthoaibenb = ""+snapshot.child("sodienthoaibenb").getValue();
                    tendoanhnghiep = ""+snapshot.child("tendoanhnghiep").getValue();
                    tenkho = ""+snapshot.child("tenkho").getValue();
                    tenthue = ""+snapshot.child("tenthue").getValue();
                    thoigianthue = ""+snapshot.child("thoigianthue").getValue();
                    thongbaothue = ""+snapshot.child("thongbaothue").getValue();
                    timstamp = ""+snapshot.child("timstamp").getValue();
                    tongtien = ""+snapshot.child("tongtien").getValue();
                    uid = ""+snapshot.child("uid").getValue();
                    tvmasohopdong.setText("Mã hợp đồng: "+mahopdong);
                    Calendar cal = Calendar.getInstance(Locale.ENGLISH);
                    cal.setTimeInMillis(Long.parseLong(timstamp));
                    String dateTime  = DateFormat.format("dd/MM/yyyy", cal).toString();
                    tvngaythangright.setText(dateTime);
                    tvtendanhnghiepright.setText(tendoanhnghiep);
                    tvdiachiright.setText(diachibena);
                    tvdienthoairight.setText(dienthoaibena);
                    tvemailright.setText(emailbena);
                    tvgiayphepsoright.setText(giayphepso);
                    tvmasothueright.setText(masothue);
                    tvtendoanhnghiep.setText(tendoanhnghiep);
                    tvtennguoithueright.setText(tenthue);
                    tvdiachithueright.setText(diachibenb);
                    tvdienthoaithueright.setText(sodienthoaibenb);
                    tvemailthueright.setText(emailbenb);
                    tvtenkhoright.setText(tenkho);
                    tvthoigianthueright.setText(thoigianthue + " tháng");
                    tvsotienthueright.setText(tongtien);
                    tvdientichthueright.setText(dientichthue+" m2");
                    tvnoidungdieukhoanhopdong.setText(noidungdieukhoan);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void setControls() {

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Chi tiết hợp đồng");

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Vui lòng đợi");
        progressDialog.setCanceledOnTouchOutside(false);
        mahopdong = getIntent().getStringExtra("mahopdong");

        tvmasohopdong = findViewById(R.id.tvmasohopdong);
        tvngaythangright = findViewById(R.id.tvngaythangright);
        tvtendanhnghiepright = findViewById(R.id.tvtendanhnghiepright);
        tvdiachiright = findViewById(R.id.tvdiachiright);
        tvdienthoairight = findViewById(R.id.tvdienthoairight);
        tvemailright = findViewById(R.id.tvemailright);
        tvgiayphepsoright = findViewById(R.id.tvgiayphepsoright);
        tvmasothueright = findViewById(R.id.tvmasothueright);
        tvtendoanhnghiep = findViewById(R.id.tvtendoanhnghiep);
        tvtennguoithueright = findViewById(R.id.tvtennguoithueright);
        tvdiachithueright = findViewById(R.id.tvdiachithueright);
        tvdienthoaithueright = findViewById(R.id.tvdienthoaithueright);
        tvemailthueright = findViewById(R.id.tvemailthueright);
        tvtenkhoright = findViewById(R.id.tvtenkhoright);
        tvthoigianthueright = findViewById(R.id.tvthoigianthueright);
        tvsotienthueright = findViewById(R.id.tvsotienthueright);
        tvnoidungdieukhoanhopdong = findViewById(R.id.tvnoidungdieukhoanhopdong);
        tvdientichthueright = findViewById(R.id.tvdientichthueright);



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