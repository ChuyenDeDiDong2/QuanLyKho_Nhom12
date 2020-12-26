package com.team12.quanlykhohang_nhom12.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.team12.quanlykhohang_nhom12.Activity.HomeActivity;
import com.team12.quanlykhohang_nhom12.Activity.SuaKhoHangActivity;
import com.team12.quanlykhohang_nhom12.Filter.FilterKhoHang;
import com.team12.quanlykhohang_nhom12.Library.ModelKhoHang;
import com.team12.quanlykhohang_nhom12.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class KhoHangIDAdapter extends RecyclerView.Adapter<KhoHangIDAdapter.HorderKhoHang> implements Filterable {
    Context context;
    public ArrayList<ModelKhoHang> khohanglist, filterList;
    private FilterKhoHang filterKhoHang;

    public KhoHangIDAdapter(Context context, ArrayList<ModelKhoHang> khohanglist) {
        this.context = context;
        this.khohanglist = khohanglist;
        this.filterList = khohanglist;
    }

    @NonNull
    @Override
    public HorderKhoHang onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //
        View view  = LayoutInflater.from(context).inflate(R.layout.kho_item_view, parent, false);
        return new HorderKhoHang(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorderKhoHang holder, int position) {
        //get data
        ModelKhoHang modelKhoHang = khohanglist.get(position);
        String id = modelKhoHang.getKhohangId();
        String uid = modelKhoHang.getUid();
        String giamgiaAvailable = modelKhoHang.getGiamgiaAvailable();
        String tenkho = modelKhoHang.getTenkho();
        String diachikhohang = modelKhoHang.getDiachikhohang();
        String dientichkho = modelKhoHang.getDientichkho();
        String dienthoaikho = modelKhoHang.getDienthoaikho();
        int giachothue = Integer.parseInt(modelKhoHang.getGiachothue());
        String tinhtrangkho = modelKhoHang.getTinhtrangkho();
        String ghichukhho = modelKhoHang.getGhichukhho();
        int giamoi = Integer.parseInt(modelKhoHang.getGiamoi());
        String phantramkm = modelKhoHang.getPhantramkm();
        String hinhanhkho = modelKhoHang.getHinhanhkho();
        String timstamp = modelKhoHang.getTimstamp();
        //set data
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat vn = NumberFormat.getInstance(localeVN);
        holder.cvkhokang.setAnimation(AnimationUtils.loadAnimation(context, R.anim.scale_list));

        holder.tvtenkho_hang.setText("Tên kho: "+ tenkho);
        holder.tvdientich_kho.setText("Diện tích: "+ dientichkho);
        //holder.tvdientich_kho.setText(dientichkho);
        holder.tv_giamgiapt.setText(phantramkm);
        holder.tvgiagiam.setText(vn.format(giamoi)+"VND");
        holder.tvgiaban.setText(vn.format(giachothue)+"VND");
        if(giamgiaAvailable.equals("true")){
            holder.tvgiagiam.setVisibility(View.VISIBLE);
            holder.tv_giamgiapt.setVisibility(View.VISIBLE);
            holder.tvgiaban.setPaintFlags(holder.tvgiaban.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        else {
            holder.tvgiagiam.setVisibility(View.GONE);
            holder.tv_giamgiapt.setVisibility(View.GONE);
        }
        try {
            Picasso.get().load(hinhanhkho).placeholder(R.drawable.google).into(holder.imghinhanhkho);
        }catch (Exception e)
        {
            holder.imghinhanhkho.setImageResource(R.drawable.google);
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
        View view = LayoutInflater.from(context).inflate(R.layout.chitiet_khohang_item, null);
        bottomSheetDialog.setContentView(view);


        ImageButton btnthoat = view.findViewById(R.id.backbtn);
        ImageButton btnxoakho = view.findViewById(R.id.btnxoakho);
        ImageButton btnsuakho = view.findViewById(R.id.btnsuakho);
        ImageButton btnquan_ly_hang = view.findViewById(R.id.btnquan_ly_hang);
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
        int giachothue =Integer.parseInt(modelKhoHang.getGiachothue());
        String tinhtrangkho = modelKhoHang.getTinhtrangkho();
        String ghichukhho = modelKhoHang.getGhichukhho();
        int giamoi = Integer.parseInt(modelKhoHang.getGiamoi());
        String phantramkm = modelKhoHang.getPhantramkm();
        String hinhanhkho = modelKhoHang.getHinhanhkho();
        String timstamp = modelKhoHang.getTimstamp();

        //setdata

        Locale localeVN = new Locale("vi", "VN");
        NumberFormat vn = NumberFormat.getInstance(localeVN);
        tvten_kho_hang_Ct.setText(tenkho);
        tvdia_chi_kho_ct.setText(diachikhohang);
        tvdien_tich_kho_ct.setText(dientichkho);
        tvsodienthoai_kho_ct.setText(dienthoaikho);
        tvgiabanct.setText(vn.format(giachothue)+"VND");
        tvgiagiamct.setText(vn.format(giamoi)+"VND");
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
        //chuc nang sua kho
        btnquan_ly_hang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, HomeActivity.class);
                intent.putExtra("khohangId", id);
                context.startActivity(intent);
            }
        });

        btnsuakho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
                Intent intent = new Intent(context, SuaKhoHangActivity.class);
                intent.putExtra("khohangId",id);
                context.startActivity(intent);
            }
        });
        //chuc nang xóa kho
        btnxoakho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
                //show delete  confire dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xóa")
                        .setMessage("Bạn có chắc muốn xóa"+tenkho+"?")
                        .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //xóa
                                xoaKhoHang(id);
                            }
                        })
                        .setNegativeButton("Quay lại", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .show();

            }
        });
        //chuc nang thoát
        btnthoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
            }
        });

    }

    private void xoaKhoHang(String id) {
        //xóa kho hàng
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tb_Users");
        reference.child(firebaseAuth.getUid()).child("KhoHang").child(id).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, "Xóa kho hàng thành công!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public int getItemCount() {
        return khohanglist.size();
    }

    @Override
    public Filter getFilter() {
        if (filterKhoHang==null){
            filterKhoHang = new FilterKhoHang(this, filterList);

        }
        return filterKhoHang;
    }

    class HorderKhoHang extends RecyclerView.ViewHolder{
        private ImageView imghinhanhkho;
        private TextView tvtenkho_hang, tv_giamgiapt, tvgiaban, tvdientich_kho, tvgiagiam;
        private CardView cvkhokang;
        public HorderKhoHang(@NonNull View itemView) {
            super(itemView);
             imghinhanhkho = itemView.findViewById(R.id.imgkho_hang);
            tv_giamgiapt = itemView.findViewById(R.id.tvgiamgiapt);
            tvtenkho_hang = itemView.findViewById(R.id.tvten_kho_hang);
            tvgiaban = itemView.findViewById(R.id.tvgiaban);
            tvgiagiam = itemView.findViewById(R.id.tvgiagiam);
            tvdientich_kho = itemView.findViewById(R.id.tvdien_tich_kho);
            cvkhokang = itemView.findViewById(R.id.cvkhokang);
        }
    }
}
