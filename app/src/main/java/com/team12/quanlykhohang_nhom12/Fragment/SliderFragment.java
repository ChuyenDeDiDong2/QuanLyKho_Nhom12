package com.team12.quanlykhohang_nhom12.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.team12.quanlykhohang_nhom12.Activity.ThemSliderActivity;
import com.team12.quanlykhohang_nhom12.Adapter.SliderAdapter;
import com.team12.quanlykhohang_nhom12.Library.ModelSlider;
import com.team12.quanlykhohang_nhom12.R;

import java.util.ArrayList;

public class SliderFragment extends Fragment {
    ImageButton btnthemslider;
    private FirebaseAuth firebaseAuth;
    private RecyclerView rv_fragment_silder;
    private ProgressDialog progressDialog;
    private ArrayList<ModelSlider> sliders;
    private SliderAdapter sliderAdapter;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.activity_slider_fragment, container, false);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this.getActivity());
        progressDialog.setTitle("Please wait");
        progressDialog.setCanceledOnTouchOutside(false);
        btnthemslider = root.findViewById(R.id.btnthemslider);
        rv_fragment_silder = root.findViewById(R.id.rv_fragment_silder);
        rv_fragment_silder.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false));
        btnthemslider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ThemSliderActivity.class));
            }
        });
        loadslider();
        return root;
    }

    private void loadslider() {
        sliders = new ArrayList<>();
        //get all product
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tb_Slider");
        reference
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //before getting reset list
                        sliders.clear();
                        for (DataSnapshot ds: snapshot.getChildren()){
                            ModelSlider modelSlider = ds.getValue(ModelSlider.class);
                            sliders.add(modelSlider);
                        }
                        sliderAdapter = new SliderAdapter(SliderFragment.this.getActivity(), sliders);
                        rv_fragment_silder.setAdapter(sliderAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}