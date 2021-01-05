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
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.team12.quanlykhohang_nhom12.Adapter.TaiKhoanBlockAdapter;
import com.team12.quanlykhohang_nhom12.R;

public class HomeAdminAppFragment extends Fragment {

    LinearLayout btnDanhSachHangKho, btnDanhSachUser, btn_slider, btndanhsachhang_bikhoa,btndanhsachtocao;
    TextView tvsoluongtocaochuadoc;
    private FirebaseAuth firebaseAuth;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.activity_home_admin_app_fragment, container, false);
        btnDanhSachHangKho = root.findViewById(R.id.btndanh_sach_hang_kho);
        btnDanhSachUser = root.findViewById(R.id.btn_danh_sach_nguoi_dung);
        btndanhsachhang_bikhoa = root.findViewById(R.id.btndanhsachhang_bikhoa);
        btn_slider = root.findViewById(R.id.btn_slider);
        btndanhsachtocao = root.findViewById(R.id.btndanhsachtocao);
        tvsoluongtocaochuadoc = root.findViewById(R.id.tvsoluongtocaochuadoc);
        firebaseAuth = FirebaseAuth.getInstance();

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
        btndanhsachtocao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_adminapp_container, new DanhSachToCaoFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        DemSoThongBao();
        return root;
    }
    private int sum = 0;
    private void DemSoThongBao(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tb_ToCao");
        reference.orderByChild("tocaochuadoc").equalTo("true").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    sum = (int) snapshot.getChildrenCount();
                    tvsoluongtocaochuadoc.setText(Integer.toString(sum)+"");
                }
                else {
                    tvsoluongtocaochuadoc.setText("0");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}