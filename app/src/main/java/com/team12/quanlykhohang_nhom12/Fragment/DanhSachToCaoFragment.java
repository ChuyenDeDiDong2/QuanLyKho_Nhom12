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
import com.team12.quanlykhohang_nhom12.Adapter.DanhSachToCaoAdapter;
import com.team12.quanlykhohang_nhom12.Adapter.HangKhoAdapter;
import com.team12.quanlykhohang_nhom12.Library.ModelChuKho;
import com.team12.quanlykhohang_nhom12.Library.ModelToCao;
import com.team12.quanlykhohang_nhom12.R;

import java.util.ArrayList;

public class DanhSachToCaoFragment extends Fragment {
    private RecyclerView redanhsach_tocao;
    private DanhSachToCaoAdapter danhSachToCaoAdapter;
    private FirebaseAuth firebaseAuth;
    private ArrayList<ModelToCao> danhsachlist;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root =(ViewGroup) inflater.inflate(R.layout.activity_danh_sach_to_cao_fragment, container, false);
        redanhsach_tocao = root.findViewById(R.id.redanhsach_tocao);
        redanhsach_tocao.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false));
        firebaseAuth = FirebaseAuth.getInstance();
        loadchukho();
        return root;
    }

    // Thực hiện load thông tin kho:
    private void loadchukho() {
        danhsachlist = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tb_ToCao");
        reference
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        danhsachlist.clear();
                        for (DataSnapshot ds: snapshot.getChildren()){
                            ModelToCao modelToCao = ds.getValue(ModelToCao.class);
                            danhsachlist.add(modelToCao);
                        }

                        danhSachToCaoAdapter = new DanhSachToCaoAdapter(DanhSachToCaoFragment.this.getActivity(), danhsachlist);
                        redanhsach_tocao.setAdapter(danhSachToCaoAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }
}