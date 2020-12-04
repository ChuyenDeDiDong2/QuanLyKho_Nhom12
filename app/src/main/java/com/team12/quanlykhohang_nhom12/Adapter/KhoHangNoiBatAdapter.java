package com.team12.quanlykhohang_nhom12.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.team12.quanlykhohang_nhom12.Library.KhoHang;
import com.team12.quanlykhohang_nhom12.R;

import java.util.ArrayList;


public class KhoHangNoiBatAdapter extends RecyclerView.Adapter<KhoHangNoiBatAdapter.KhoHangHoder> {
    Context context;
    ArrayList<KhoHang> khoHang;

    public KhoHangNoiBatAdapter(Context c, ArrayList<KhoHang> k){
        context = c;
        khoHang = k;
    }
    @NonNull
    @Override
    public KhoHangHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new KhoHangHoder(LayoutInflater.from(context).inflate(R.layout.cardviewkhonoibat, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull KhoHangHoder holder, int position) {
        holder.txtTenKho.setText(khoHang.get(position).getsTenKho());

        Picasso.get().load(khoHang.get(position).isbHinhAnhKho()).into(holder.imgKho);
    }

    @Override
    public int getItemCount() {
        return khoHang.size();
    }

    class KhoHangHoder extends RecyclerView.ViewHolder
    {
        TextView txtTenKho, txtDiaChi, txtDienTich, txtSDT;
        ImageView imgKho;
        public KhoHangHoder(@NonNull View itemView) {
            super(itemView);
            txtTenKho = itemView.findViewById(R.id.cTenKho);

            imgKho = itemView.findViewById(R.id.imgKho);
        }
    }
}
