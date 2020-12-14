package com.team12.quanlykhohang_nhom12.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;
import com.team12.quanlykhohang_nhom12.Activity.NhanTinActivity;
import com.team12.quanlykhohang_nhom12.Activity.TaoHopDongActivity;
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
        this.filterList = khohangList;
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
                chitietBottomSheet(modelKhoHang);
            }
        });
    }

    private void chitietBottomSheet(ModelKhoHang modelKhoHang) {
        //bottom sheet
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.chitiet_khohang_item_user, null);
        bottomSheetDialog.setContentView(view);


        ImageButton btnthoat = view.findViewById(R.id.backbtn);
        Button btnnhantin = view.findViewById(R.id.btn_nhan_tin);
        Button btntaohopdong = view.findViewById(R.id.btntao_hop_dong);
        ImageView  khohang_icon_ct = view.findViewById(R.id.khohang_icon_ct);
        TextView  tvgiamgiapt_ct = view.findViewById(R.id.tvgiamgiapt_ct);
        TextView  tvten_kho_hang_Ct = view.findViewById(R.id.tvten_kho_hang_Ct);
        TextView  tvdia_chi_kho_ct = view.findViewById(R.id.tvdia_chi_kho_ct);
        TextView  tvdien_tich_kho_ct = view.findViewById(R.id.tvdien_tich_kho_ct);
        TextView  tvsodienthoai_kho_ct = view.findViewById(R.id.tvsodienthoai_kho_ct);
        TextView  tvdanhmuckho_ct = view.findViewById(R.id.tvdanhmuckho_ct);
        TextView  tvghichu_khoct = view.findViewById(R.id.tvghichu_khoct);
        TextView  tvgiabanct = view.findViewById(R.id.tvgiabanct);
        TextView  tvgiagiamct = view.findViewById(R.id.tvgiagiamct);

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

        //setdata
        tvten_kho_hang_Ct.setText(tenkho);
        tvdia_chi_kho_ct.setText(diachikhohang);
        tvdien_tich_kho_ct.setText(dientichkho);
        tvsodienthoai_kho_ct.setText(dienthoaikho);
        tvgiabanct.setText(giachothue+"VND");
        tvgiagiamct.setText(giamoi+"VND");
        tvdanhmuckho_ct.setText(tinhtrangkho);
        tvghichu_khoct.setText(ghichukhho);
        tvgiamgiapt_ct.setText(phantramkm);
        if(giamgiaAvailable.equals("true")){
            tvgiagiamct.setVisibility(View.VISIBLE);
            tvgiamgiapt_ct.setVisibility(View.VISIBLE);
            tvgiabanct.setPaintFlags(tvgiabanct.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        else {
            tvgiagiamct.setVisibility(View.GONE);
            tvgiamgiapt_ct.setVisibility(View.GONE);
        }
        try {
            Picasso.get().load(hinhanhkho).placeholder(R.drawable.google).into(khohang_icon_ct);
        }catch (Exception e)
        {
            khohang_icon_ct.setImageResource(R.drawable.google);
        }
        //show diglog
        bottomSheetDialog.show();
        //chuc nang thoát
        btnthoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
            }
        });
        btnnhantin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //view.getContext().startActivity(new Intent(context, NhanTinActivity.class));
                Intent intent = new Intent(context, NhanTinActivity.class);
                intent.putExtra("hisUid", uid);
                context.startActivity(intent);
            }
        });
        btntaohopdong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.getContext().startActivity(new Intent(context, TaoHopDongActivity.class));
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
