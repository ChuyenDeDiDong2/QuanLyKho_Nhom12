package com.team12.quanlykhohang_nhom12.Fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

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
import com.team12.quanlykhohang_nhom12.Activity.AdminKhoActivity;
import com.team12.quanlykhohang_nhom12.Activity.DangNhapActivity;
import com.team12.quanlykhohang_nhom12.Activity.HomeToRentActivity;
import com.team12.quanlykhohang_nhom12.Activity.SuaKhoHangActivity;
import com.team12.quanlykhohang_nhom12.Activity.ThemTaiKhoanActivity;
import com.team12.quanlykhohang_nhom12.R;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public class TaiKhoanKhoFragment extends Fragment {

    //private ImageButton backbtn;
    private TextView txtEmailad,txtten_tai_khoan_chukhoad,tvdongcua;
    private EditText txtten_cua_banad,txtdienthoaiad,txtsotaikhoanad,txtdiachiad;
    private ImageView tk_kho_icon;
    private Button btnupdate;
    private SwitchCompat dongmoAvailable;
    private FirebaseAuth firebaseAuth;
    //
    private Uri filePath;
    private ProgressDialog progressDialog;

    private final int PICK_IMAGE_REQUEST = 71;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.row_chitiet_tk_kho, container, false);

        //backbtn = root.findViewById(R.id.backbtn);
        //
        txtten_tai_khoan_chukhoad = root.findViewById(R.id.txtten_tai_khoan_chukhoad);
        txtEmailad = root.findViewById(R.id.txtEmailad);
        txtten_cua_banad = root.findViewById(R.id.txtten_cua_banad);
        txtdienthoaiad = root.findViewById(R.id.txtdienthoaiad);
        txtsotaikhoanad = root.findViewById(R.id.txtsotaikhoanad);
        txtdiachiad = root.findViewById(R.id.txtdiachiad);
        //
        tk_kho_icon = root.findViewById(R.id.tk_kho_icon);
        btnupdate = root.findViewById(R.id.btnupdate);
        dongmoAvailable = root.findViewById(R.id.swdongmo);

        //
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("đợi");
        progressDialog.setCanceledOnTouchOutside(false);

        firebaseAuth = FirebaseAuth.getInstance();
        loadKhoHangDetail();
        checkUserType();

//        backbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(root.getContext(), AdminKhoActivity.class));
//            }
//        });
        tk_kho_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputData();

            }
        });
        dongmoAvailable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    open = "true";
                }else {
                    open = "false";
                }
            }
        });
        return root;
    }

    private void loadKhoHangDetail() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tb_Users");
        reference.child(firebaseAuth.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //get data
                        String khohangId = ""+snapshot.child("khohangId").getValue();
                        String name = ""+snapshot.child("name").getValue();
                        String phone = ""+snapshot.child("phone").getValue();
                        String email = ""+snapshot.child("email").getValue();
                        String sotaikhoan = ""+snapshot.child("sotaikhoan").getValue();
                        String tentaikhoan = ""+snapshot.child("tentaikhoan").getValue();
                        String diachi = ""+snapshot.child("diachi").getValue();
                        String accountType = ""+snapshot.child("accountType").getValue();
                        String online = ""+snapshot.child("online").getValue();
                        String open = ""+snapshot.child("open").getValue();//String giamgiaAvailable = ""+snapshot.child("giamgiaAvailable").getValue();
                        String noibat = ""+snapshot.child("noibat").getValue();
                        String profileImage = ""+snapshot.child("profileImage").getValue();
                        String typingTo = ""+snapshot.child("typingTo").getValue();
                        String uid = ""+snapshot.child("uid").getValue();
                        String dongmoAvailable = ""+snapshot.child("dongmoAvailable").getValue();
                        //set data to view
                        txtten_cua_banad.setText(name);
                        txtEmailad.setText(email);
                        txtdienthoaiad.setText(phone);
                        txtsotaikhoanad.setText(sotaikhoan);
                        txtten_tai_khoan_chukhoad.setText(tentaikhoan);
                        txtdiachiad.setText(diachi);
                        try {
                            Picasso.get().load(profileImage).placeholder(R.drawable.google).into(tk_kho_icon);
                        }catch (Exception e){
                            tk_kho_icon.setImageResource(R.drawable.google);
                        }
                        if(open.equals("true")){
                            TaiKhoanKhoFragment.this.dongmoAvailable.setChecked(true);

                        }else {

                            TaiKhoanKhoFragment.this.dongmoAvailable.setChecked(false);

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
    //hien thi (giua kho va thue)
    private void checkUserType() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tb_Users");
        reference.orderByChild("uid").equalTo(firebaseAuth.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds: snapshot.getChildren()){
                            String accountType =""+ds.child("accountType").getValue();
                            if(accountType.equals("user")){
                                txtten_tai_khoan_chukhoad.setVisibility(View.GONE);
                                txtsotaikhoanad.setVisibility(View.GONE);
                                dongmoAvailable.setVisibility(View.GONE);
                                tvdongcua.setVisibility(View.GONE);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    //lấy dữ liệu
    private String name,phone ,email, sotaikhoan, tentaikhoan, diachi, accountType, online, open, noibat, profileImage, typingTo;
    private boolean dongAvailable  = true;
    private void inputData() {
        //tentaikhoan = txtten_tai_khoan_chukhoad.getText().toString().trim();
        name = txtten_cua_banad.getText().toString().trim();
        email = txtEmailad.getText().toString().trim();
        phone = txtdienthoaiad.getText().toString().trim();
        sotaikhoan = txtsotaikhoanad.getText().toString().trim();
        diachi = txtdiachiad.getText().toString().trim();

        dongAvailable = dongmoAvailable.isChecked();//true/false
        if(TextUtils.isEmpty(name)){
            Toast.makeText(getActivity(), "Vui lòng nhập tên...", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(phone)){
            Toast.makeText(getActivity(), "Vui lòng nhập số điện thoại...", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(sotaikhoan)){
            Toast.makeText(getActivity(), "Vui lòng nhập số tài khoản...", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(diachi)){
            Toast.makeText(getActivity(), "Vui lòng nhập địa chỉ...", Toast.LENGTH_SHORT).show();
            return;
        }
        capNhatKhoHang();
    }

    //cập nhật
    private void capNhatKhoHang() {
        //Show
        progressDialog.setMessage("Cập nhật tài khoản");
        progressDialog.show();
        if(filePath == null)
        {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("name", ""+name);
            hashMap.put("email", ""+email);
            hashMap.put("phone", ""+phone);
            hashMap.put("sotaikhoan", ""+sotaikhoan);
            hashMap.put("diachi", ""+diachi);
            hashMap.put("open", ""+open);


            //cap nhat db
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tb_Users");
            reference.child(firebaseAuth.getUid())
                    .updateChildren(hashMap)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            //update
                            progressDialog.dismiss();
                            Toast.makeText(TaiKhoanKhoFragment.this.getActivity(), "Cập nhật thành công!",Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //update failed
                            progressDialog.dismiss();
                            Toast.makeText(TaiKhoanKhoFragment.this.getActivity(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }else {
            //update widh image
            String filePathName = "profile_images/" + ""+firebaseAuth.getUid();// lay từ id của kho
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
                                hashMap.put("name", ""+name);
                                hashMap.put("email", ""+email);
                                hashMap.put("phone", ""+phone);
                                hashMap.put("sotaikhoan", ""+sotaikhoan);
                                hashMap.put("diachi", ""+diachi);
                                hashMap.put("open", ""+open);
                                hashMap.put("profileImage", ""+downloadUri);//hinhanh
                                //cap nhat db
                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tb_Users");
                                reference.child(firebaseAuth.getUid())
                                        .updateChildren(hashMap)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                //update
                                                progressDialog.dismiss();
                                                Toast.makeText(TaiKhoanKhoFragment.this.getActivity(), "Cập nhật thành công!",Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                //update failed
                                                progressDialog.dismiss();
                                                Toast.makeText(TaiKhoanKhoFragment.this.getActivity(),  ""+e.getMessage(), Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(TaiKhoanKhoFragment.this.getActivity(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    //lấy hình ảnh
    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                tk_kho_icon.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }


}
