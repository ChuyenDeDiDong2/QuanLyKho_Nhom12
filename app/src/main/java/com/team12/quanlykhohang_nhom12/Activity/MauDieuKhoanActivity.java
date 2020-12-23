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
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import java.io.IOException;
import java.util.HashMap;

public class MauDieuKhoanActivity extends AppCompatActivity {

    Toolbar toolbar;
    private ImageView ivavatarMDK;
    private Button btnSua;
    private ImageView hinhanhMDK;

    private Uri filePath;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    private final int PICK_IMAGE_REQUEST = 71;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mau_dieu_khoan);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setControls();
        setEvent();
        loadKhoHangDetail();
    }

    private void setEvent() {
        ivavatarMDK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
        btnSua.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                capNhatKhoHang();
            }
        });
    }

    private void loadKhoHangDetail() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tb_Users");
        reference.child(firebaseAuth.getUid()).child("MauDieuKhoan").child("Mau")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //get data
                        String hinhMDK = ""+snapshot.child("hinhMDK").getValue();
                        String maMDK = ""+snapshot.child("maMDK").getValue();
                        String uid = ""+snapshot.child("uid").getValue();
                        //set data to view

                        try {
                            Picasso.get().load(hinhMDK).placeholder(R.drawable.google).into(ivavatarMDK);
                        }catch (Exception e){
                            ivavatarMDK.setImageResource(R.drawable.google);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }


    private void capNhatKhoHang() {
        //Show
        progressDialog.setMessage("Cập nhật kho hàng");
        progressDialog.show();
        if(filePath == null)
        {
            HashMap<String, Object> hashMap = new HashMap<>();

            //cap nhat db
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tb_Users");
            reference.child(firebaseAuth.getUid()).child("MauDieuKhoan")
                    .updateChildren(hashMap)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            //update
                            progressDialog.dismiss();
                            Toast.makeText(MauDieuKhoanActivity.this, "Cập nhật thành công!",Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //update failed
                            progressDialog.dismiss();
                            Toast.makeText(MauDieuKhoanActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }else {
            //update widh image
            String filePathName = "profile_images/MauDieuKhoan" ;// lay từ id của kho
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
                                hashMap.put("hinhMDK", ""+downloadUri);//hinhanh
                                //cap nhat db
                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tb_Users");
                                reference.child(firebaseAuth.getUid()).child("MauDieuKhoan")
                                        .updateChildren(hashMap)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                //update
                                                progressDialog.dismiss();
                                                Toast.makeText(MauDieuKhoanActivity.this, "Cập nhật thành công!",Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                //update failed
                                                progressDialog.dismiss();
                                                Toast.makeText(MauDieuKhoanActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(MauDieuKhoanActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }




//    private void inputData() {
//        addMauDK();
//    }
//
//    private void addMauDK() {
//        progressDialog.setMessage("Thêm mẫu điều khoản ...");
//        progressDialog.show();
//        String timestamp = ""+System.currentTimeMillis();
//
//        if(filePath == null) {
//            HashMap<String, Object> hashMap = new HashMap<>();
//            hashMap.put("maMDK", ""+timestamp);
//            hashMap.put("hinhMDK", "");
//            hashMap.put("uid", ""+firebaseAuth.getUid());
//
//            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tb_Users");
//            reference.child(firebaseAuth.getUid()).child("MauDieuKhoan").child(timestamp).setValue(hashMap)
//                    .addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
//                            progressDialog.dismiss();
//                            Toast.makeText(MauDieuKhoanActivity.this, "Tạo mẫu điều khoản thành công", Toast.LENGTH_SHORT).show();
//                            clearData();
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            progressDialog.dismiss();
//                            Toast.makeText(MauDieuKhoanActivity.this, ""+e.getMessage(),Toast.LENGTH_SHORT).show();
//                        }
//                    });
//         } else {
//            String filePathName = "profile_images/" + ""+timestamp;
//            StorageReference storageReference = FirebaseStorage.getInstance().getReference(filePathName);
//            storageReference.putFile(filePath)
//                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
//                            while (!uriTask.isSuccessful());
//                            Uri downloadImageUri = uriTask.getResult();
//
//                            if(uriTask.isSuccessful()) {
//                                HashMap<String, Object> hashMap = new HashMap<>();
//                                hashMap.put("maMDK", ""+timestamp);
//                                hashMap.put("hinhMDK", ""+downloadImageUri);
//                                hashMap.put("uid", ""+firebaseAuth.getUid());
//
//                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tb_Users");
//                                reference.child(firebaseAuth.getUid()).child("MauDieuKhoan").child(timestamp).setValue(hashMap)
//                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                            @Override
//                                            public void onSuccess(Void aVoid) {
//                                                progressDialog.dismiss();
//                                                Toast.makeText(MauDieuKhoanActivity.this, "Thêm mẫu điều khoản", Toast.LENGTH_SHORT).show();
//                                                clearData();
//                                            }
//                                        })
//
//                                        .addOnFailureListener(new OnFailureListener() {
//                                            @Override
//                                            public void onFailure(@NonNull Exception e) {
//                                                progressDialog.dismiss();
//                                                Toast.makeText(MauDieuKhoanActivity.this, ""+e.getMessage(),Toast.LENGTH_SHORT).show();
//                                            }
//                                        });
//                            }
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            progressDialog.dismiss();
//                            Toast.makeText(MauDieuKhoanActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    });
//        }
//    }
//
//    private void clearData() {
//        ivavatarMDK.setImageResource(R.drawable.ic_mauhopdong);
//        filePath = null;
//    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    private void setControls() {

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Sửa mẫu hợp đồng");

        ivavatarMDK = findViewById(R.id.imgIDavatarMDK);
        btnSua = findViewById(R.id.btnSua);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Vui lòng đợi");
        progressDialog.setCanceledOnTouchOutside(false);
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
                ivavatarMDK.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
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