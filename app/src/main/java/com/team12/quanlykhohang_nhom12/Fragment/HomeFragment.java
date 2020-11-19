package com.team12.quanlykhohang_nhom12.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.team12.quanlykhohang_nhom12.Activity.AddStaftActivity;
import com.team12.quanlykhohang_nhom12.Activity.InfoAppActivity;
import com.team12.quanlykhohang_nhom12.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    LinearLayout btnCapPhat, btnThongKe, btnNhanVien, btnPhongBan, btnVanPhongPham, btnVaiTro;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);

        //slider
        ImageSlider imageSlider = root.findViewById(R.id.slider);
        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel("https://sec-warehouse.vn/wp-content/uploads/2020/08/kho-hoa-chat-800x400.jpg",""));
        slideModels.add(new SlideModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ6Y0EVQGEcoPtGKXtXaPIU_mysnqzzT14qZg&usqp=CAU",""));
        slideModels.add(new SlideModel("https://vilas.edu.vn/wp-content/uploads/2019/05/flow.jpg",""));
        slideModels.add(new SlideModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSh-P8PfdVxPiswy3TyUqklTPnOF9QkqKEYMg&usqp=CAU",""));
        imageSlider.setImageList(slideModels, true);
        //getCantroll
        btnCapPhat = root.findViewById(R.id.btnaLlocation);
        btnThongKe = root.findViewById(R.id.btnStatistics);
        btnNhanVien = root.findViewById(R.id.btnStaff);
        btnPhongBan = root.findViewById(R.id.btnDepartment);
        btnVanPhongPham = root.findViewById(R.id.btnStationery);
        btnVaiTro = root.findViewById(R.id.btnRole);
        //su kien khi nhan vao icon tren trang chu
        //----trang cap phat
        btnCapPhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new AllocationFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        //----trang thong ke
        btnThongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new AllocationFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        //----trang nhan vien
        btnNhanVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new StaftManagerFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
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
        //----trang nhan vien
        btnVanPhongPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new StationeryFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        //----trang nhan vien
        btnVaiTro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new RoleFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return root;
    }
}
