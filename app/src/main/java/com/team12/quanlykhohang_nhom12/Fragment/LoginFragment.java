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
import com.team12.quanlykhohang_nhom12.Activity.HomeToRentActivity;
import com.team12.quanlykhohang_nhom12.MainActivity;
import com.team12.quanlykhohang_nhom12.R;

public class LoginFragment extends Fragment implements View.OnClickListener {
    EditText txtemail, txtpass;
    TextView forgetPass;
    Button btnLogin;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    float v=0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_fragment, container, false);
        txtemail = root.findViewById(R.id.emailsignin);
        txtpass = root.findViewById(R.id.passwordsignin);
        forgetPass = root.findViewById(R.id.forgerpass);
        btnLogin = root.findViewById(R.id.btnlogin);

        txtemail.setTranslationY(800);
        txtpass.setTranslationY(800);
        forgetPass.setTranslationY(800);
        btnLogin.setTranslationY(800);

        txtemail.setAlpha(v);
        txtpass.setAlpha(v);
        forgetPass.setAlpha(v);
        btnLogin.setAlpha(v);

        txtemail.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(300).start();
        txtpass.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(500).start();
        forgetPass.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(500).start();
        btnLogin.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(700).start();
        mAuth = FirebaseAuth.getInstance();
        progressBar = (ProgressBar) root.findViewById(R.id.progressBar2);
        btnLogin = (Button) root.findViewById(R.id.btnlogin);
        btnLogin.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btnlogin:
                Login();
                break;
        }
    }

    private void Login() {
        String email = txtemail.getText().toString().trim();
        String pass = txtpass.getText().toString().trim();


        if (email.isEmpty()){
            txtemail.setError("Chưa nhập email!");
            txtemail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            txtemail.setError("Mời nhập lại email!");
            txtemail.requestFocus();
            return;
        }
        if (pass.isEmpty()){
            txtpass.setError("Chưa nhập mật khẩu!");
            txtpass.requestFocus();
            return;
        }
        if (pass.length() < 6){
            txtpass.setError("Mời nhập đủ 6 kí tự!");
            txtpass.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    startActivity(new Intent(LoginFragment.this.getActivity(), HomeToRentActivity.class));
                            /*FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            if (user.isEmailVerified()){
                                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                            }else {
                                user.sendEmailVerification();
                                Toast.makeText(MainActivity.this, "Kiểm tra email để xác nhận gtaif khoản của bạn!", Toast.LENGTH_LONG).show();
                            }*/

                }else {
                    Toast.makeText(LoginFragment.this.getActivity(), "Đăng nhập thất bại! Mời nhập lại!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
