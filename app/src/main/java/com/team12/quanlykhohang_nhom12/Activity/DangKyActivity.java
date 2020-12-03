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
import com.google.firebase.database.FirebaseDatabase;
import com.team12.quanlykhohang_nhom12.Adapter.UserAdapter;
import com.team12.quanlykhohang_nhom12.MainActivity;
import com.team12.quanlykhohang_nhom12.R;

public class DangKyActivity extends AppCompatActivity implements View.OnClickListener  {
    Button btnDK;
    EditText edtNTenDN, edtNEmail, edtNPass, edtNTen, edtNDiachi, edtNSoTK;
    TextView txtDK, banner;

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        mAuth = FirebaseAuth.getInstance();
        progressBar =(ProgressBar) findViewById(R.id.progressBar1);
        banner = (TextView) findViewById(R.id.banner);
        banner.setOnClickListener(this);
        btnDK = (Button) findViewById(R.id.btnDK);
        btnDK.setOnClickListener(this);

        edtNTenDN = (EditText) findViewById(R.id.edtNTenDN);
        edtNEmail = (EditText) findViewById(R.id.edtNEmail);
        edtNPass = (EditText) findViewById(R.id.edtNPass);
        edtNTen = (EditText) findViewById(R.id.edtNTen);
        edtNDiachi = (EditText) findViewById(R.id.edtNDiachi);
        edtNSoTK = (EditText) findViewById(R.id.edtNSoTK);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.banner:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.btnDK:
                btnDK();
                break;
        }
    }

    private void btnDK(){
        String tenDN = edtNTenDN.getText().toString().trim();
        String email = edtNEmail.getText().toString().trim();
        String pass = edtNPass.getText().toString().trim();
        String ten = edtNTen.getText().toString().trim();
        String diachi = edtNDiachi.getText().toString().trim();
        String sotk = edtNSoTK.getText().toString().trim();

        if (tenDN.isEmpty()){
            edtNTenDN.setError("Chưa có tên đăng nhập!");
            edtNTenDN.requestFocus();
            return;
        }
        if (email.isEmpty()){
            edtNEmail.setError("Chưa có email nhập!");
            edtNEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edtNEmail.setError("Mời nhập lại email!");
            edtNEmail.requestFocus();
            return;
        }
        if (pass.isEmpty()){
            edtNPass.setError("Chưa có pass!");
            edtNPass.requestFocus();
            return;
        }
        if (ten.isEmpty()){
            edtNTenDN.setError("Chưa có họ và tên!");
            edtNTenDN.requestFocus();
            return;
        }
        if (diachi.isEmpty()){
            edtNDiachi.setError("Chưa có địa chỉ!");
            edtNDiachi.requestFocus();
            return;
        }
        if (sotk.isEmpty()){
            edtNSoTK.setError("Chưa có số tài khoản!");
            edtNSoTK.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            UserAdapter user = new UserAdapter(tenDN, email, ten, diachi, sotk);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(DangKyActivity.this, "Tài khoản dăng ký thành công!", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                        startActivity(new Intent(DangKyActivity.this, DangnhapActivity.class));
                                    }else {
                                        Toast.makeText(DangKyActivity.this, "Đăng kí thất bại! Mời nhập lại!", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }else {
                            Toast.makeText(DangKyActivity.this, "Đăng kí thất bại! Mời nhập lại!", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }
}