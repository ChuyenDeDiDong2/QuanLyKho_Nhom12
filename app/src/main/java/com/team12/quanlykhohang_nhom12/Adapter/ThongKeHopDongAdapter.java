package com.team12.quanlykhohang_nhom12.Adapter;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.team12.quanlykhohang_nhom12.Activity.ChiTietHopDongActivity;
import com.team12.quanlykhohang_nhom12.Library.ModelHopDong;
import com.team12.quanlykhohang_nhom12.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class ThongKeHopDongAdapter extends RecyclerView.Adapter<ThongKeHopDongAdapter.HolderChuKho> {
    private Context context;
    public ArrayList<ModelHopDong> list;

    public ThongKeHopDongAdapter(Context context, ArrayList<ModelHopDong> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public HolderChuKho onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //ìnfale layout row item show
        View view = LayoutInflater.from(context).inflate(R.layout.row_item_hopdong, parent, false);
        return new HolderChuKho(view);
    }

    @Override

    public void onBindViewHolder(@NonNull HolderChuKho holder, int position) {
        ModelHopDong modelHopDong = list.get(position);
        String uid = modelHopDong.getUid();
        int tongtien = Integer.parseInt(modelHopDong.getTongtien());
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
         Locale localeVN = new Locale("vi", "VN");
         NumberFormat vn = NumberFormat.getInstance(localeVN);
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(Long.parseLong(timstamp));
        String dateTime  = DateFormat.format("dd/MM/yyyy", cal).toString();
        holder.cvchukhodc.setAnimation(AnimationUtils.loadAnimation(context, R.anim.scale_list));
        holder.tv_ma_hop_dong.setText("Mã hợp đồng: "+timstamp);
        holder.tv_ngay_thue.setText("Ngày thuê: "+dateTime);
        holder.tv_ngay_het_han.setText("Ngày hết hạn: "+"2020");
        holder.tv_tong_tien.setText("Doanh thu: "+vn.format(tongtien)+ "Vnd");

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
        private TextView tv_ma_hop_dong , tv_ngay_thue, tv_ngay_het_han, tv_tong_tien;
        private CardView cvchukhodc;

        public HolderChuKho(@NonNull View itemView) {
            super(itemView);

            cvchukhodc = itemView.findViewById(R.id.cvchukhodc);
            tv_ma_hop_dong = itemView.findViewById(R.id.tv_ma_hop_dong);
            tv_ngay_thue = itemView.findViewById(R.id.tv_ngay_thue);
            tv_ngay_het_han = itemView.findViewById(R.id.tv_ngay_het_han);
            tv_tong_tien = itemView.findViewById(R.id.tv_tong_tien);
            //


        }
    }
}
