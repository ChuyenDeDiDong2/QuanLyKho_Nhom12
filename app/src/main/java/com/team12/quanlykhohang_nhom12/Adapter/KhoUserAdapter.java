package com.team12.quanlykhohang_nhom12.Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.team12.quanlykhohang_nhom12.Filter.FilterKhoHangUser;
import com.team12.quanlykhohang_nhom12.Library.ModelKhoHang;
import com.team12.quanlykhohang_nhom12.R;

import java.util.ArrayList;

public class KhoUserAdapter extends RecyclerView.Adapter<KhoUserAdapter.HolderKhoUser> implements Filterable {
    private Context context;
    public ArrayList<ModelKhoHang> khohangList, filterList;
    private FilterKhoHangUser filterKhoHangUser;

    public KhoUserAdapter(Context context, ArrayList<ModelKhoHang> khohangList) {
        this.context = context;
        this.khohangList = khohangList;
    }

    @NonNull
    @Override
    public HolderKhoUser onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.kho_item_view_user, parent, false);

        return new HolderKhoUser(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderKhoUser holder, int position) {
        //get data
        ModelKhoHang modelKhoHang = khohangList.get(position);
        String id = modelKhoHang.getKhohangId();
        String uid = modelKhoHang.getUid();
        String giamgiaAvailable = modelKhoHang.getGiamgiaAvailable();
        String tenkho = modelKhoHang.getTenkho();
        String diachikhohang = modelKhoHang.getDiachikhohang();
        String dientichkho = modelKhoHang.getDientichkho();
        String dienthoaikho = modelKhoHang.getDienthoaikho();
        String giachothue = modelKhoHang.getGiachothue();
        String tinhtrangkho = modelKhoHang.getTinhtrangkho();
        String ghichukhho = modelKhoHang.getGhichukhho();
        String giamoi = modelKhoHang.getGiamoi();
        String phantramkm = modelKhoHang.getPhantramkm();
        String hinhanhkho = modelKhoHang.getHinhanhkho();
        String timstamp = modelKhoHang.getTimstamp();
        //set data
        holder.tvten_kho_hang.setText(tenkho);
        holder.tvdien_tich_kho.setText(dientichkho);
        //holder.tvdientich_kho.setText(dientichkho);
        holder.tvgiamgiapt.setText(phantramkm);
        holder.tvgiagiam.setText(giamoi+"VND");
        holder.tvgiaban.setText(giachothue+"VND");
        if(giamgiaAvailable.equals("true")){
            holder.tvgiagiam.setVisibility(View.VISIBLE);
            holder.tvgiamgiapt.setVisibility(View.VISIBLE);
            holder.tvgiaban.setPaintFlags(holder.tvgiaban.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        else {
            holder.tvgiagiam.setVisibility(View.GONE);
            holder.tvgiamgiapt.setVisibility(View.GONE);
        }
        try {
            Picasso.get().load(hinhanhkho).placeholder(R.drawable.google).into(holder.imgkho_hang);
        }catch (Exception e)
        {
            holder.imgkho_hang.setImageResource(R.drawable.google);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //chitietBottomSheet(modelKhoHang);
            }
        });
    }

    @Override
    public int getItemCount() {
        return khohangList.size();
    }

    @Override
    public Filter getFilter() {
        if (filterKhoHangUser==null)
        {
            filterKhoHangUser = new FilterKhoHangUser(this, filterList);

        }
        return filterKhoHangUser;
    }

    class HolderKhoUser extends RecyclerView.ViewHolder{
        //uid view
        private ImageView imgkho_hang;
        private TextView tvgiamgiapt, tvten_kho_hang, tvdien_tich_kho, tvgiaban, tvgiagiam;

        public HolderKhoUser(@NonNull View itemView) {
            super(itemView);
            //init ui view
            imgkho_hang = itemView.findViewById(R.id.imgkho_hang);
            tvgiamgiapt = itemView.findViewById(R.id.tvgiamgiapt);
            tvten_kho_hang = itemView.findViewById(R.id.tvten_kho_hang);
            tvdien_tich_kho = itemView.findViewById(R.id.tvdien_tich_kho);
            tvgiaban = itemView.findViewById(R.id.tvgiaban);
            tvgiagiam = itemView.findViewById(R.id.tvgiagiam);
        }

    }
}
