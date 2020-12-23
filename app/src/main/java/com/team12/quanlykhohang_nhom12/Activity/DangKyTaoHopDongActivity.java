package com.team12.quanlykhohang_nhom12.Activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.team12.quanlykhohang_nhom12.R;

public class
DangKyTaoHopDongActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView tvtenuser, tvsodienthoaiuser, tvtenkhodangky;
    String hisUid;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangky_tao_hop_dong);
        toolbar = findViewById(R.id.toolbar);

        tvtenuser = findViewById(R.id.tvtenuser);
        tvsodienthoaiuser = findViewById(R.id.tvsodienthoaiuser);
        tvtenkhodangky = findViewById(R.id.tvtenkhodangky);

        hisUid = getIntent().getStringExtra("hisUid");
        loadThueinfo();
        setSupportActionBar(toolbar);
        setControl();
        setEvent();
    }

    private void setEvent() {

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
    private void loadThueinfo(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tb_Users");
        reference.child(firebaseAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    //String tenThue = ""+snapshot.child("tentaikhoan").getValue();
                    //String soDT = ""+snapshot.child("phone").getValue();

                    //tvtenuser.setText(tenThue);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}