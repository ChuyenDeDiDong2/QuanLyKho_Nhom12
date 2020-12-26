package com.team12.quanlykhohang_nhom12.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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

public class SuaSanPhamActivity extends AppCompatActivity {

    private String khohangId, hanghoaId;
    Toolbar toolbar;
    private ImageView ivnew_product_photo;
    private TextView edtnew_product_name_product;
    private TextView edtnew_product_unit_product;
    private TextView edtnew_product_number;
    private TextView edtnew_product_price;
    private TextView edtnew_product_note;
    private Button btnnew_product_done;
    private Uri filePath;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    private final int PICK_IMAGE_REQUEST = 71;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_san_pham);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setControl();
        setEvent();
        loadHangDetail();
    }
    private void setEvent() {
        ivnew_product_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //su kien chon anh tu thu vien
                chooseImage();
            }
        });
        //
        btnnew_product_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputData();
            }
        });
    }

    private void loadHangDetail() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tb_Users");
        reference.child(firebaseAuth.getUid()).child("KhoHang").child(khohangId).child("HangHoa").child(hanghoaId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //get data
                        String khohangId = ""+snapshot.child("khohangId").getValue();
                        String hanghoaId = ""+snapshot.child("hanghoaId").getValue();
                        String tensanpham = ""+snapshot.child("tensanpham").getValue();
                        String donvi = ""+snapshot.child("donvi").getValue();
                        String ghichu = ""+snapshot.child("ghichu").getValue();
                        String dongia = ""+snapshot.child("dongia").getValue();
                        String soluong = ""+snapshot.child("soluong").getValue();
                        String hinhanhhang = ""+snapshot.child("hinhanhhang").getValue();
                        String timstamphh = ""+snapshot.child("timstamphh").getValue();
                        String uid = ""+snapshot.child("uid").getValue();
                        //set data to view

                        edtnew_product_name_product.setText(tensanpham);
                        edtnew_product_unit_product.setText(donvi);
                        edtnew_product_number.setText(soluong);
                        edtnew_product_price.setText(dongia);
                        edtnew_product_note.setText(ghichu);
                        try {
                            Picasso.get().load(hinhanhhang).placeholder(R.drawable.google).into(ivnew_product_photo);
                        }catch (Exception e){
                            ivnew_product_photo.setImageResource(R.drawable.google);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
    private String   tensanpham, donvi, hinhanhhang, ghichu, dongia, soluong;
    private void inputData() {
        tensanpham = edtnew_product_name_product.getText().toString().trim();
        donvi = edtnew_product_unit_product.getText().toString().trim();
        soluong = edtnew_product_number.getText().toString().trim();
        dongia = edtnew_product_price.getText().toString().trim();
        ghichu = edtnew_product_note.getText().toString().trim();

        if(TextUtils.isEmpty(tensanpham)){
            Toast.makeText(this, "Vui lòng nhập tên...", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(donvi)){
            Toast.makeText(this, "Vui lòng nhập đơn vị...", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(soluong)){
            Toast.makeText(this, "Vui lòng nhập số lượng...", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(dongia)){
            Toast.makeText(this, "Vui lòng nhập giá...", Toast.LENGTH_SHORT).show();
            return;
        }

        capNhatHang();
    }

    private void capNhatHang() {
        //Show
        progressDialog.setMessage("Cập nhật sản phẩm");
        progressDialog.show();
        if(filePath == null)
        {
            //tensanpham, donvi, hinhanhhang, ghichu, dongia, soluong;;
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("tensanpham", ""+tensanpham);
            hashMap.put("donvi", ""+donvi);
            hashMap.put("soluong", ""+soluong);
            hashMap.put("dongia", ""+dongia);
            hashMap.put("ghichu", ""+ghichu);
            //hashMap.put("hinhanhhang", "");//hinhanh

            //cap nhat db
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tb_Users");
            reference.child(firebaseAuth.getUid()).child("KhoHang").child(khohangId).child("HangHoa").child(hanghoaId)
                    .updateChildren(hashMap)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            //update
                            progressDialog.dismiss();
                            Toast.makeText(SuaSanPhamActivity.this, "Cập nhật thành công!",Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //update failed
                            progressDialog.dismiss();
                            Toast.makeText(SuaSanPhamActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
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
                                hashMap.put("tensanpham", ""+tensanpham);
                                hashMap.put("donvi", ""+donvi);
                                hashMap.put("soluong", ""+soluong);
                                hashMap.put("dongia", ""+dongia);
                                hashMap.put("ghichu", ""+ghichu);
                                hashMap.put("hinhanhhang", ""+downloadUri);//hinhanh
                                //cap nhat db
                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tb_Users");
                                reference.child(firebaseAuth.getUid()).child("KhoHang").child(khohangId).child("HangHoa").child(hanghoaId)
                                        .updateChildren(hashMap)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                //update
                                                progressDialog.dismiss();
                                                Toast.makeText(SuaSanPhamActivity.this, "Cập nhật thành công!",Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                //update failed
                                                progressDialog.dismiss();
                                                Toast.makeText(SuaSanPhamActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(SuaSanPhamActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
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
                ivnew_product_photo.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void setControl() {

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Sửa sản phẩm");

        khohangId = getIntent().getStringExtra("khohangId");
        hanghoaId = getIntent().getStringExtra("hanghoaId");
        ivnew_product_photo = findViewById(R.id.ivnew_product_photo);

        edtnew_product_name_product = findViewById(R.id.edtnew_product_name_product);
        edtnew_product_unit_product = findViewById(R.id.edtnew_product_unit_product);
        edtnew_product_number = findViewById(R.id.edtnew_product_number);
        edtnew_product_price = findViewById(R.id.edtnew_product_price);
        edtnew_product_note = findViewById(R.id.edtnew_product_note);
        btnnew_product_done = findViewById(R.id.btnnew_product_done);

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