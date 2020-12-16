package com.team12.quanlykhohang_nhom12.Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;
import com.team12.quanlykhohang_nhom12.Filter.FilterKhoHang;
import com.team12.quanlykhohang_nhom12.Library.ModelHangHoa;
import com.team12.quanlykhohang_nhom12.Library.ModelKhoHang;
import com.team12.quanlykhohang_nhom12.R;

import java.util.ArrayList;

public class HangHoaAdapter extends RecyclerView.Adapter<HangHoaAdapter.HorderHangHoa> {
    Context context;
    public ArrayList<ModelHangHoa> hanghoalist;

    public HangHoaAdapter(Context context, ArrayList<ModelHangHoa> hanghoalist) {
        this.context = context;
        this.hanghoalist = hanghoalist;
    }

    @NonNull
    @Override
    public HorderHangHoa onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //
        View view  = LayoutInflater.from(context).inflate(R.layout.row_item_hanghoa, parent, false);
        return new HorderHangHoa(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorderHangHoa holder, int position) {
        //get data
        ModelHangHoa modelHangHoa = hanghoalist.get(position);
        String id = modelHangHoa.getHanghoaId();
        String uid = modelHangHoa.getUid();
        String tensanpham = modelHangHoa.getTensanpham();
        String donvi = modelHangHoa.getDonvi();
        String dongia = modelHangHoa.getDongia();
        String soluong = modelHangHoa.getSoluong();
        String ghichu = modelHangHoa.getGhichu();
        String hinhanhhang = modelHangHoa.getHinhanhhang();
        String timstamphh = modelHangHoa.getTimstamphh();// , ,
        //set data
        holder.tv_item_stationery_name_product.setText("Tên sản phẩm: "+tensanpham);
        holder.tv_item_stationery_number.setText("Số lượng: "+soluong);
        holder.tv_item_stationery_price.setText("Đơn giá: "+dongia+" Vnd");
        holder.tv_item_stationery_note.setText("Ghí chú: "+ghichu);

        try {
            Picasso.get().load(hinhanhhang).placeholder(R.drawable.google).into(holder.iv_item_stationery_image_product);
        }catch (Exception e)
        {
            holder.iv_item_stationery_image_product.setImageResource(R.drawable.google);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //chitietBottomSheet(modelHangHoa);
            }
        });

    }

//    private void chitietBottomSheet(ModelHangHoa modelHangHoa) {
//        //bottom sheet
//        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
//        View view = LayoutInflater.from(context).inflate(R.layout.chitiet_khohang_item, null);
//        bottomSheetDialog.setContentView(view);
//
//
//        ImageButton btnthoat = view.findViewById(R.id.backbtn);
//        ImageButton btnxoakho = view.findViewById(R.id.btnxoakho);
//        ImageButton btnsuakho = view.findViewById(R.id.btnsuakho);
//        ImageButton btnquan_ly_hang = view.findViewById(R.id.btnquan_ly_hang);
//        ImageView  khohang_icon_ct = view.findViewById(R.id.khohang_icon_ct);
//        TextView  tvgiamgiapt_ct = view.findViewById(R.id.tvgiamgiapt_ct);
//
//        String id = modelKhoHang.getKhohangId();
//        String uid = modelKhoHang.getUid();
//        String giamgiaAvailable = modelKhoHang.getGiamgiaAvailable();
//        String tenkho = modelKhoHang.getTenkho();
//        String diachikhohang = modelKhoHang.getDiachikhohang();
//        String dientichkho = modelKhoHang.getDientichkho();
//        String dienthoaikho = modelKhoHang.getDienthoaikho();
//
//        String hinhanhkho = modelKhoHang.getHinhanhkho();
//        String timstamp = modelKhoHang.getTimstamp();
//
//        //setdata
//        tv_item_stationery_name_product.setText(tenkho);//tv_item_stationery_price, tv_item_stationery_note
//        tv_item_stationery_number.setText(diachikhohang);
//        tv_item_stationery_price.setText(dientichkho);
//        tv_item_stationery_note.setText(dienthoaikho);
//        tvgiabanct.setText(giachothue+"VND");
//
//        if(giamgiaAvailable.equals("true")){
//            tvgiagiamct.setVisibility(View.VISIBLE);
//            tvgiamgiapt_ct.setVisibility(View.VISIBLE);
//            tvgiabanct.setPaintFlags(tvgiabanct.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//        }
//        else {
//            tvgiagiamct.setVisibility(View.GONE);
//            tvgiamgiapt_ct.setVisibility(View.GONE);
//        }
//        try {
//            Picasso.get().load(hinhanhkho).placeholder(R.drawable.google).into(khohang_icon_ct);
//        }catch (Exception e)
//        {
//            khohang_icon_ct.setImageResource(R.drawable.google);
//        }
//        //show diglog
//        bottomSheetDialog.show();
//        //chuc nang sua kho
//
//
////        btnsuakho.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                bottomSheetDialog.dismiss();
////                Intent intent = new Intent(context, SuaKhoHangActivity.class);
////                intent.putExtra("khohangId",id);
////                context.startActivity(intent);
////            }
////        });
//        //chuc nang xóa kho
////        btnxoakho.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                bottomSheetDialog.dismiss();
////                //show delete  confire dialog
////                AlertDialog.Builder builder = new AlertDialog.Builder(context);
////                builder.setTitle("Xóa")
////                        .setMessage("Bạn có chắc muốn xóa"+tenkho+"?")
////                        .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
////                            @Override
////                            public void onClick(DialogInterface dialogInterface, int i) {
////                                //xóa
////                                xoaKhoHang(id);
////                            }
////                        })
////                        .setNegativeButton("Quay lại", new DialogInterface.OnClickListener() {
////                            @Override
////                            public void onClick(DialogInterface dialogInterface, int i) {
////                                dialogInterface.dismiss();
////                            }
////                        })
////                        .show();
////
////            }
////        });
//
//
//    }

//    private void xoaKhoHang(String id) {
//        //xóa kho hàng
//        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tb_Users");
//        reference.child(firebaseAuth.getUid()).child("KhoHang").child(id).removeValue()
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Toast.makeText(context, "Xóa kho hàng thành công!", Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }

    @Override
    public int getItemCount() {
        return hanghoalist.size();
    }


    class HorderHangHoa extends RecyclerView.ViewHolder{
        private ImageView iv_item_stationery_image_product;
        private TextView tv_item_stationery_name_product, tv_item_stationery_number, tv_item_stationery_price, tv_item_stationery_note;
        private ImageButton ibt_item_stationery_show_menu;
        public HorderHangHoa(@NonNull View itemView) {
            super(itemView);
            iv_item_stationery_image_product = itemView.findViewById(R.id.iv_item_stationery_image_product);
            tv_item_stationery_name_product = itemView.findViewById(R.id.tv_item_stationery_name_product);
            tv_item_stationery_number = itemView.findViewById(R.id.tv_item_stationery_number);
            tv_item_stationery_price = itemView.findViewById(R.id.tv_item_stationery_price);
            tv_item_stationery_note = itemView.findViewById(R.id.tv_item_stationery_note);
            ibt_item_stationery_show_menu = itemView.findViewById(R.id.ibt_item_stationery_show_menu);
        }
    }
}
