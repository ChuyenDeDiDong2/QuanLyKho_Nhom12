package com.team12.quanlykhohang_nhom12.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.team12.quanlykhohang_nhom12.Adapter.KhoHangAdapter;
import com.team12.quanlykhohang_nhom12.Adapter.KhoHangNoiBatAdapter;
import com.team12.quanlykhohang_nhom12.Library.KhoHang;
import com.team12.quanlykhohang_nhom12.R;

import java.util.ArrayList;

public class HomeUserFragment extends Fragment {
    RecyclerView recHomeUser1, recHomeNoiBat1;
    DatabaseReference reference;
    ArrayList<KhoHang> list;
    KhoHangAdapter adapter;
    KhoHangNoiBatAdapter adapterNB;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.activity_home_user, container, false);

        recHomeUser1 = root.findViewById(R.id.recHomeUser);
        recHomeNoiBat1 = root.findViewById(R.id.recHomeNoiBat);
        recHomeUser1.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recHomeNoiBat1.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayoutManager.HORIZONTAL, false));

        list= new ArrayList<KhoHang>();
        reference = FirebaseDatabase.getInstance().getReference().child("KhoHang");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshotl: snapshot.getChildren())
                {
                    KhoHang khoHang = dataSnapshotl.getValue(KhoHang.class);
                    list.add(khoHang);
                }
                adapter = new KhoHangAdapter(HomeUserFragment.this.getActivity(), list);
                recHomeUser1.setAdapter(adapter);
                adapterNB = new KhoHangNoiBatAdapter(HomeUserFragment.this.getActivity(), list);
                recHomeNoiBat1.setAdapter(adapterNB);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomeUserFragment.this.getActivity(), "Opp", Toast.LENGTH_SHORT).show();
            }
        });
        return root;

    }
}