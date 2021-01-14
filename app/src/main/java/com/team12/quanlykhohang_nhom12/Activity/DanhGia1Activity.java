package com.team12.quanlykhohang_nhom12.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.team12.quanlykhohang_nhom12.Library.ModelChuKho;
import com.team12.quanlykhohang_nhom12.R;

import java.util.HashMap;
import java.util.Map;

public class DanhGia1Activity extends AppCompatActivity {

    EditText edtNhanXet;
    ImageButton btnBack;
    FloatingActionButton btnXacNhan;
    ImageView profileIV;
    RatingBar ratingBar;
    TextView txtTenkho;
    String hisUid;

    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_gia1);

        ratingBar = findViewById(R.id.ratingBar);
        edtNhanXet = findViewById(R.id.edtNhanXet);
        btnXacNhan = findViewById(R.id.btnXacNhan);
        btnBack = findViewById(R.id.btnBack);
        profileIV = findViewById(R.id.profileIV);
        txtTenkho = findViewById(R.id.txtTenkho);


        hisUid = getIntent().getStringExtra("hisUid");
        auth = FirebaseAuth.getInstance();
        //loadMyReview();
        loadKhoInfo();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                inputData();
                totalgia();
                DemSoThongBao();
                tinhtrungbinhsao();
            }
        });
        //totalgia();
    }

    private void loadKhoInfo() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tb_Users");
        reference.child(hisUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String tenkho = ""+snapshot.child("tentaikhoan").getValue();
                String KhoImage = ""+snapshot.child("profileImage").getValue();

                txtTenkho.setText(tenkho);
                try {
                    Picasso.get().load(KhoImage).placeholder(R.drawable.ic_avartar_48).into(profileIV);
                }catch (Exception e){
                    profileIV.setImageResource(R.drawable.ic_avartar_48);
                }
            }

            @Override public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private int sum=4;
    private void totalgia() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tb_Users");
        reference.child(hisUid).child("DanhGia").orderByChild("hisUid").equalTo(hisUid)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot ds: snapshot.getChildren()){
                            Map<String, Object> map =  (Map<String, Object>)ds.getValue();
                            Object diem = map.get("diem");
                            int dgValue  = Integer.parseInt(String.valueOf(diem));
                            sum += dgValue;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }
    private int sum1=1;
    private void DemSoThongBao(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tb_Users");
        reference.child(hisUid).child("DanhGia").orderByChild("hisUid").equalTo(hisUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    sum1 = (int) snapshot.getChildrenCount();
                }
                else {
                    sum1 = 0;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
     private void tinhtrungbinhsao(){
         int diem = sum/sum1;
         FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
         ProgressDialog progressDialog = new ProgressDialog(this);
         HashMap<String, Object> hashMap = new HashMap<>();

         hashMap.put("diemtb", "" + diem);

         //cap nhat db
         DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tb_Users");
         reference.child(hisUid)
                 .updateChildren(hashMap)
                 .addOnSuccessListener(new OnSuccessListener<Void>() {
                     @Override
                     public void onSuccess(Void aVoid) {
                         //update
                         progressDialog.dismiss();
                         Toast.makeText(DanhGia1Activity.this, "Tài khoản đã bị khóa!", Toast.LENGTH_SHORT).show();
                     }
                 })
                 .addOnFailureListener(new OnFailureListener() {
                     @Override
                     public void onFailure(@NonNull Exception e) {
                         //update failed
                         progressDialog.dismiss();
                         Toast.makeText(DanhGia1Activity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                     }
                 });
     }

    private void inputData() {

        String ratings = "" + ratingBar.getRating();
        String review = "" + edtNhanXet.getText().toString().trim();
        String timestamp = ""+System.currentTimeMillis();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("uid", "" + auth.getUid());
        hashMap.put("his", "" + hisUid);
        hashMap.put("diem", "" + ratings);
        hashMap.put("nhanxet", "" + review);
        hashMap.put("timestamp", "" + timestamp);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tb_Users");
        reference.child(hisUid).child("DanhGia").child(timestamp).updateChildren(hashMap).
                addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(DanhGia1Activity.this, "Đánh giá hoàn thành!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(DanhGia1Activity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}