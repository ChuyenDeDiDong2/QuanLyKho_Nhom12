package com.team12.quanlykhohang_nhom12.Adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.team12.quanlykhohang_nhom12.Adapter.HangHoaAdapter;
import com.team12.quanlykhohang_nhom12.R;

import java.util.ArrayList;

public class DSHangHoaAdapter extends RecyclerView.Adapter<DSHangHoaAdapter.DSHangHoaHoder> {

    Context context;
    ArrayList<HangHoaAdapter> HH;

    public DSHangHoaAdapter(Context c, ArrayList<HangHoaAdapter> k){
        context = c;
        HH = k;
    }
    @NonNull
    @Override
    public DSHangHoaHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DSHangHoaHoder(LayoutInflater.from(context).inflate(R.layout.cardviewkho, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DSHangHoaHoder hoder, int position) {
        hoder.txtTenHang.setText(HH.get(position).getsTenHang());
        hoder.txtDonVi.setText(HH.get(position).getsDonVi());
        hoder.txtSoLuong.setText(HH.get(position).getsSoLuong());
    }

    @Override
    public int getItemCount() {
        return HH.size();
    }


    class DSHangHoaHoder extends RecyclerView.ViewHolder
    {
        TextView txtTenHang, txtDonVi, txtSoLuong;
        public DSHangHoaHoder(@NonNull View itemView) {
            super(itemView);
            txtTenHang = itemView.findViewById(R.id.edtTenhang);
            txtDonVi = itemView.findViewById(R.id.edtDonVi);
            txtSoLuong = itemView.findViewById(R.id.edtSoLuong);
        }
    }

}
