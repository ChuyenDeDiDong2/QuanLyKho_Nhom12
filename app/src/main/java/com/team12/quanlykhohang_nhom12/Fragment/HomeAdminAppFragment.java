package com.team12.quanlykhohang_nhom12.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.team12.quanlykhohang_nhom12.Adapter.TaiKhoanBlockAdapter;
import com.team12.quanlykhohang_nhom12.R;

public class HomeAdminAppFragment extends Fragment {

    LinearLayout btnDanhSachHangKho, btnDanhSachUser, btn_slider, btndanhsachhang_bikhoa;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.activity_home_admin_app_fragment, container, false);
        btnDanhSachHangKho = root.findViewById(R.id.btndanh_sach_hang_kho);
        btnDanhSachUser = root.findViewById(R.id.btn_danh_sach_nguoi_dung);
        btndanhsachhang_bikhoa = root.findViewById(R.id.btndanhsachhang_bikhoa);
        btn_slider = root.findViewById(R.id.btn_slider);
        btnDanhSachHangKho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_adminapp_container, new DanhSachHangKhoFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        btnDanhSachUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_adminapp_container, new DanhSachUserFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        btndanhsachhang_bikhoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_adminapp_container, new DanhSachTaiKhoanBlockFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        btn_slider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_adminapp_container, new SliderFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return root;
    }
}