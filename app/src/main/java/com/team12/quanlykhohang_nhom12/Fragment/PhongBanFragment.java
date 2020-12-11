package com.team12.quanlykhohang_nhom12.Fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.gms.common.internal.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.team12.quanlykhohang_nhom12.Activity.ThemKhoActivity;
import com.team12.quanlykhohang_nhom12.Adapter.KhoHangIDAdapter;
import com.team12.quanlykhohang_nhom12.Library.Constants_kho;
import com.team12.quanlykhohang_nhom12.Library.ModelKhoHang;
import com.team12.quanlykhohang_nhom12.R;

import java.util.ArrayList;

public class PhongBanFragment extends Fragment {
    ImageButton btnThemKho;
    RecyclerView danhsachkhohangrc;
    private TextView tvTiemKiem, tvkhohangfilter;
    private ArrayList<ModelKhoHang> khokhanglist;
    private KhoHangIDAdapter khoHangIDAdapter;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private ImageButton btnFilter_KhoHang;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_phongban, container, false);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this.getActivity());
        progressDialog.setTitle("Please wait");
        progressDialog.setCanceledOnTouchOutside(false);

        btnThemKho = root.findViewById(R.id.btnthemkho);
        danhsachkhohangrc = root.findViewById(R.id.redanhsach_khohang);
        btnFilter_KhoHang = root.findViewById(R.id.btnFilterKhoHang);
        tvTiemKiem = root.findViewById(R.id.txttim_kiem);
        tvkhohangfilter = root.findViewById(R.id.tvkhohang_filter);

        btnThemKho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(root.getContext(), ThemKhoActivity.class));
            }
        });
        tvTiemKiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                try {

                    khoHangIDAdapter.getFilter().filter(s);
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btnFilter_KhoHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PhongBanFragment.this.getActivity());
                builder.setTitle("Chọn danh mục kho")
                        .setItems(Constants_kho.options2, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String selected = Constants_kho.options2[i];
                                tvkhohangfilter.setText(selected);
                                if(selected.equals("Tất cả kho")){
                                    loadKhoHang();
                                }
                                else {
                                    loadFilterKhoHang(selected);
                                }
                            }
                        })
                        .show();
            }
        });

        loadKhoHang();

        return root;
    }

    private void loadFilterKhoHang(String selected) {
        khokhanglist = new ArrayList<>();
        //get all product
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tb_Users");
        reference.child(firebaseAuth.getUid()).child("KhoHang")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //before getting reset list
                        khokhanglist.clear();
                        for (DataSnapshot ds: snapshot.getChildren()){
                            String khohangCatelogy = ""+ds.child("tinhtrangkho").getValue();
                            if (selected.equals(khohangCatelogy)){
                                ModelKhoHang modelKhoHang = ds.getValue(ModelKhoHang.class);
                                khokhanglist.add(modelKhoHang);
                            }

                        }
                        khoHangIDAdapter = new KhoHangIDAdapter(PhongBanFragment.this.getActivity(), khokhanglist);
                        danhsachkhohangrc.setAdapter(khoHangIDAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void loadKhoHang() {
        khokhanglist = new ArrayList<>();
        //get all product
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tb_Users");
        reference.child(firebaseAuth.getUid()).child("KhoHang")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //before getting reset list
                        khokhanglist.clear();
                        for (DataSnapshot ds: snapshot.getChildren()){
                            ModelKhoHang modelKhoHang = ds.getValue(ModelKhoHang.class);
                            khokhanglist.add(modelKhoHang);
                        }
                        khoHangIDAdapter = new KhoHangIDAdapter(PhongBanFragment.this.getActivity(), khokhanglist);
                        danhsachkhohangrc.setAdapter(khoHangIDAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}
