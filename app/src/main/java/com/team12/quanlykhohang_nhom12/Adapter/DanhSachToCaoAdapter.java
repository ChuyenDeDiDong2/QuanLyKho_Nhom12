package com.team12.quanlykhohang_nhom12.Adapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.team12.quanlykhohang_nhom12.Activity.ChiTietHopDongActivity;
import com.team12.quanlykhohang_nhom12.Activity.HomeActivity;
import com.team12.quanlykhohang_nhom12.Activity.SuaKhoHangActivity;
import com.team12.quanlykhohang_nhom12.Library.ModelHopDong;
import com.team12.quanlykhohang_nhom12.Library.ModelKhoHang;
import com.team12.quanlykhohang_nhom12.Library.ModelToCao;
import com.team12.quanlykhohang_nhom12.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class DanhSachToCaoAdapter extends RecyclerView.Adapter<DanhSachToCaoAdapter.HolderChuKho> {
    private Context context;
    public ArrayList<ModelToCao> list;

    public DanhSachToCaoAdapter(Context context, ArrayList<ModelToCao> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public HolderChuKho onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //ìnfale layout row item show
        View view = LayoutInflater.from(context).inflate(R.layout.row_item_tocao, parent, false);
        return new HolderChuKho(view);
    }

    @Override

    public void onBindViewHolder(@NonNull HolderChuKho holder, int position) {
        ModelToCao modelToCao = list.get(position);
        String uid = modelToCao.getUid();
        String noidungtocao = modelToCao.getNoidungtocao();
        String tennguoigui = modelToCao.getTennguoigui();
        String hangkho = modelToCao.getHangkho();
        String timestamp = modelToCao.getTimestamp();
        String hisUid = modelToCao.getHisUid();
        String myUid = modelToCao.getMyUid();

        //setdate
        holder.cvchukhodc.setAnimation(AnimationUtils.loadAnimation(context, R.anim.scale_list));
        holder.tvtenguoitocao.setText("Tên người tố cáo : "+tennguoigui);
        holder.tvhangbitocao.setText("Tên hãng bị tố cáo : "+hangkho);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chitietBottomSheet(modelToCao);
                String tocaochuadoc = "false";
                ProgressDialog progressDialog = new ProgressDialog(context);
                HashMap<String, Object> hashMap = new HashMap<>();

                hashMap.put("tocaochuadoc", "" + tocaochuadoc);

                //cap nhat db
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tb_ToCao");
                reference.child(timestamp)
                        .updateChildren(hashMap)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                //update
                                progressDialog.dismiss();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                //update failed
                                progressDialog.dismiss();
                                Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }



    private void chitietBottomSheet(ModelToCao modelToCao) {
        //bottom sheet
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.chitiet_tocao_item, null);
        bottomSheetDialog.setContentView(view);

        TextView  tvngaythang = view.findViewById(R.id.tvngaythang);
        TextView  tvtennguoitocao = view.findViewById(R.id.tvtennguoitocao);
        TextView  tvtenhangbitocao = view.findViewById(R.id.tvtenhangbitocao);
        TextView  tvnoidung = view.findViewById(R.id.tvnoidung);


        ImageButton btnthoat = view.findViewById(R.id.backbtn);
        String uid = modelToCao.getUid();
        String noidungtocao = modelToCao.getNoidungtocao();
        String tennguoigui = modelToCao.getTennguoigui();
        String hangkho = modelToCao.getHangkho();
        String timestamp = modelToCao.getTimestamp();
        String hisUid = modelToCao.getHisUid();
        String myUid = modelToCao.getMyUid();
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(Long.parseLong(timestamp));
        String dateTime  = DateFormat.format("dd/MM/yyyy", cal).toString();

        //setdata
        tvngaythang.setText(dateTime);
        tvtennguoitocao.setText(tennguoigui);
        tvtenhangbitocao.setText(hangkho);
        tvnoidung.setText(noidungtocao);

        //chuc nang thoát
        btnthoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
            }
        });
        //show diglog
        bottomSheetDialog.show();

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //view holder
    class HolderChuKho extends RecyclerView.ViewHolder{
        private TextView tvtenguoitocao,tvhangbitocao ;
        private CardView cvchukhodc;

        public HolderChuKho(@NonNull View itemView) {
            super(itemView);

            cvchukhodc = itemView.findViewById(R.id.cvchukhodc);
            tvtenguoitocao = itemView.findViewById(R.id.tvtenguoitocao);
            tvhangbitocao = itemView.findViewById(R.id.tvhangbitocao);


        }
    }
}
