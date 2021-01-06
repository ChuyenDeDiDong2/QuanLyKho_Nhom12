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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.team12.quanlykhohang_nhom12.Activity.ChiTietHopDongActivity;
import com.team12.quanlykhohang_nhom12.Library.ModelHopDong;
import com.team12.quanlykhohang_nhom12.Library.ModelKhoHang;
import com.team12.quanlykhohang_nhom12.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;

public class ThongKeTungKhoAdapter extends RecyclerView.Adapter<ThongKeTungKhoAdapter.HolderChuKho> {
    private Context context;
    public ArrayList<ModelKhoHang> list;

    public ThongKeTungKhoAdapter(Context context, ArrayList<ModelKhoHang> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public HolderChuKho onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //ìnfale layout row item show
        View view = LayoutInflater.from(context).inflate(R.layout.row_item_thongkekho, parent, false);
        return new HolderChuKho(view);
    }

    @Override
//, , , , , , , , , ;
    public void onBindViewHolder(@NonNull HolderChuKho holder, int position) {
        ModelKhoHang modelKhoHang = list.get(position);
        String khohangId = modelKhoHang.getKhohangId();
        String tenkho = modelKhoHang.getTenkho();
        String dientichkho = modelKhoHang.getDientichkho();
        String dienthoaikho = modelKhoHang.getDienthoaikho();
        String diachikhohang = modelKhoHang.getDiachikhohang();
        String chieucao = modelKhoHang.getChieucao();
        int tongthunhap = Integer.parseInt(modelKhoHang.getTongthunhap());
        String diachi = modelKhoHang.getDiachi();
        String giachothue = modelKhoHang.getGiachothue();
        String tinhtrangkho = modelKhoHang.getTinhtrangkho();
        String ghichukhho = modelKhoHang.getGhichukhho();
        String giamgiaAvailable = modelKhoHang.getGiamgiaAvailable();
        String giamoi = modelKhoHang.getGiamoi();
        String phantramkm = modelKhoHang.getPhantramkm();
        String hinhanhkho = modelKhoHang.getHinhanhkho();
        String dientichdathue = modelKhoHang.getDientichdathue();
        String timstamp = modelKhoHang.getTimstamp();
        String uid = modelKhoHang.getUid();

        //setdate
         Locale localeVN = new Locale("vi", "VN");
         NumberFormat vn = NumberFormat.getInstance(localeVN);
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(Long.parseLong(timstamp));
        String dateTime  = DateFormat.format("dd/MM/yyyy", cal).toString();
        holder.tv_ten_kho.setText("Kho: "+tenkho);
        holder.tv_tong_tien.setText("Tổng tiền: "+ vn.format(tongthunhap) + "Vnd");
        holder.tv_dien_tich_da_thue.setText("Diện tích đã thuê: "+dientichdathue+"m2");
        holder.tv_tong_dien_tich.setText("Tổng diện tích kho: "+dientichkho+"m2");


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(context, ChiTietHopDongActivity.class);
//                intent.putExtra("mahopdong",timstamp);
//                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //view holder
    class HolderChuKho extends RecyclerView.ViewHolder{
        private TextView tv_ten_kho , tv_tong_dien_tich, tv_dien_tich_da_thue, tv_tong_tien;
        private CardView cvchukhodc;

        public HolderChuKho(@NonNull View itemView) {
            super(itemView);

            cvchukhodc = itemView.findViewById(R.id.cvchukhodc);
            tv_ten_kho = itemView.findViewById(R.id.tv_ten_kho);
            tv_tong_dien_tich = itemView.findViewById(R.id.tv_tong_dien_tich);
            tv_dien_tich_da_thue = itemView.findViewById(R.id.tv_dien_tich_da_thue);
            tv_tong_tien = itemView.findViewById(R.id.tv_tong_tien);
            //


        }
        //orderByChild("hisUid").equalTo(firebaseAuth.getUid())

    }
}
