package com.team12.quanlykhohang_nhom12.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.team12.quanlykhohang_nhom12.Activity.NewProductActivity;
import com.team12.quanlykhohang_nhom12.Adapter.HangHoaAdapter;
import com.team12.quanlykhohang_nhom12.Adapter.DSHangHoaAdapter;
import com.team12.quanlykhohang_nhom12.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class StationeryFragment extends Fragment {
    FloatingActionButton btnAddStation;
    ImageView imgHH;
    RecyclerView DSHH;
    DatabaseReference reference;
    ArrayList<HangHoaAdapter> list;
    DSHangHoaAdapter DShanghoa;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.activity_stationery_fragment, container, false);

        btnAddStation = root.findViewById(R.id.btnAddStation);
        btnAddStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(root.getContext(), NewProductActivity.class));
            }
        });

        DSHH = root.findViewById(R.id.dshanghoa);
        DSHH.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        list= new ArrayList<HangHoaAdapter>();
        reference = FirebaseDatabase.getInstance().getReference().child("HH");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshotl: snapshot.getChildren())
                {
                    HangHoaAdapter Hanghoa = dataSnapshotl.getValue(HangHoaAdapter.class);
                    list.add(Hanghoa);
                }
                DShanghoa = new DSHangHoaAdapter(StationeryFragment.this.getActivity(), list);
                DSHH.setAdapter(DShanghoa);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(StationeryFragment.this.getActivity(), "Opp", Toast.LENGTH_SHORT).show();
            }
        });
        return root;
    }
}