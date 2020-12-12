package com.team12.quanlykhohang_nhom12.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.team12.quanlykhohang_nhom12.Activity.DangNhapActivity;
import com.team12.quanlykhohang_nhom12.Adapter.ChuKhoAdapter;
import com.team12.quanlykhohang_nhom12.Library.ModelChuKho;
import com.team12.quanlykhohang_nhom12.R;

import java.util.ArrayList;

public class HomeUserFragment extends Fragment {
    RecyclerView recHomeUser1, recHomeNoiBat1;
    private TextView nametv;
    private ImageView btndangxuat;
    private FirebaseAuth firebaseAuth;

    private ArrayList<ModelChuKho> chukhoList;
    private ChuKhoAdapter chuKhoAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.activity_home_user, container, false);


        nametv = root.findViewById(R.id.tvname);
        btndangxuat = root.findViewById(R.id.ivdangxuat);
        recHomeNoiBat1 = root.findViewById(R.id.recHomeNoiBat);
        recHomeNoiBat1.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayoutManager.HORIZONTAL, false));
        firebaseAuth = FirebaseAuth.getInstance();

        checkUser();//Nút quay lại;
        btndangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                checkUser();
            }
        });

        return root;

    }



    // Kiểm tra tài khoản đã tồn tại hay chưa?
    private void checkUser() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user == null){
            startActivity(new Intent(HomeUserFragment.this.getActivity(), DangNhapActivity.class));
            getActivity().finish();
        }
        else
        {
            loadMyinfo();
        }
    }
    // Thực hiện load thông tin của người dùng:
    private void loadMyinfo() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tb_Users");
        reference.orderByChild("uid").equalTo(firebaseAuth.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds: snapshot.getChildren()){
                            String name =""+ds.child("name").getValue();
                            String email =""+ds.child("email").getValue();
                            String noibat =""+ds.child("noibat").getValue();
                            //String phone =""+ds.child("phone").getValue();
                            //String profileImage =""+ds.child("profileImage").getValue();
                            String accountType =""+ds.child("accountType").getValue();

                            nametv.setText(name);
                            loadchukho(noibat);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
    // Thực hiện load thông tin kho nổi bật:
    private void loadchukho(final String noibat) {
        chukhoList = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tb_Users");
        reference.orderByChild("accountType").equalTo("admin")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        chukhoList.clear();
                        for (DataSnapshot ds: snapshot.getChildren()){
                            ModelChuKho modelChuKho = ds.getValue(ModelChuKho.class);
                            String chukhoemail =""+ds.child("noibat").getValue();
                            //chỉ hiển thị hãng nổi bật cho người dùng nhìn thấy:
                            if (chukhoemail.equals(noibat)){
                                chukhoList.add(modelChuKho);
                            }
                        }

                        chuKhoAdapter = new ChuKhoAdapter(HomeUserFragment.this.getActivity(), chukhoList);
                        recHomeNoiBat1.setAdapter(chuKhoAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }
}