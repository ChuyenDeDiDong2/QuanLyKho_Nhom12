package com.team12.quanlykhohang_nhom12.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.team12.quanlykhohang_nhom12.Library.Constants_kho;
import com.team12.quanlykhohang_nhom12.R;

import java.io.IOException;
import java.util.HashMap;

public class DangKyChoThueKhoActivity extends AppCompatActivity {
    private ImageView btnBack, ivprofile;
    private EditText txtEmail,txtdiachiad, txtPhone, txtName, txtPassword, txtRePassword, txtTenTaiKhoan, txtSoTaiKhoan;
    private Button btnDangKy;
    private TextView txttinhthanh;
    private Uri filePath;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    private final int PICK_IMAGE_REQUEST = 71;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky_cho_thue_kho);
        getControl();
        getEvent();
    }
    private void getEvent() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //su kien quay lai
                onBackPressed();
            }
        });
        ivprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //su kien chon anh tu thu vien
                chooseImage();
            }
        });
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //su kien khi nhan vao nut dang ky
                inputData();
            }
        });
        txttinhthanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                tinhthanhDialog();
            }
        });
    }
    private void tinhthanhDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Tỉnh thành")
                .setItems(Constants_kho.options3, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String tinhthanh = Constants_kho.options3[i];
                        txttinhthanh.setText(tinhthanh);
                    }
                })
                .show();
    }
    private String ten,diachi,  pass, repass, email, soDT, tenTaiKhoan, sotaikhoan, tinhthanh;
    private void inputData() {
        ten = txtName.getText().toString().trim();
        tenTaiKhoan = txtTenTaiKhoan.getText().toString().trim();
        diachi = txtdiachiad.getText().toString().trim();
        tinhthanh = txttinhthanh.getText().toString().trim();
        pass = txtPassword.getText().toString().trim();
        repass= txtRePassword.getText().toString().trim();
        email= txtEmail .getText().toString().trim();
        soDT = txtPhone.getText().toString().trim();
        sotaikhoan = txtSoTaiKhoan.getText().toString().trim();


        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this, "Vui lòng nhập email của bạn...", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(ten)){
            Toast.makeText(this, "Vui lòng nhập họ tên của bạn...", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(tenTaiKhoan)){
            Toast.makeText(this, "Vui lòng nhập họ tên tài khoản...", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(soDT)){
            Toast.makeText(this, "Vui lòng nhập số điện thoại của bạn...", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(diachi)){
            Toast.makeText(this, "Vui lòng nhập số điện thoại của bạn...", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(tinhthanh)){
            Toast.makeText(this, "Vui lòng chọn tỉnh thành/thành phố...", Toast.LENGTH_SHORT).show();
            return;
        }
        if(pass.length()<8){
            Toast.makeText(this, "Vui lòng nhập mật khẩu...", Toast.LENGTH_SHORT).show();
            return;
        }
        if(sotaikhoan.length()<14){
            Toast.makeText(this, "Vui lòng nhập đúng số tài khoản...", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!pass.equals(repass)){
            Toast.makeText(this, "Mật khẩu không đúng...", Toast.LENGTH_SHORT).show();
            return;
        }


        createAccount();
    }

    private void createAccount() {
        progressDialog.setMessage("Tạo tài khoản...");
        progressDialog.show();
        //
        firebaseAuth.createUserWithEmailAndPassword(email, pass)
                .addOnSuccessListener(this, new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        saverFirebaseData();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(DangKyChoThueKhoActivity.this, "" +e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void saverFirebaseData() {
        progressDialog.setMessage("Lưu thông tin tài khoản");
        String timestanp = ""+System.currentTimeMillis();
        if(filePath==null){
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("uid", ""+firebaseAuth.getUid());
            hashMap.put("name", ""+ten);
            hashMap.put("phone", ""+soDT);
            hashMap.put("email", ""+email);
            hashMap.put("sotaikhoan", ""+sotaikhoan);
            hashMap.put("tentaikhoan", ""+tenTaiKhoan);
            hashMap.put("diachi", ""+diachi);
            hashMap.put("tinhthanh", ""+tinhthanh);
            hashMap.put("accountType", "admin");
            hashMap.put("online", "true");
            hashMap.put("block", "false");
            hashMap.put("open", "true");
            hashMap.put("noibat", "true");
            hashMap.put("diemtb", "0");
            hashMap.put("profileImage", "");

            //luu db
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tb_Users");
            reference.child(firebaseAuth.getUid()).setValue(hashMap)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            progressDialog.dismiss();
                            startActivity(new Intent(DangKyChoThueKhoActivity.this, AdminKhoActivity.class));
                            finish();;
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            startActivity(new Intent(DangKyChoThueKhoActivity.this, AdminKhoActivity.class));
                            finish();
                        }
                    });
        }
        else {
            String filePathName = "profile_images/" + ""+firebaseAuth.getUid();
            //upload image
            StorageReference storageReference = FirebaseStorage.getInstance().getReference(filePathName);
            storageReference.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!uriTask.isSuccessful());
                            Uri downloadIamgeUri = uriTask.getResult();
                            if(uriTask.isSuccessful()){
                                HashMap<String, Object> hashMap = new HashMap<>();
                                hashMap.put("uid", ""+firebaseAuth.getUid());
                                hashMap.put("name", ""+ten);
                                hashMap.put("phone", ""+soDT);
                                hashMap.put("email", ""+email);
                                hashMap.put("sotaikhoan", ""+sotaikhoan);
                                hashMap.put("tentaikhoan", ""+tenTaiKhoan);
                                hashMap.put("diachi", ""+diachi);
                                hashMap.put("tinhthanh", ""+tinhthanh);
                                hashMap.put("accountType", "admin");
                                hashMap.put("online", "true");
                                hashMap.put("block", "false");
                                hashMap.put("open", "true");
                                hashMap.put("noibat", "true");
                                hashMap.put("diemtb", "0");
                                hashMap.put("profileImage", ""+downloadIamgeUri);//url upload image

                                //luu db
                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tb_Users");
                                reference.child(firebaseAuth.getUid()).setValue(hashMap)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                progressDialog.dismiss();
                                                startActivity(new Intent(DangKyChoThueKhoActivity.this, AdminKhoActivity.class));
                                                finish();;
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                progressDialog.dismiss();
                                                startActivity(new Intent(DangKyChoThueKhoActivity.this, AdminKhoActivity.class));
                                                finish();
                                            }
                                        });
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(DangKyChoThueKhoActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
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
                ivprofile.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    private void getControl() {

        btnBack = findViewById(R.id.iv_quaylai);
        ivprofile = findViewById(R.id.ivtai_khoan);
        txtTenTaiKhoan = findViewById(R.id.txtten_tai_khoan_chukhoad);
        txtEmail = findViewById(R.id.txtEmailad);
        txtPhone = findViewById(R.id.txtdienthoaiad);
        txtName = findViewById(R.id.txtten_cua_banad);
        txtSoTaiKhoan = findViewById(R.id.txtsotaikhoanad);
        txtdiachiad = findViewById(R.id.txtdiachiad);
        txtPassword = findViewById(R.id.txtmatkhauad);
        txtRePassword = findViewById(R.id.txtnhap_lai_mat_khauad);
        txttinhthanh = findViewById(R.id.tvTinhThanh);
        btnDangKy = findViewById(R.id.btndangkyad);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Vui lòng chờ trong giây lát");
        progressDialog.setCanceledOnTouchOutside(false);
    }
}