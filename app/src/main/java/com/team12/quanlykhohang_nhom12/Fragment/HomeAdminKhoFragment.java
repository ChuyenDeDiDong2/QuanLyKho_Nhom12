package com.team12.quanlykhohang_nhom12.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.team12.quanlykhohang_nhom12.Activity.DangNhapActivity;
import com.team12.quanlykhohang_nhom12.Activity.MauDieuKhoanActivity;
import com.team12.quanlykhohang_nhom12.Activity.ThemKhoActivity;
import com.team12.quanlykhohang_nhom12.R;

public class HomeAdminKhoFragment extends Fragment {
    LinearLayout btnthongke, btnPhongBan, btnMauDK;
    private TextView nametv, tvtenhang;
    private ImageView btndangxuat, iv_hang_icon;
    private FirebaseAuth firebaseAuth;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.activity_home_admin_kho_fragment, container, false);
        //getCantroll
        btnMauDK = root.findViewById(R.id.btnHopDong);
        btnthongke = root.findViewById(R.id.btnthongke);
        btnPhongBan = root.findViewById(R.id.btnDepartment);
        //
        nametv = root.findViewById(R.id.tvname);
        tvtenhang = root.findViewById(R.id.tvtenhang);
        iv_hang_icon = root.findViewById(R.id.iv_hang_icon);
        btndangxuat = root.findViewById(R.id.ivdangxuat);
        firebaseAuth = FirebaseAuth.getInstance();
        checkUser();

        //su kien khi nhan vao icon tren trang chu

        //----trang nhan vien
        btnthongke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.fragment_container, new TaiKhoanFragment());
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
            }
        });
        btndangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                checkUser();
            }
        });
        //----trang nhan vien
        btnPhongBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new PhongBanFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        btnMauDK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(root.getContext(), MauDieuKhoanActivity.class));
            }
        });

        return root;
    }

    private void checkUser() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user == null){
            startActivity(new Intent(HomeAdminKhoFragment.this.getActivity(), DangNhapActivity.class));
            getActivity().finish();
        }
        else
        {
            loadMyinfo();
        }
    }

    private void loadMyinfo() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tb_Users");
        reference.orderByChild("uid").equalTo(firebaseAuth.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds: snapshot.getChildren()){
                            String name =""+ds.child("name").getValue();
                            String tentaikhoan =""+ds.child("tentaikhoan").getValue();
                            String accountType =""+ds.child("accountType").getValue();
                            String profileImage = ""+ds.child("profileImage").getValue();

                            nametv.setText(name +"("+accountType+")");
                            tvtenhang.setText(tentaikhoan);
                            try {
                                Picasso.get().load(profileImage).placeholder(R.drawable.google).into(iv_hang_icon);
                            }catch (Exception e){
                                iv_hang_icon.setImageResource(R.drawable.google);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}