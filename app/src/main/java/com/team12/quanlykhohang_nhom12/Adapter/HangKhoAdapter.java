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
import com.team12.quanlykhohang_nhom12.R;

import java.util.ArrayList;

public class HangKhoAdapter extends RecyclerView.Adapter<HangKhoAdapter.HolderChuKho> {
    private Context context;
    public ArrayList<ModelChuKho> chukhoList;

    public HangKhoAdapter(Context context, ArrayList<ModelChuKho> chukhoList) {
        this.context = context;
        this.chukhoList = chukhoList;
    }

    @NonNull
    @Override
    public HolderChuKho onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //ìnfale layout row item show
        View view = LayoutInflater.from(context).inflate(R.layout.row_item_admin, parent, false);
        return new HolderChuKho(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderChuKho holder, int position) {
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
        holder.tvten_hk.setText(tentaikhoan);
        holder.tvsodienthoai_hk.setText(phone);

        if (online.equals("true")){
            //chu kho online
            holder.ivonline_status.setVisibility(View.VISIBLE);
        }
        else {
            //chu kho offline
            holder.ivonline_status.setVisibility(View.GONE);
        }

        try {
            Picasso.get().load(profileImage).placeholder(R.drawable.google).into(holder.ivpeople_hk);
        }catch (Exception e)
        {
            holder.ivpeople_hk.setImageResource(R.drawable.google);
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
        return chukhoList.size();
    }

    //view holder
    class HolderChuKho extends RecyclerView.ViewHolder{
        private ImageView ivpeople_hk, ivonline_status;
        private TextView tvten_hk, tvsodienthoai_hk;
        private RatingBar ratingbarchukho;
        private CardView cvchukhodc;

        public HolderChuKho(@NonNull View itemView) {
            super(itemView);
            cvchukhodc = itemView.findViewById(R.id.cvchukhodc);
            ivpeople_hk = itemView.findViewById(R.id.ivpeople_hk);
            ivonline_status = itemView.findViewById(R.id.ivonline_status);
            tvten_hk = itemView.findViewById(R.id.tvten_hk);
            tvsodienthoai_hk = itemView.findViewById(R.id.tvsodienthoai_hk);
        }
    }
}
