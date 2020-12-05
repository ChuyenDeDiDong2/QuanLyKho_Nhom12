package com.team12.quanlykhohang_nhom12.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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
import com.team12.quanlykhohang_nhom12.Activity.ThemKhoActivity;
import com.team12.quanlykhohang_nhom12.Activity.ThemMauHopDongActivity;
import com.team12.quanlykhohang_nhom12.Adapter.DSMauHopDongAdapter;
import com.team12.quanlykhohang_nhom12.Library.MauHopDong;
import com.team12.quanlykhohang_nhom12.R;

import java.util.ArrayList;

public class MauHopDongFragment extends Fragment {
    ImageButton btnMauHD;
    RecyclerView dsMauHD;
    DatabaseReference reference;
    ArrayList<MauHopDong> list;
    DSMauHopDongAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_mauhopdong, container, false);

        btnMauHD = root.findViewById(R.id.btnMauHopDong);

        btnMauHD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(root.getContext(), ThemMauHopDongActivity.class));
            }
        });

        dsMauHD = root.findViewById(R.id.dsmauhopdong);
        dsMauHD.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        list= new ArrayList<MauHopDong>();
        reference = FirebaseDatabase.getInstance().getReference().child("MauHD");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshotl: snapshot.getChildren())
                {
                    MauHopDong mauHopDong = dataSnapshotl.getValue(MauHopDong.class);
                    list.add(mauHopDong);
                }
                adapter = new DSMauHopDongAdapter(MauHopDongFragment.this.getActivity(), list);
                dsMauHD.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MauHopDongFragment.this.getActivity(), "Opp", Toast.LENGTH_SHORT).show();
            }
        });
        return root;
    }
}
