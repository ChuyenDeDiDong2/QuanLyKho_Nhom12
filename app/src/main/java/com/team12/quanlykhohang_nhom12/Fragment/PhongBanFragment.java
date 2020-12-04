package com.team12.quanlykhohang_nhom12.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.team12.quanlykhohang_nhom12.Activity.AddStaftActivity;
import com.team12.quanlykhohang_nhom12.Activity.ThemKhoActivity;
import com.team12.quanlykhohang_nhom12.R;

public class PhongBanFragment extends Fragment {
    ImageButton btnThemKho;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_phongban, container, false);

        btnThemKho = root.findViewById(R.id.btnthemkho);

        btnThemKho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(root.getContext(), ThemKhoActivity.class));
            }
        });

        return root;
    }
}
