package com.team12.quanlykhohang_nhom12.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.team12.quanlykhohang_nhom12.Adapter.DanhSachHopDongAdapter;
import com.team12.quanlykhohang_nhom12.Library.ModelHopDong;
import com.team12.quanlykhohang_nhom12.R;

import java.util.ArrayList;

public class DanhSachHopDongFragment extends Fragment {
    private RecyclerView rcdanhsachdanghopdong;
    private DanhSachHopDongAdapter danhSachHopDongAdapter;
    private FirebaseAuth firebaseAuth;
    private ArrayList<ModelHopDong> danhsachlist;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.activity_danh_sach_hop_dong, container, false);
        rcdanhsachdanghopdong = root.findViewById(R.id.rcdanhsachdanghopdong);
        rcdanhsachdanghopdong.setLayoutManager(new LinearLayoutManager(DanhSachHopDongFragment.this.getActivity(), LinearLayoutManager.VERTICAL, false));
        firebaseAuth = FirebaseAuth.getInstance();
        loadinput();
        return root;
    }
    private void loadinput() {
        danhsachlist = new ArrayList<>();
        //Hiển thị slider
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tb_HopDongChinhThuc");
        reference.orderByChild("hisUid").equalTo(firebaseAuth.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //before getting reset list
                        danhsachlist.clear();
                        for (DataSnapshot ds: snapshot.getChildren()){
                            ModelHopDong modelHopDong = ds.getValue(ModelHopDong.class);
                            danhsachlist.add(modelHopDong);
                        }
                        danhSachHopDongAdapter = new DanhSachHopDongAdapter(DanhSachHopDongFragment.this.getActivity(), danhsachlist);
                        rcdanhsachdanghopdong.setAdapter(danhSachHopDongAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

}