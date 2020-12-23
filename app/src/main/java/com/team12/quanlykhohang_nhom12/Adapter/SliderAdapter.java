package com.team12.quanlykhohang_nhom12.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.team12.quanlykhohang_nhom12.Activity.ChiTietChuKhoActivity;
import com.team12.quanlykhohang_nhom12.Library.ModelChuKho;
import com.team12.quanlykhohang_nhom12.Library.ModelSlider;
import com.team12.quanlykhohang_nhom12.R;

import java.util.ArrayList;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.HolderChuKho> {
    private Context context;
    public ArrayList<ModelSlider> sliders;

    public SliderAdapter(Context context, ArrayList<ModelSlider> sliders) {
        this.context = context;
        this.sliders = sliders;
    }

    @NonNull
    @Override
    public HolderChuKho onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //ìnfale layout row item show
        View view = LayoutInflater.from(context).inflate(R.layout.slider_show_item, parent, false);
        return new HolderChuKho(view);
    }

    @Override
    //hinhanh, link, tieude, uid
    public void onBindViewHolder(@NonNull HolderChuKho holder, int position) {
        ModelSlider modelSlider = sliders.get(position);
        String hinhanh = modelSlider.getHinhanh();
        String link = modelSlider.getLink();
        final String uid = modelSlider.getUid();
        String tieude = modelSlider.getTieude();
        //setdate

        holder.cvchukhodc.setAnimation(AnimationUtils.loadAnimation(context, R.anim.scale_list));
        holder.tieudeslider.setText(tieude);


        try {
            Picasso.get().load(hinhanh).placeholder(R.drawable.google).into(holder.imgsilier);
        }catch (Exception e)
        {
            holder.imgsilier.setImageResource(R.drawable.google);
        }
        //nhận vào để xem chi tiết kho
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(context, ChiTietChuKhoActivity.class);
//                intent.putExtra("chukhoId", uid);
//                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sliders.size();
    }

    //view holder
    class HolderChuKho extends RecyclerView.ViewHolder{
        private ImageView imgsilier;
        private TextView tieudeslider;
        private CardView cvchukhodc;

        public HolderChuKho(@NonNull View itemView) {
            super(itemView);
            cvchukhodc = itemView.findViewById(R.id.cvchukhodc);
            imgsilier = itemView.findViewById(R.id.imgsilier);
            tieudeslider = itemView.findViewById(R.id.tieudeslider);

        }
    }
}
