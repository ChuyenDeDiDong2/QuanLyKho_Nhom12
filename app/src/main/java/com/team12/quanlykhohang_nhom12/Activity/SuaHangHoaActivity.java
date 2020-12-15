package com.team12.quanlykhohang_nhom12.Activity;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
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
import com.team12.quanlykhohang_nhom12.R;

import java.io.IOException;
import java.util.HashMap;

public class SuaHangHoaActivity extends AppCompatActivity {
    private String hanghoaId;
    Toolbar toolbar;
    private ImageView ivhinhanhhang;
    private EditText txtloaihang,txtsoluong,txtxuatxu;
    private Button btnsua_hang;

    private Uri filePath;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    private final int PICK_IMAGE_REQUEST = 71;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_hanghoa);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setControl();
        setEvent();
        loadKhoHangDetail();
    }

    private void loadKhoHangDetail() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tb_Users");
        reference.child(firebaseAuth.getUid()).child("KhoHang").child(hanghoaId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //get data
                        String hanghoaID = ""+snapshot.child("hanghoaID").getValue();
                        String loaihang = ""+snapshot.child("loaihang").getValue();
                        String soluong = ""+snapshot.child("soluong").getValue();
                        String xuatxu = ""+snapshot.child("xuatxu").getValue();
                        String hinhanhhang = ""+snapshot.child("hinhanhhang").getValue();
                        String timstamp = ""+snapshot.child("timstamp").getValue();
                        String uid = ""+snapshot.child("uid").getValue();
                        //set data to view
                        txtloaihang.setText(loaihang);
                        txtsoluong.setText(soluong);
                        txtxuatxu.setText(xuatxu);
                        try {
                            Picasso.get().load(hinhanhhang).placeholder(R.drawable.google).into(ivhinhanhhang);
                        }catch (Exception e){
                            ivhinhanhhang.setImageResource(R.drawable.google);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void setEvent() {
        ivhinhanhhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //su kien chon anh tu thu vien
                chooseImage();
            }
        });
        //
        btnsua_hang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputData();
            }
        });
    }
    private String loaihang,soluong,xuatxu;
    private void inputData() {
        loaihang = txtloaihang.getText().toString().trim();
        soluong = txtsoluong.getText().toString().trim();
        xuatxu = txtxuatxu.getText().toString().trim();
        if(TextUtils.isEmpty(loaihang)){
            Toast.makeText(this, "Vui lòng nhập loại hàng...", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(soluong)){
            Toast.makeText(this, "Vui lòng nhập số lượng...", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(xuatxu)){
            Toast.makeText(this, "Vui lòng nhập xuất xứ...", Toast.LENGTH_SHORT).show();
            return;
        }
        CapNhatHang();
    }

    private void CapNhatHang() {
        //Show
        progressDialog.setMessage("Cập nhật hàng hóa");
        progressDialog.show();
        if(filePath == null)
        {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("loaihang", ""+loaihang);
            hashMap.put("soluong", ""+soluong);
            hashMap.put("xuatxu", ""+xuatxu);

            //cap nhat db
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tb_Users");
            reference.child(firebaseAuth.getUid()).child("KhoHang").child(hanghoaId)
                    .updateChildren(hashMap)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            //update
                            progressDialog.dismiss();
                            Toast.makeText(SuaHangHoaActivity.this, "Cập nhật thành công!",Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //update failed
                            progressDialog.dismiss();
                            Toast.makeText(SuaHangHoaActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }else {
            //update widh image
            String filePathName = "profile_images/" + ""+hanghoaId;// lay từ id của kho
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
                                hashMap.put("loaihang", ""+loaihang);
                                hashMap.put("soluong", ""+soluong);
                                hashMap.put("xuatxu", ""+xuatxu);
                                hashMap.put("hinhanhkho", ""+downloadUri);//hinhanh
                                //cap nhat db
                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tb_Users");
                                reference.child(firebaseAuth.getUid()).child("KhoHang").child(hanghoaId)
                                        .updateChildren(hashMap)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                //update
                                                progressDialog.dismiss();
                                                Toast.makeText(SuaHangHoaActivity.this, "Cập nhật thành công!",Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                //update failed
                                                progressDialog.dismiss();
                                                Toast.makeText(SuaHangHoaActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(SuaHangHoaActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
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
                ivhinhanhhang.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void setControl() {
        hanghoaId = getIntent().getStringExtra("hanghoaId");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Sửa hàng hóa");
        ivhinhanhhang = findViewById(R.id.ivavata_hang);
        txtloaihang = findViewById(R.id.txtloaihang);
        txtsoluong = findViewById(R.id.txtsoluong);
        txtxuatxu = findViewById(R.id.txtxuatxu);
        btnsua_hang = findViewById(R.id.btnsua_kho);

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
