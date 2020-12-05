package com.team12.quanlykhohang_nhom12.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.team12.quanlykhohang_nhom12.Library.MauHopDong;
import com.team12.quanlykhohang_nhom12.R;

import java.util.ArrayList;

public class DSMauHopDongAdapter extends RecyclerView.Adapter<DSMauHopDongAdapter.DSMauHopDongHoder> {

    Context context;
    ArrayList<MauHopDong> mauHD;

    public DSMauHopDongAdapter(Context c, ArrayList<MauHopDong> k){
        context = c;
        mauHD = k;
    }
    @NonNull
    @Override
    public DSMauHopDongHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DSMauHopDongAdapter.DSMauHopDongHoder(LayoutInflater.from(context).inflate(R.layout.cardviewmauhd, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DSMauHopDongHoder hoder, int position) {
        hoder.txtTenMauHD.setText(mauHD.get(position).getsTenMauHD());
        hoder.txtDonVi.setText(mauHD.get(position).getsDonVi());
        hoder.txtDonGia.setText(mauHD.get(position).getsDonGia());
    }

    @Override
    public int getItemCount() {
        return mauHD.size();
    }


    class DSMauHopDongHoder extends RecyclerView.ViewHolder
    {
        TextView txtTenMauHD, txtDonVi, txtDonGia;
        public DSMauHopDongHoder(@NonNull View itemView) {
            super(itemView);
            txtTenMauHD = itemView.findViewById(R.id.tenmhd);
            txtDonVi = itemView.findViewById(R.id.donvi);
            txtDonGia = itemView.findViewById(R.id.dongia);
        }
    }

}
