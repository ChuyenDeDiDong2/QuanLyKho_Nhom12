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

import com.team12.quanlykhohang_nhom12.Activity.ThemSliderActivity;
import com.team12.quanlykhohang_nhom12.R;

public class SliderFragment extends Fragment {
    ImageButton btnthemslider;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.activity_slider_fragment, container, false);
        btnthemslider = root.findViewById(R.id.btnthemslider);
        btnthemslider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ThemSliderActivity.class));

            }
        });
        return root;
    }
}