package com.team12.quanlykhohang_nhom12.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.team12.quanlykhohang_nhom12.Activity.ThongKeDoanhThuThueKhoActivity;
import com.team12.quanlykhohang_nhom12.Adapter.ThongKeHopDongAdapter;
import com.team12.quanlykhohang_nhom12.Adapter.ThongKeTungKhoAdapter;
import com.team12.quanlykhohang_nhom12.Library.ModelHopDong;
import com.team12.quanlykhohang_nhom12.Library.ModelKhoHang;
import com.team12.quanlykhohang_nhom12.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

    public class ThongKeTungKhoFragment extends Fragment {
        private RecyclerView rcdanhsachdanghopdong;
        private ThongKeTungKhoAdapter thongKeTungKhoAdapter;
        private TextView tv_total_price, tv_dientichdathue;
        private FirebaseAuth firebaseAuth;
        private String khohangId;
        private ArrayList<ModelKhoHang> danhsachlist;
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            ViewGroup root = (ViewGroup) inflater.inflate(R.layout.activity_thong_ke_tung_kho_fragment, container, false);
            rcdanhsachdanghopdong = root.findViewById(R.id.rcdanhsachdanghopdong);
            rcdanhsachdanghopdong.setLayoutManager(new LinearLayoutManager(ThongKeTungKhoFragment.this.getActivity(), LinearLayoutManager.VERTICAL, false));
            firebaseAuth = FirebaseAuth.getInstance();
            tv_total_price = root.findViewById(R.id.tv_total_price);
            tv_dientichdathue = root.findViewById(R.id.tv_dientichdathue);
            loadinput();
            totaldientichdathue();
            return root;
        }
        private void loadinput() {
            danhsachlist = new ArrayList<>();
            //Hiển thị slider
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tb_Users");
            reference.child(firebaseAuth.getUid()).child("KhoHang")
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //before getting reset list
                            danhsachlist.clear();
                            for (DataSnapshot ds: snapshot.getChildren()){
                                ModelKhoHang modelKhoHang = ds.getValue(ModelKhoHang.class);
                                danhsachlist.add(modelKhoHang);
                            }
                            thongKeTungKhoAdapter = new ThongKeTungKhoAdapter(ThongKeTungKhoFragment.this.getActivity(), danhsachlist);
                            rcdanhsachdanghopdong.setAdapter(thongKeTungKhoAdapter);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
        }

        private void totaldientichdathue() {
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tb_Users");
            reference.child(firebaseAuth.getUid()).child("KhoHang")
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Locale localeVN = new Locale("vi", "VN");
                            NumberFormat vn = NumberFormat.getInstance(localeVN);
                            int sum=0;
                            int sum1=0;
                            for (DataSnapshot ds: snapshot.getChildren()){
                                Map<String, Object> map =  (Map<String, Object>)ds.getValue();
                                Object tongthunhap = map.get("tongthunhap");
                                Object tongdientichchothue = map.get("dientichdathue");
                                int dgValue  = Integer.parseInt(String.valueOf(tongthunhap));
                                int dtValue  = Integer.parseInt(String.valueOf(tongdientichchothue));
                                sum += dgValue;
                                sum1 += dtValue;
                                tv_total_price.setText(vn.format(sum)+" Vnd");
                                tv_dientichdathue.setText(sum1 +" m2");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

        }

    }