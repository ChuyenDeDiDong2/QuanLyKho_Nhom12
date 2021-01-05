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
import com.team12.quanlykhohang_nhom12.R;

import java.util.ArrayList;

public class ThongKeTungKhoFragment extends Fragment {
    private RecyclerView rcdanhsachdanghopdong;
    private ThongKeTungKhoAdapter thongKeTungKhoAdapter;
    private FirebaseAuth firebaseAuth;
    private String khohangId;
    private ArrayList<ModelHopDong> danhsachlist;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.activity_thong_ke_tung_kho_fragment, container, false);
        rcdanhsachdanghopdong = root.findViewById(R.id.rcdanhsachdanghopdong);
        rcdanhsachdanghopdong.setLayoutManager(new LinearLayoutManager(ThongKeTungKhoFragment.this.getActivity(), LinearLayoutManager.VERTICAL, false));
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
                        thongKeTungKhoAdapter = new ThongKeTungKhoAdapter(ThongKeTungKhoFragment.this.getActivity(), danhsachlist);
                        rcdanhsachdanghopdong.setAdapter(thongKeTungKhoAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

}