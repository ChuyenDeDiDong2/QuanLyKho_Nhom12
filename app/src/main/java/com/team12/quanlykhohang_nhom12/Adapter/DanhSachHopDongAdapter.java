package com.team12.quanlykhohang_nhom12.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.team12.quanlykhohang_nhom12.Activity.ChiTietHopDongActivity;
import com.team12.quanlykhohang_nhom12.Library.ModelDangKyThue;
import com.team12.quanlykhohang_nhom12.Library.ModelHopDong;
import com.team12.quanlykhohang_nhom12.R;

import java.util.ArrayList;
import java.util.HashMap;

public class DanhSachHopDongAdapter extends RecyclerView.Adapter<DanhSachHopDongAdapter.HolderChuKho> {
    private Context context;
    public ArrayList<ModelHopDong> list;

    public DanhSachHopDongAdapter(Context context, ArrayList<ModelHopDong> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public HolderChuKho onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //ìnfale layout row item show
        View view = LayoutInflater.from(context).inflate(R.layout.row_item_banhopdong, parent, false);
        return new HolderChuKho(view);
    }

    @Override
    // , , , , , , ;
    //  , , , , , , ;
    //  , , ,  ,, ;
    public void onBindViewHolder(@NonNull HolderChuKho holder, int position) {
        ModelHopDong modelHopDong = list.get(position);
        String uid = modelHopDong.getUid();
        String tongtien = modelHopDong.getTongtien();
        String timstamp = modelHopDong.getTimstamp();
        String thongbaothue = modelHopDong.getThongbaothue();
        String thoigianthue = modelHopDong.getThoigianthue();
        String tenthue = modelHopDong.getTenthue();
        String tenkho = modelHopDong.getTenkho();
        String tendoanhnghiep = modelHopDong.getTendoanhnghiep();
        String sodienthoaibenb = modelHopDong.getSodienthoaibenb();
        String noidungdieukhoan = modelHopDong.getNoidungdieukhoan();
        String masothue = modelHopDong.getMasothue();
        String khohangId = modelHopDong.getKhohangId();
        String hisUid = modelHopDong.getHisUid();
        String giayphepso = modelHopDong.getGiayphepso();
        String emailbenb = modelHopDong.getEmailbenb();
        String emailbena = modelHopDong.getEmailbena();
        String dientichthue = modelHopDong.getDientichthue();
        String diachibena = modelHopDong.getDiachibena();
        String diachibenb = modelHopDong.getDiachibenb();
        String dienthoaibena = modelHopDong.getDienthoaibena();
        //setdate
        holder.cvchukhodc.setAnimation(AnimationUtils.loadAnimation(context, R.anim.scale_list));
        holder.tvmahopdong.setText("Mã hợp đồng: "+timstamp);
        holder.tvtennguoithe.setText("Tên người thuê: "+tenthue);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//
                Intent intent = new Intent(context, ChiTietHopDongActivity.class);
                intent.putExtra("mahopdong",timstamp);
                context.startActivity(intent);
            }
        });

    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    //view holder
    class HolderChuKho extends RecyclerView.ViewHolder{
        private TextView tvmahopdong,tvtennguoithe ;
        private CardView cvchukhodc;

        public HolderChuKho(@NonNull View itemView) {
            super(itemView);

            cvchukhodc = itemView.findViewById(R.id.cvchukhodc);
            tvmahopdong = itemView.findViewById(R.id.tvmahopdong);
            tvtennguoithe = itemView.findViewById(R.id.tvtennguoithe);
            //


        }
    }
}
