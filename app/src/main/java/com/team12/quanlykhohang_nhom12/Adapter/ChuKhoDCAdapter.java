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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.squareup.picasso.Picasso;
import com.team12.quanlykhohang_nhom12.Activity.ChiTietChuKhoActivity;
import com.team12.quanlykhohang_nhom12.Library.ModelChuKho;
import com.team12.quanlykhohang_nhom12.R;

import java.util.ArrayList;

public class ChuKhoDCAdapter extends RecyclerView.Adapter<ChuKhoDCAdapter.HolderChuKhoDC> {
    private Context context;
    public ArrayList<ModelChuKho> chukhoList;

    public ChuKhoDCAdapter(Context context, ArrayList<ModelChuKho> chukhoList) {
        this.context = context;
        this.chukhoList = chukhoList;
    }

    @NonNull
    @Override
    public HolderChuKhoDC onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //ìnfale layout row item show
        View view = LayoutInflater.from(context).inflate(R.layout.chukho_tem_show_dc, parent, false);
        return new HolderChuKhoDC(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderChuKhoDC holder, int position) {
        ModelChuKho modelChuKho = chukhoList.get(position);
        String accountType = modelChuKho.getAccountType();
        String name = modelChuKho.getName();
        final String uid = modelChuKho.getUid();
        String phone = modelChuKho.getPhone();
        final String email = modelChuKho.getEmail();
        String sotaikhoan = modelChuKho.getSotaikhoan();
        String tentaikhoan = modelChuKho.getTentaikhoan();
        String online = modelChuKho.getOnline();
        String cuahangOpen = modelChuKho.getOpen();
        String noibat = modelChuKho.getNoibat();
        String profileImage = modelChuKho.getProfileImage();
        //setdate


        holder.cvchukhodc.setAnimation(AnimationUtils.loadAnimation(context, R.anim.scale_list));
        holder.ten_chu_kho_tv.setText("Hãng kho: "+ tentaikhoan);
        holder.phone_chu_kho_tv.setText("Liên hệ: "+ phone);
        holder.tv_email.setText("Liên hệ gmail: "+ email);
        if (online.equals("true")){
            //chu kho online
            holder.onlineiv.setVisibility(View.VISIBLE);
        }
        else {
            //chu kho offline
            holder.onlineiv.setVisibility(View.GONE);
        }

        //kiem tra chu kho con hoat dong
        if (cuahangOpen.equals("true")){
            //chu kho online
            holder.opentv.setVisibility(View.GONE);
        }
        else {
            //chu kho offline
            holder.opentv.setVisibility(View.VISIBLE);
        }
        try {
            Picasso.get().load(profileImage).placeholder(R.drawable.google).into(holder.chukhotv);
        }catch (Exception e)
        {
            holder.chukhotv.setImageResource(R.drawable.google);
        }
        //nhận vào để xem chi tiết kho
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cuahangOpen.equals("false")){
                    Toast.makeText(context, "Cửa hàng đã đóng cửa", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(context, ChiTietChuKhoActivity.class);
                    intent.putExtra("chukhoId", uid);
                    context.startActivity(intent);
                    Toast.makeText(context, "Chào mừng bạn đến với hãng kho", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return chukhoList.size();
    }

    //view holder
    class HolderChuKhoDC extends RecyclerView.ViewHolder{
        private ImageView chukhotv, onlineiv;
        private TextView ten_chu_kho_tv, phone_chu_kho_tv, tv_email, opentv;
        private RatingBar ratingbarchukho;
        private CardView cvchukhodc;

        public HolderChuKhoDC(@NonNull View itemView) {
            super(itemView);
            cvchukhodc = itemView.findViewById(R.id.cvchukhodc);
            chukhotv = itemView.findViewById(R.id.chukhotv);
            onlineiv = itemView.findViewById(R.id.onlineiv);
            ten_chu_kho_tv = itemView.findViewById(R.id.ten_chu_kho_tv);
            phone_chu_kho_tv = itemView.findViewById(R.id.phone_chu_kho_tv);
            tv_email = itemView.findViewById(R.id.tv_email);
            opentv = itemView.findViewById(R.id.opentv);
            ratingbarchukho = itemView.findViewById(R.id.ratingbarchukho);
        }
    }
}
