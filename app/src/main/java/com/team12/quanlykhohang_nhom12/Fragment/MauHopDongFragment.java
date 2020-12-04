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

import com.team12.quanlykhohang_nhom12.Activity.ThemKhoActivity;
import com.team12.quanlykhohang_nhom12.Activity.ThemMauHopDongActivity;
import com.team12.quanlykhohang_nhom12.R;

public class MauHopDongFragment extends Fragment {
    ImageButton btnMauHD;

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
        return root;
    }
}
