package com.team12.quanlykhohang_nhom12.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.team12.quanlykhohang_nhom12.Activity.AddStaftActivity;
import com.team12.quanlykhohang_nhom12.Activity.HomeActivity;
import com.team12.quanlykhohang_nhom12.R;
public class StaftManagerFragment extends Fragment {
    ImageButton btnAddStaft;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.activity_staft_manager_fragment, container, false);

        btnAddStaft = root.findViewById(R.id.btnAddStaft);

        btnAddStaft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(root.getContext(), AddStaftActivity.class));
            }
        });

        return root;


    }
}