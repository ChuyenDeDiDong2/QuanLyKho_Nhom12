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
import android.widget.EditText;
import android.widget.ImageView;
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

public class ThemSliderActivity extends AppCompatActivity {
    Toolbar toolbar;
    private Uri filePath;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private ImageView ivthemslider;
    private EditText tv_Tieudesider,tv_link;
    private Button btnthemslider;

    private final int PICK_IMAGE_REQUEST = 71;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_slider);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setControls();
        setEvent();
    }
    private void setEvent() {
        ivthemslider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //su kien chon anh tu thu vien
                chooseImage();
            }
        });
        //
        btnthemslider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputData();
            }
        });


    }

    private String tieude,link;

    private void inputData() {
        tieude = tv_Tieudesider.getText().toString().trim();
        link = tv_link.getText().toString().trim();

        addslider();

    }

    private void addslider() {
        progressDialog.setMessage("Thêm slider...");
        progressDialog.show();
        String timestamp = ""+System.currentTimeMillis();

        if (filePath == null){
            //upload hinh anh
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("tieude", ""+tieude);
            hashMap.put("link", ""+link);
            hashMap.put("hinhanh", "");//hinhanh
            hashMap.put("uid", ""+timestamp);//
            //add to db
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tb_Slider");
            reference.child(timestamp).setValue(hashMap)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            //
                            progressDialog.dismiss();
                            Toast.makeText(ThemSliderActivity.this, "Thêm thành công...", Toast.LENGTH_SHORT).show();
                            clearData();
                        }
                    })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //
                            progressDialog.dismiss();
                            Toast.makeText(ThemSliderActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
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
                                hashMap.put("tieude", ""+tieude);
                                hashMap.put("link", ""+link);
                                hashMap.put("hinhanh",  ""+downloadImageUri);//hinhanh
                                hashMap.put("uid", ""+timestamp);//
                                //add to db
                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tb_Slider");
                                reference.child(timestamp).setValue(hashMap)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                //
                                                progressDialog.dismiss();
                                                Toast.makeText(ThemSliderActivity.this, "Thêm thành công...", Toast.LENGTH_SHORT).show();
                                                clearData();
                                            }
                                        })

                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                //
                                                progressDialog.dismiss();
                                                Toast.makeText(ThemSliderActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(ThemSliderActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });

        }
    }

    private void clearData() {
        tv_Tieudesider.setText("");
        tv_link.setText("");

        ivthemslider.setImageResource(R.drawable.ic_avartar_48);
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
                ivthemslider.setImageBitmap(bitmap);
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

        ivthemslider = findViewById(R.id.ivthemslider);
        tv_Tieudesider = findViewById(R.id.tv_Tieudesider);
        tv_link = findViewById(R.id.tv_link);
        btnthemslider = findViewById(R.id.btnthemslider);

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