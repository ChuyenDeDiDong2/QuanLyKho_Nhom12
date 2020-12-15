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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.team12.quanlykhohang_nhom12.R;

import java.io.IOException;
import java.util.HashMap;

public class NewProductActivity extends AppCompatActivity {
    Toolbar toolbar;
    private ImageView ivhinhanhhang;
    private EditText txtloaihang,txtsoluong,txtxuatsu;
    private Button btnThem_hang;

    private Uri filePath;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    private final int PICK_IMAGE_REQUEST = 71;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setControls();
        setEvent();
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
        btnThem_hang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputData();
            }
        });
    }

    private String loaihang,soluong,xuatxu;
    private boolean giamgiaAvailable  =false;
    private void inputData() {
        loaihang = txtloaihang.getText().toString().trim();
        soluong = txtsoluong.getText().toString().trim();
        xuatxu = txtxuatsu.getText().toString().trim();
        if(TextUtils.isEmpty(loaihang)){
            Toast.makeText(this, "Vui lòng nhập loại kho...", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(soluong)){
            Toast.makeText(this, "Vui lòng nhập số lượng...", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(xuatxu)){
            Toast.makeText(this, "Vui lòng nhập nơi xuất xứ...", Toast.LENGTH_SHORT).show();
            return;
        }
        addHang();

    }

    private void addHang() {
        progressDialog.setMessage("Thêm hàng...");
        progressDialog.show();
        String timestamp = ""+System.currentTimeMillis();

        if (filePath == null){
            //upload hinh anh
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("khohangId", ""+timestamp);
            hashMap.put("loaihang", ""+loaihang);
            hashMap.put("soluong", ""+soluong);
            hashMap.put("xuatsu", ""+xuatxu);
            hashMap.put("hinhanhkho", "");//hinhanh
            hashMap.put("timstamp", ""+timestamp);//
            hashMap.put("uid", ""+firebaseAuth.getUid());//
            //add to db
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tb_Users");
            reference.child(firebaseAuth.getUid()).child("KhoHang").child(timestamp).setValue(hashMap)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            //
                            progressDialog.dismiss();
                            Toast.makeText(NewProductActivity.this, "Thêm kho hàng  thành công...", Toast.LENGTH_SHORT).show();
                            clearData();
                        }
                    })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //
                            progressDialog.dismiss();
                            Toast.makeText(NewProductActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

        }else {
            String filePathName = "profile_images/" + ""+timestamp;
            //upload image
            StorageReference storageReference = FirebaseStorage.getInstance().getReference(filePathName);
            storageReference.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //
                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!uriTask.isSuccessful());
                            Uri downloadImageUri =uriTask.getResult();

                            if (uriTask.isSuccessful()){
                                //upload hinh anh
                                HashMap<String, Object> hashMap = new HashMap<>();
                                hashMap.put("khohangId", ""+timestamp);
                                hashMap.put("loaihang", ""+loaihang);
                                hashMap.put("soluong", ""+soluong);
                                hashMap.put("xuatxu", ""+xuatxu);
                                hashMap.put("hinhanhkho", ""+downloadImageUri);//hinhanh
                                hashMap.put("timstamp", ""+timestamp);//
                                hashMap.put("uid", ""+firebaseAuth.getUid());//
                                //add to db
                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tb_Users");
                                reference.child(firebaseAuth.getUid()).child("KhoHang").child(timestamp).setValue(hashMap)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                //
                                                progressDialog.dismiss();
                                                Toast.makeText(NewProductActivity.this, "Thêm kho hàng thành công...", Toast.LENGTH_SHORT).show();
                                                clearData();
                                            }
                                        })

                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                //
                                                progressDialog.dismiss();
                                                Toast.makeText(NewProductActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //
                            progressDialog.dismiss();
                            Toast.makeText(NewProductActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });

        }
    }

    private void clearData() {
        txtloaihang.setText("");
        txtsoluong.setText("");
        txtxuatsu.setText("");
        ivhinhanhhang.setImageResource(R.drawable.ic_avartar_48);
        filePath = null;
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


    private void setControls() {

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Thêm kho hàng");

        ivhinhanhhang= findViewById(R.id.ivavata_kho);
        txtloaihang = findViewById(R.id.txtloaihang);
        txtsoluong = findViewById(R.id.txtsoluong);
        txtxuatsu = findViewById(R.id.txtxuatxu);
        btnThem_hang = findViewById(R.id.btnthem_hang);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Vui lòng đợi");
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