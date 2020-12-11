package com.team12.quanlykhohang_nhom12.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

import java.io.IOException;
import java.util.HashMap;

public class SuaKhoHangActivity extends AppCompatActivity {
    private String khohangId;
    Toolbar toolbar;
    private ImageView ivhinhanhkho;
    private EditText txtten_kho_hang,txtdiachi_kho_hang, txtdien_tich_kho, txtsodt_kho, txtgiacho_thue, txtghi_chu_kho, txtgia_moi_kho, txtgiamphantram_10;
    private TextView tvtinh_trang_kho;
    private SwitchCompat swgiamgia;
    private Button btnsua_kho_hang;

    private Uri filePath;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    private final int PICK_IMAGE_REQUEST = 71;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_kho_hang);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setControl();
        setEvent();
        loadKhoHangDetail();
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
                        //set data to view
                        if(giamgiaAvailable.equals("true")){
                            swgiamgia.setChecked(true);

                            txtgia_moi_kho.setVisibility(View.VISIBLE);
                            txtgiamphantram_10.setVisibility(View.VISIBLE);
                        }else {

                            swgiamgia.setChecked(false);

                            txtgia_moi_kho.setVisibility(View.GONE);
                            txtgiamphantram_10.setVisibility(View.GONE);
                        }
                        txtten_kho_hang.setText(tenkho);
                        txtdiachi_kho_hang.setText(diachikhohang);
                        txtdien_tich_kho.setText(dientichkho);
                        txtsodt_kho.setText(dienthoaikho);
                        txtgiacho_thue.setText(giachothue);
                        tvtinh_trang_kho.setText(tinhtrangkho);
                        txtghi_chu_kho.setText(ghichukhho);
                        txtgia_moi_kho.setText(giamoi);
                        txtgiamphantram_10.setText(phantramkm);
                        try {
                            Picasso.get().load(hinhanhkho).placeholder(R.drawable.google).into(ivhinhanhkho);
                        }catch (Exception e){
                            ivhinhanhkho.setImageResource(R.drawable.google);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void setEvent() {
        ivhinhanhkho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //su kien chon anh tu thu vien
                chooseImage();
            }
        });
        //
        tvtinh_trang_kho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                tinhtrangDialog();
            }
        });
        btnsua_kho_hang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputData();
            }
        });
        swgiamgia.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    txtgia_moi_kho.setVisibility(View.VISIBLE);
                    txtgiamphantram_10.setVisibility(View.VISIBLE);
                }else {
                    txtgia_moi_kho.setVisibility(View.GONE);
                    txtgiamphantram_10.setVisibility(View.GONE);
                }
            }
        });
    }
    private String tenkho,diachikhohang, dientichkho, dienthoaikho, giachothue, tinhtrangkho, ghichu, giamoi, khuyenmaiphantram;
    private boolean giamgiaAvailable  =false;
    private void inputData() {
        tenkho = txtten_kho_hang.getText().toString().trim();
        dientichkho = txtdien_tich_kho.getText().toString().trim();
        diachikhohang = txtdiachi_kho_hang.getText().toString().trim();
        dienthoaikho = txtsodt_kho.getText().toString().trim();
        giachothue = txtgiacho_thue.getText().toString().trim();
        tinhtrangkho = tvtinh_trang_kho.getText().toString().trim();
        ghichu = txtghi_chu_kho.getText().toString().trim();
        //giamoi = txtgia_moi_kho.getText().toString().trim();
        //khuyenmaiphantram = txtgiamphantram_10.getText().toString().trim();
        giamgiaAvailable = swgiamgia.isChecked();//true/false
        if(TextUtils.isEmpty(tenkho)){
            Toast.makeText(this, "Vui lòng nhập tên kho...", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(diachikhohang)){
            Toast.makeText(this, "Vui lòng nhập địa chỉ kho...", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(dientichkho)){
            Toast.makeText(this, "Vui lòng nhập diện tích kho...", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(dienthoaikho)){
            Toast.makeText(this, "Vui lòng nhập số điện thoại kho...", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(giachothue)){
            Toast.makeText(this, "Vui lòng nhập giá...", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(tinhtrangkho)){
            Toast.makeText(this, "Vui lòng nhập tình trạng kho...", Toast.LENGTH_SHORT).show();
            return;
        }
        if (giamgiaAvailable){
            giamoi = txtgia_moi_kho.getText().toString().trim();
            khuyenmaiphantram = txtgiamphantram_10.getText().toString().trim();
            if(TextUtils.isEmpty(giamoi)){
                Toast.makeText(this, "Vui lòng nhập giá mới...", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        else {
            giamoi = "0";
            khuyenmaiphantram="";
        }
        capNhatKhoHang();
    }

    private void capNhatKhoHang() {
        //Show
        progressDialog.setMessage("Cập nhật kho hàng");
        progressDialog.show();
        if(filePath == null)
        {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("tenkho", ""+tenkho);
            hashMap.put("diachikhohang", ""+diachikhohang);
            hashMap.put("dientichkho", ""+dientichkho);
            hashMap.put("dienthoaikho", ""+dienthoaikho);
            hashMap.put("giachothue", ""+giachothue);
            hashMap.put("tinhtrangkho", ""+tinhtrangkho);
            hashMap.put("ghichukhho", ""+ghichu);
            hashMap.put("giamgiaAvailable", ""+giamgiaAvailable);
            hashMap.put("giamoi", ""+giamoi);
            hashMap.put("phantramkm", ""+khuyenmaiphantram);

            //cap nhat db
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tb_Users");
            reference.child(firebaseAuth.getUid()).child("KhoHang").child(khohangId)
                    .updateChildren(hashMap)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            //update
                            progressDialog.dismiss();
                            Toast.makeText(SuaKhoHangActivity.this, "Cập nhật thành công!",Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //update failed
                            progressDialog.dismiss();
                            Toast.makeText(SuaKhoHangActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }else {
            //update widh image
            String filePathName = "profile_images/" + ""+khohangId;// lay từ id của kho
            //cập nhật hình ảnh
            StorageReference storageReference = FirebaseStorage.getInstance().getReference(filePathName);
            storageReference.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!uriTask.isSuccessful());
                            Uri downloadUri = uriTask.getResult();
                            if (uriTask.isSuccessful()){
                                HashMap<String, Object> hashMap = new HashMap<>();
                                hashMap.put("tenkho", ""+tenkho);
                                hashMap.put("diachikhohang", ""+diachikhohang);
                                hashMap.put("dientichkho", ""+dientichkho);
                                hashMap.put("dienthoaikho", ""+dienthoaikho);
                                hashMap.put("giachothue", ""+giachothue);
                                hashMap.put("tinhtrangkho", ""+tinhtrangkho);
                                hashMap.put("ghichukhho", ""+ghichu);
                                hashMap.put("giamgiaAvailable", ""+giamgiaAvailable);
                                hashMap.put("giamoi", ""+giamoi);
                                hashMap.put("phantramkm", ""+khuyenmaiphantram);
                                hashMap.put("hinhanhkho", ""+downloadUri);//hinhanh
                                //cap nhat db
                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tb_Users");
                                reference.child(firebaseAuth.getUid()).child("KhoHang").child(khohangId)
                                        .updateChildren(hashMap)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                //update
                                                progressDialog.dismiss();
                                                Toast.makeText(SuaKhoHangActivity.this, "Cập nhật thành công!",Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                //update failed
                                                progressDialog.dismiss();
                                                Toast.makeText(SuaKhoHangActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //upload failed
                            progressDialog.dismiss();
                            Toast.makeText(SuaKhoHangActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void tinhtrangDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Tình trạng kho")
                .setItems(Constants_kho.options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String tinhtrang = Constants_kho.options[i];
                        tvtinh_trang_kho.setText(tinhtrang);
                    }
                })
                .show();
    }
    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                ivhinhanhkho.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void setControl() {
        khohangId = getIntent().getStringExtra("khohangId");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Sửa kho hàng");
        ivhinhanhkho = findViewById(R.id.ivavata_kho);
        txtten_kho_hang = findViewById(R.id.txtten_kho_khang);
        txtdiachi_kho_hang = findViewById(R.id.txtdiachi_kho_hang);
        txtdien_tich_kho = findViewById(R.id.txtdien_tich);
        txtsodt_kho = findViewById(R.id.txtso_dien_thoai_kho);
        txtghi_chu_kho = findViewById(R.id.txtghi_chu);
        txtgia_moi_kho = findViewById(R.id.txtgia_moi);
        txtgiacho_thue = findViewById(R.id.txtgia_cho_thue);
        txtgiamphantram_10 = findViewById(R.id.txtgiamtheophamtram);
        tvtinh_trang_kho = findViewById(R.id.spntinh_trang_kho);
        swgiamgia = findViewById(R.id.discountSwitch);
        btnsua_kho_hang = findViewById(R.id.btnsua_kho);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Vui lòng đợi...");
        progressDialog.setCanceledOnTouchOutside(false);
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