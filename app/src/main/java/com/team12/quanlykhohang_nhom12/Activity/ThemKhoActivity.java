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
import com.team12.quanlykhohang_nhom12.Library.Constants_kho;
import com.team12.quanlykhohang_nhom12.R;

import java.io.IOException;
import java.util.HashMap;

public class ThemKhoActivity extends AppCompatActivity {
    Toolbar toolbar;
    private ImageView ivhinhanhkho;
    private EditText txtten_kho_hang,txtdiachi_kho_hang, txtdien_tich_kho, txtsodt_kho, txtgiacho_thue, txtghi_chu_kho, txtgia_moi_kho, txtgiamphantram_10;
    private TextView tvtinh_trang_kho;
    private SwitchCompat swgiamgia;
    private Button btnThem_kho_hang;

    private Uri filePath;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    private final int PICK_IMAGE_REQUEST = 71;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_kho);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setControls();
        setEvent();
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
        btnThem_kho_hang.setOnClickListener(new View.OnClickListener() {
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
        diachikhohang = txtdiachi_kho_hang.getText().toString().trim();
        dientichkho = txtdien_tich_kho.getText().toString().trim();
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
        addKhoHang();

    }

    private void addKhoHang() {
        progressDialog.setMessage("Thêm kho hàng...");
        progressDialog.show();
        String timestamp = ""+System.currentTimeMillis();

        if (filePath == null){
            //upload hinh anh
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("khohangId", ""+timestamp);

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
                            Toast.makeText(ThemKhoActivity.this, "Thêm kho hàng  thành công...", Toast.LENGTH_SHORT).show();
                            clearData();
                        }
                    })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //
                            progressDialog.dismiss();
                            Toast.makeText(ThemKhoActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
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
                                                Toast.makeText(ThemKhoActivity.this, "Thêm kho hàng thành công...", Toast.LENGTH_SHORT).show();
                                                clearData();
                                            }
                                        })

                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                //
                                                progressDialog.dismiss();
                                                Toast.makeText(ThemKhoActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(ThemKhoActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });

        }
    }

    private void clearData() {
        txtten_kho_hang.setText("");

        txtdiachi_kho_hang.setText("");
        txtdien_tich_kho.setText("");
        txtsodt_kho.setText("");
        txtghi_chu_kho.setText("");
        txtgia_moi_kho.setText("");
        txtgiacho_thue.setText("");
        txtgiamphantram_10.setText("");
        tvtinh_trang_kho.setText("");
        swgiamgia.setText("");
        ivhinhanhkho.setImageResource(R.drawable.ic_avartar_48);
        filePath = null;
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


    private void setControls() {

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Thêm kho hàng");

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
        btnThem_kho_hang = findViewById(R.id.btnthem_kho);

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