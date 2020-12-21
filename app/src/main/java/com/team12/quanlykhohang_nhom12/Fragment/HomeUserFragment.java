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

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.team12.quanlykhohang_nhom12.Activity.DangNhapActivity;
import com.team12.quanlykhohang_nhom12.Adapter.ChuKhoAdapter;
import com.team12.quanlykhohang_nhom12.Adapter.ChuKhoDCAdapter;
import com.team12.quanlykhohang_nhom12.Library.ModelChuKho;
import com.team12.quanlykhohang_nhom12.R;

import java.util.ArrayList;
import java.util.List;

public class HomeUserFragment extends Fragment {
    RecyclerView recHomeUser, recHomeNoiBat1;
    private FirebaseAuth firebaseAuth;

    private ArrayList<ModelChuKho> chukhoList, chukholistdc;
    private ChuKhoAdapter chuKhoAdapter;
    private ChuKhoDCAdapter chukhoDCAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.activity_home_user, container, false);


        recHomeNoiBat1 = root.findViewById(R.id.recHomeNoiBat);
        recHomeNoiBat1.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recHomeUser = root.findViewById(R.id.recHomeUser);
        recHomeUser.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false));
        firebaseAuth = FirebaseAuth.getInstance();

        ImageSlider imageSlider = root.findViewById(R.id.slider);
        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel("https://sec-warehouse.vn/wp-content/uploads/2020/08/kho-hoa-chat-800x400.jpg", ""));
        slideModels.add(new SlideModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ6Y0EVQGEcoPtGKXtXaPIU_mysnqzzT14qZg&usqp=CAU", ""));
        slideModels.add(new SlideModel("https://vilas.edu.vn/wp-content/uploads/2019/05/flow.jpg", ""));
        slideModels.add(new SlideModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSh-P8PfdVxPiswy3TyUqklTPnOF9QkqKEYMg&usqp=CAU", ""));
        imageSlider.setImageList(slideModels, true);

        checkUser();//Nút quay lại;

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
                            String diachi =""+ds.child("diachi").getValue();
                            //String profileImage =""+ds.child("profileImage").getValue();
                            String accountType =""+ds.child("accountType").getValue();
                            loadchukho(noibat, diachi);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
    // Thực hiện load thông tin kho nổi bật:
    private void loadchukho(final String noibat, final String diachi) {
        chukhoList = new ArrayList<>();
        chukholistdc = new ArrayList<>();
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
                        chukholistdc.clear();
                        for (DataSnapshot ds: snapshot.getChildren()){
                            ModelChuKho modelChuKho = ds.getValue(ModelChuKho.class);
                            String chukhodiachi =""+ds.child("diachi").getValue();
                            //chỉ hiển thị hãng nổi bật cho người dùng nhìn thấy:
                            if (chukhodiachi.equals(diachi)){
                                chukholistdc.add(modelChuKho);
                            }
                        }

                        chuKhoAdapter = new ChuKhoAdapter(HomeUserFragment.this.getActivity(), chukhoList);
                        recHomeNoiBat1.setAdapter(chuKhoAdapter);
                        chukhoDCAdapter = new ChuKhoDCAdapter(HomeUserFragment.this.getActivity(), chukholistdc);
                        recHomeUser.setAdapter(chukhoDCAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }
}