package com.team12.quanlykhohang_nhom12.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.team12.quanlykhohang_nhom12.MainActivity;
import com.team12.quanlykhohang_nhom12.R;

    public class DangnhapActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txtDK;
    private EditText edtEmail, edtPass;
    private Button btnDN;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangnhap2);

        mAuth = FirebaseAuth.getInstance();
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        txtDK = (TextView) findViewById(R.id.txtDK);
        txtDK.setOnClickListener(this);

        btnDN = (Button) findViewById(R.id.btnDN);
        btnDN.setOnClickListener(this);

        //edtTenDN = (EditText) findViewById(R.id.edtTenDN);
        edtPass = (EditText) findViewById(R.id.edtNPass);
        edtEmail = (EditText) findViewById(R.id.edtNEmail);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txtDK:
                startActivity(new Intent(DangnhapActivity.this, DangkyActivity.class));
                break;
            case R.id.btnDN:
                userLogin();
                break;
        }
    }
        private void userLogin() {
            String email = edtEmail.getText().toString().trim();
            //String tenDN = edtTenDN.getText().toString().trim();
            String pass = edtPass.getText().toString().trim();


            if (email.isEmpty()){
                edtEmail.setError("Chưa nhập email!");
                edtEmail.requestFocus();
                return;
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                edtEmail.setError("Mời nhập lại email!");
                edtEmail.requestFocus();
                return;
            }
            if (pass.isEmpty()){
                edtPass.setError("Chưa nhập mật khẩu!");
                edtPass.requestFocus();
                return;
            }
            if (pass.length() < 6){
                edtPass.setError("Mời nhập đủ 6 kí tự!");
                edtPass.requestFocus();
                return;
            }
            progressBar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        startActivity(new Intent(DangnhapActivity.this, MainActivity.class));
                            /*FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            if (user.isEmailVerified()){
                                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                            }else {
                                user.sendEmailVerification();
                                Toast.makeText(MainActivity.this, "Kiểm tra email để xác nhận gtaif khoản của bạn!", Toast.LENGTH_LONG).show();
                            }*/

                    }else {
                        Toast.makeText(DangnhapActivity.this, "Đăng nhập thất bại! Mời nhập lại!", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
}