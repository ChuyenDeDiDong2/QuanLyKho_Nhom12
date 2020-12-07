package com.team12.quanlykhohang_nhom12.Fragment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.team12.quanlykhohang_nhom12.Activity.HomeToRentActivity;
import com.team12.quanlykhohang_nhom12.Library.Users;
import com.team12.quanlykhohang_nhom12.R;

import static android.app.Activity.RESULT_OK;

public class SignupFragment extends Fragment implements View.OnClickListener {
    Button btnDangKy;
    EditText txtEmail, txtSoDT, txtDiaChi, txtTen, txtpass, txtSoTK, loaitk;
    ImageView profileiv;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    //
    private static final int CAMERA_REQUEST_CODE = 200;
    private static final int STORAGE_REQUEST_CODE = 300;
    private static final int IMAGE_PICK_GALLERY_CODE = 400;
    private static final int IMAGE_PICK_CAMERA_CODE = 500;
    //
    private String[] cameraPermissions;
    private String[] storagePermissions;
    //
    private Uri image_uri;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.signup_fragment, container, false);
        mAuth = FirebaseAuth.getInstance();
        progressBar =(ProgressBar) root.findViewById(R.id.progressBar1);
        btnDangKy = (Button) root.findViewById(R.id.btnsignup);
        btnDangKy.setOnClickListener(this);

        txtEmail = (EditText) root.findViewById(R.id.txtemail);
        txtSoDT = (EditText) root.findViewById(R.id.txtphone);
        txtDiaChi = (EditText) root.findViewById(R.id.txtdiachi);
        txtSoTK = (EditText) root.findViewById(R.id.sotaikhoan);
        txtTen = (EditText) root.findViewById(R.id.txtten);
        loaitk = (EditText) root.findViewById(R.id.txtloaitk);
        txtpass = (EditText) root.findViewById(R.id.txtpassword);
        profileiv = (ImageView) root.findViewById(R.id.iv_profile);
        profileiv.setOnClickListener(this);
        //
        cameraPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        return root;

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.btnsignup:
                btnDK();
                break;
            case R.id.iv_profile:
                showImagePickDialog();
                break;
        }
    }

    private void showImagePickDialog() {
        //chon chinh anh
        String[] options = {"Camera","Gallery"};
        //dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        builder.setTitle("Pick Image")
                .setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0){
                            //camera
                            if(checkCameraPermission()){
                                pickFromCamera();
                            }
                            else {
                                //not allowed, request
                                requestCameraPermission();
                            }
                        }else {
                            //gallery
                            if(checkStoragePermission()){
                                pickFromGallery();
                            }else {
                                requestStoragePermission();
                            }
                        }
                    }
                })
                .show();
    }
    private void pickFromGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_GALLERY_CODE);

    }
    private void pickFromCamera(){
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.TITLE,"Temp_Image Title");
        contentValues.put(MediaStore.Images.Media.DESCRIPTION,"Temp_Image Desctiption");
        image_uri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(intent, IMAGE_PICK_CAMERA_CODE);
    }

    private boolean checkStoragePermission()
    {
        boolean result = ContextCompat.checkSelfPermission(this.getContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result;
    }
    private void requestStoragePermission(){
        ActivityCompat.requestPermissions(this.getActivity(), storagePermissions, STORAGE_REQUEST_CODE);
    }
    private boolean checkCameraPermission()
    {
        boolean result = ContextCompat.checkSelfPermission(this.getContext(),
                Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this.getContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }
    private void requestCameraPermission(){
        ActivityCompat.requestPermissions(this.getActivity(), cameraPermissions, CAMERA_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case CAMERA_REQUEST_CODE:{
                if(grantResults.length>0){
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if(cameraAccepted && storageAccepted){
                        pickFromCamera();
                    }
                    else {
                        Toast.makeText(this.getActivity(), "Camera premissions are ...", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            case STORAGE_REQUEST_CODE:{
                if(grantResults.length>0){

                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if(storageAccepted){
                        pickFromGallery();
                    }
                    else {
                        Toast.makeText(this.getActivity(), "Storage premissions are ...", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        for (Fragment fragment: getChildFragmentManager().getFragments()){
            if(resultCode== Activity.RESULT_OK ){
                if(requestCode == IMAGE_PICK_GALLERY_CODE){
                    image_uri = data.getData();
                    //
                    profileiv.setImageURI(image_uri);
                }
                else if(requestCode == IMAGE_PICK_CAMERA_CODE){
                    //
                    profileiv.setImageURI(image_uri);
                }
            }
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void btnDK(){
        String ten = txtTen.getText().toString().trim();
        String pass = txtpass.getText().toString().trim();
        String type = loaitk.getText().toString().trim();
        String email = txtEmail.getText().toString().trim();
        String soDT = txtSoDT.getText().toString().trim();
        String diaChi = txtDiaChi.getText().toString().trim();
        String soTK = txtSoTK.getText().toString().trim();


       /* if (email.isEmpty()){
            txtEmai.setError("Chưa có email nhập!");
            txtEmai.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            txtEmai.setError("Mời nhập lại email!");
            txtEmai.requestFocus();
            return;
        }
        if (pass.isEmpty()){
            txtpass.setError("Chưa có pass!");
            txtpass.requestFocus();
            return;
        }
        if (ten.isEmpty()){
            txtTen.setError("Chưa có họ và tên!");
            txtTen.requestFocus();
            return;
        }
        if (diachi.isEmpty()){
            txtDiaChi.setError("Chưa có địa chỉ!");
            txtDiaChi.requestFocus();
            return;
        }
        if (sodt.isEmpty()){
            txtSoDT.setError("Chưa có điện thoại!");
            txtSoDT.requestFocus();
            return;
        }
        if (sotk.isEmpty()){
            txtSoTK.setError("Chưa có số tài khoản!");
            txtSoTK.requestFocus();
            return;
        }
*/
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this.getActivity(), new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Users user = new Users(email, soDT, diaChi, soTK, ten, type);
                        FirebaseDatabase.getInstance().getReference("Users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(SignupFragment.this.getActivity(), "Tài khoản dăng ký thành công!", Toast.LENGTH_LONG).show();
                                    progressBar.setVisibility(View.GONE);
                                    startActivity(new Intent(SignupFragment.this.getActivity(), HomeToRentActivity.class));
                                }else {
                                    Toast.makeText(SignupFragment.this.getActivity(), "Đăng kí thất bại! Mời nhập lại!", Toast.LENGTH_LONG).show();
                                    progressBar.setVisibility(View.GONE);
                                }
                            }
                        });
                    }else {
                        Toast.makeText(SignupFragment.this.getActivity(), "Đăng kí thất bại! Mời nhập lại!", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }
            });
    }
}
