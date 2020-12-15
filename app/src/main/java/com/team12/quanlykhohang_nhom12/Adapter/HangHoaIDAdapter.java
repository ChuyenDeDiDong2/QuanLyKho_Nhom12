package com.team12.quanlykhohang_nhom12.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.team12.quanlykhohang_nhom12.Activity.HomeActivity;
import com.team12.quanlykhohang_nhom12.Activity.SuaHangHoaActivity;
import com.team12.quanlykhohang_nhom12.Filter.FilterHangHoa;
import com.team12.quanlykhohang_nhom12.Library.ModelHangHoa;
import com.team12.quanlykhohang_nhom12.R;

import java.util.ArrayList;

public class HangHoaIDAdapter extends RecyclerView.Adapter<HangHoaIDAdapter.HorderHangHoa> implements Filterable {
    Context context;
    public ArrayList<ModelHangHoa> hanghoalist, filterList;
    private FilterHangHoa filterHangHoa;

    public HangHoaIDAdapter(Context context, ArrayList<ModelHangHoa> hanghoalist) {
        this.context = context;
        this.hanghoalist = hanghoalist;
        this.filterList = hanghoalist;
    }

    @NonNull
    @Override
    public HangHoaIDAdapter.HorderHangHoa onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //
        View view  = LayoutInflater.from(context).inflate(R.layout.hanghoa_item_view, parent, false);
        return new HangHoaIDAdapter.HorderHangHoa(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HangHoaIDAdapter.HorderHangHoa holder, int position) {
        //get data
        ModelHangHoa modelHangHoa = hanghoalist.get(position);
        String id = modelHangHoa.getHanghoaId();
        String uid = modelHangHoa.getUid();
        String loaihang = modelHangHoa.getLoaihang();
        String soluong = modelHangHoa.getSoluong();
        String xuatxu = modelHangHoa.getXuatxu();
        String hinhanhhang = modelHangHoa.getHinhanhhang();
        //set data
        holder.txtloaihang.setText(loaihang);
        holder.txtsoluong.setText(soluong);
        holder.txtxuatxu.setText(xuatxu);
        try {
            Picasso.get().load(hinhanhhang).placeholder(R.drawable.google).into(holder.imghinhanhhang);
        }catch (Exception e)
        {
            holder.imghinhanhhang.setImageResource(R.drawable.google);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chitietBottomSheet(modelHangHoa);
            }
        });

    }

    private void chitietBottomSheet(ModelHangHoa modelHangHoa) {
        //bottom sheet
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.chitiet_hanghoa_item, null);
        bottomSheetDialog.setContentView(view);


        ImageButton btnthoat = view.findViewById(R.id.backbtn);
        ImageButton btnxoahang = view.findViewById(R.id.btnxoahang);
        ImageButton btnsuakho = view.findViewById(R.id.btnsuahang);
        ImageView hanghoa_icon = view.findViewById(R.id.hanghoa_icon);
        TextView  txtloaihang = view.findViewById(R.id.txtloaihang);
        TextView  txtsoluong = view.findViewById(R.id.txtsoluong);
        TextView  txtxuatxu = view.findViewById(R.id.txtxuatxu);

        String id = modelHangHoa.getHanghoaId();
        String uid = modelHangHoa.getUid();
        String loaihang = modelHangHoa.getLoaihang();
        String soluong = modelHangHoa.getSoluong();
        String xuatxu = modelHangHoa.getXuatxu();
        String hinhanhhang = modelHangHoa.getHinhanhhang();

        //setdata
        txtloaihang.setText(loaihang);
        txtsoluong.setText(soluong);
        txtxuatxu.setText(xuatxu);
        try {
            Picasso.get().load(hinhanhhang).placeholder(R.drawable.google).into(hanghoa_icon);
        }catch (Exception e)
        {
            hanghoa_icon.setImageResource(R.drawable.google);
        }
        //show diglog
        bottomSheetDialog.show();

        btnsuakho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
                Intent intent = new Intent(context, SuaHangHoaActivity.class);
                intent.putExtra("khohangId",id);
                context.startActivity(intent);
            }
        });
        //chuc nang xóa kho
        btnxoahang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
                //show delete  confire dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xóa")
                        .setMessage("Bạn có chắc muốn xóa"+loaihang+"?")
                        .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //xóa
                                xoaHangHoa(id);
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

    private void xoaHangHoa(String id) {
        //xóa hàng hoa
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tb_Users");
        reference.child(firebaseAuth.getUid()).child("Hanghoa").child(id).removeValue()
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
        return hanghoalist.size();
    }

    @Override
    public Filter getFilter() {
        if (filterHangHoa==null){
            filterHangHoa = new FilterHangHoa(this, filterList);

        }
        return filterHangHoa;
    }

    class HorderHangHoa extends RecyclerView.ViewHolder{
        private ImageView imghinhanhhang;
        private TextView txtloaihang, txtsoluong, txtxuatxu;
        public HorderHangHoa(@NonNull View itemView) {
            super(itemView);
            imghinhanhhang = itemView.findViewById(R.id.imghanghoa);;
            txtloaihang = itemView.findViewById(R.id.txtloaihang);
            txtsoluong = itemView.findViewById(R.id.txtsoluong);
            txtxuatxu = itemView.findViewById(R.id.txtxuatxu);
        }
    }
}

