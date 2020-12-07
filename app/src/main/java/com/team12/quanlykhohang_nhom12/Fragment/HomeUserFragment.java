package com.team12.quanlykhohang_nhom12.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.team12.quanlykhohang_nhom12.Activity.DangNhapActivity;
import com.team12.quanlykhohang_nhom12.Activity.HomeToRentActivity;
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
    private TextView nametv;
    private ImageView btndangxuat;
    private FirebaseAuth firebaseAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.activity_home_user, container, false);

        recHomeUser1 = root.findViewById(R.id.recHomeUser);
        recHomeNoiBat1 = root.findViewById(R.id.recHomeNoiBat);
        recHomeUser1.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recHomeNoiBat1.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayoutManager.HORIZONTAL, false));

        nametv = root.findViewById(R.id.tvname);
        btndangxuat = root.findViewById(R.id.ivdangxuat);
        firebaseAuth = FirebaseAuth.getInstance();
        checkUser();
        btndangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                checkUser();
            }
        });

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
    private void checkUser() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user == null){
            startActivity(new Intent(HomeUserFragment.this.getActivity(), DangNhapActivity.class));
            getActivity().finish();
        }
        else
        {
            loadMyinfo();
        }
    }

    private void loadMyinfo() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tb_Users");
        reference.orderByChild("uid").equalTo(firebaseAuth.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds: snapshot.getChildren()){
                            String name =""+ds.child("name").getValue();
                            String accountType =""+ds.child("accountType").getValue();

                            nametv.setText(name +"("+accountType+")");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}