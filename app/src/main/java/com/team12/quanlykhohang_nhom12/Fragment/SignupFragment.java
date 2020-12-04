package com.team12.quanlykhohang_nhom12.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.team12.quanlykhohang_nhom12.Activity.HomeActivity;
import com.team12.quanlykhohang_nhom12.Activity.HomeToRentActivity;
import com.team12.quanlykhohang_nhom12.Library.Users;
import com.team12.quanlykhohang_nhom12.MainActivity;
import com.team12.quanlykhohang_nhom12.R;

public class SignupFragment extends Fragment implements View.OnClickListener {
    Button btnDangKy;
    EditText txtEmail, txtSoDT, txtDiaChi, txtTen, txtpass, txtSoTK, loaitk;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

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
        return root;

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.btnsignup:
                btnDK();
                break;
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
