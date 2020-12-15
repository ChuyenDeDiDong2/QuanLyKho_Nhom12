package com.team12.quanlykhohang_nhom12.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.team12.quanlykhohang_nhom12.Activity.ThemHangHoaActivity;
import com.team12.quanlykhohang_nhom12.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HangHoaActivity extends Fragment {
    FloatingActionButton btnAddStation;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.activity_hanghoa_fragment, container, false);

        btnAddStation = root.findViewById(R.id.btnAddStation);
        btnAddStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(root.getContext(), ThemHangHoaActivity.class));
            }
        });
        return root;
    }

}