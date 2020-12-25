package com.team12.quanlykhohang_nhom12.Adapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.team12.quanlykhohang_nhom12.Activity.ChiTietChuKhoActivity;
import com.team12.quanlykhohang_nhom12.Activity.DangKyTaoHopDongActivity;
import com.team12.quanlykhohang_nhom12.Activity.NhanTinActivity;
import com.team12.quanlykhohang_nhom12.Fragment.TaiKhoanKhoFragment;
import com.team12.quanlykhohang_nhom12.Library.ModelChuKho;
import com.team12.quanlykhohang_nhom12.Library.ModelKhoHang;
import com.team12.quanlykhohang_nhom12.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class HangKhoAdapter extends RecyclerView.Adapter<HangKhoAdapter.HolderChuKho> {
    private Context context;
    public ArrayList<ModelChuKho> chukhoList;

    public HangKhoAdapter(Context context, ArrayList<ModelChuKho> chukhoList) {
        this.context = context;
        this.chukhoList = chukhoList;
    }

    @NonNull
    @Override
    public HolderChuKho onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //ìnfale layout row item show
        View view = LayoutInflater.from(context).inflate(R.layout.row_item_admin, parent, false);
        return new HolderChuKho(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderChuKho holder, int position) {
        ModelChuKho modelChuKho = chukhoList.get(position);
        String accountType = modelChuKho.getAccountType();
        String name = modelChuKho.getName();
        final String uid = modelChuKho.getUid();
        String phone = modelChuKho.getPhone();
        final String email = modelChuKho.getEmail();
        String sotaikhoan = modelChuKho.getSotaikhoan();
        String tentaikhoan = modelChuKho.getTentaikhoan();
        String online = modelChuKho.getOnline();
        String cuahangOpen = modelChuKho.getOpen();
        String noibat = modelChuKho.getNoibat();
        String block = modelChuKho.getBlock();
        String profileImage = modelChuKho.getProfileImage();
        //setdate

        holder.cvchukhodc.setAnimation(AnimationUtils.loadAnimation(context, R.anim.scale_list));
        holder.tvten_hk.setText(tentaikhoan);
        holder.tvsodienthoai_hk.setText(phone);
//        if (block.equals("true"))
//        {
//            holder.btnkhoataikhoan.setVisibility(View.GONE);
//        }
//        else {
//            holder.btnkhoataikhoan.setVisibility(View.VISIBLE);
//        }
//        if (block.equals("true"))
//        {
//            holder.btnmokhoataikhoan.setVisibility(View.VISIBLE);
//        }
//        else {
//            holder.btnmokhoataikhoan.setVisibility(View.GONE);
//        }

        if (online.equals("true")){
            //Chủ kho online:
            holder.ivonline_status.setVisibility(View.VISIBLE);
        }
        else {
            //Chủ kho offline:
            holder.ivonline_status.setVisibility(View.GONE);
        }

        try {
            Picasso.get().load(profileImage).placeholder(R.drawable.google).into(holder.ivpeople_hk);
        }catch (Exception e)
        {
            holder.ivpeople_hk.setImageResource(R.drawable.google);
        }
        //Nhấp vào để xem chi tiết kho:
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chitietBottomSheet(modelChuKho);
            }
        });

        //Nút Xóa tài khoản:
        holder.btnxoataikhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xóa")
                        .setMessage("Bạn có chắc muốn xóa"+tentaikhoan+"?")
                        .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //xóa
                                //xoaTaiKhoan(uid);

                                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tb_Users");
                                //reference.child(firebaseAuth.getUid()).child(uid).removeValue()
                                Query mQuery = reference.orderByChild("uid").equalTo(uid);
                                mQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot ds: snapshot.getChildren()){
                                            ds.getRef().removeValue();
                                            Toast.makeText(context, "Xóa tài khoản thành công!", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Toast.makeText(context, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
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
        //Thực hiện chức năng khóa tk:
        holder.btnkhoataikhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String block = "true";
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                ProgressDialog progressDialog = new ProgressDialog(context);
                HashMap<String, Object> hashMap = new HashMap<>();

                hashMap.put("block", ""+ block);

                //cap nhat db
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tb_Users");
                reference.child(uid)
                        .updateChildren(hashMap)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                //update
                                progressDialog.dismiss();
                                Toast.makeText(context, "Tài khoản đã bị khóa!",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                //update failed
                                progressDialog.dismiss();
                                Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        holder.btnmokhoataikhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String block = "false";
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                ProgressDialog progressDialog = new ProgressDialog(context);
                HashMap<String, Object> hashMap = new HashMap<>();

                hashMap.put("block", ""+ block);

                //cap nhat db
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tb_Users");
                reference.child(uid)
                        .updateChildren(hashMap)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                //update
                                progressDialog.dismiss();
                                Toast.makeText(context, "Tài khoản đã được mở!",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                //update failed
                                progressDialog.dismiss();
                                Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
    //
    private void chitietBottomSheet(ModelChuKho modelChuKho) {
        //bottom sheet
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.chitiet_hangkho_item, null);
        bottomSheetDialog.setContentView(view);


        ImageButton btnthoat = view.findViewById(R.id.backbtn);
        TextView tvtenhang0 = view.findViewById(R.id.tvtenhang0);
        ImageView khohang_icon_ct = view.findViewById(R.id.khohang_icon_ct);
        TextView tvtenchuhang = view.findViewById(R.id.tvtenchuhang);
        TextView  tvdiachihang = view.findViewById(R.id.tvdiachihang);
        TextView  tvsodientthoaihang = view.findViewById(R.id.tvsodientthoaihang);
        TextView  tvemailhang = view.findViewById(R.id.tvemailhang);
        TextView  tvsotaikhoanhang = view.findViewById(R.id.tvsotaikhoanhang);

        String accountType = modelChuKho.getAccountType();
        String name = modelChuKho.getName();
        final String uid = modelChuKho.getUid();
        String phone = modelChuKho.getPhone();
        final String email = modelChuKho.getEmail();
        String sotaikhoan = modelChuKho.getSotaikhoan();
        String tentaikhoan = modelChuKho.getTentaikhoan();
        String online = modelChuKho.getOnline();
        String diachi = modelChuKho.getDiachi();
        String cuahangOpen = modelChuKho.getOpen();
        String noibat = modelChuKho.getNoibat();
        String block = modelChuKho.getBlock();
        String profileImage = modelChuKho.getProfileImage();

        //setdata trên firebase:

        Locale localeVN = new Locale("vi", "VN");
        NumberFormat vn = NumberFormat.getInstance(localeVN);
        tvtenhang0.setText(tentaikhoan);
        tvtenchuhang.setText(name);
        tvdiachihang.setText(diachi);
        tvsodientthoaihang.setText(phone);
        tvemailhang.setText(email);
        tvsotaikhoanhang.setText(sotaikhoan);

        try {
            Picasso.get().load(profileImage).placeholder(R.drawable.google).into(khohang_icon_ct);
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


    }
    @Override
    public int getItemCount() {
        return chukhoList.size();
    }

    //view holder
    class HolderChuKho extends RecyclerView.ViewHolder{
        private ImageView ivpeople_hk, ivonline_status;
        private TextView tvten_hk, tvsodienthoai_hk;
        private RatingBar ratingbarchukho;
        private CardView cvchukhodc;
        private ImageButton btnxoataikhoan, btnkhoataikhoan, btnmokhoataikhoan;

        public HolderChuKho(@NonNull View itemView) {
            super(itemView);
            cvchukhodc = itemView.findViewById(R.id.cvchukhodc);
            ivpeople_hk = itemView.findViewById(R.id.ivpeople_hk);
            ivonline_status = itemView.findViewById(R.id.ivonline_status);
            tvten_hk = itemView.findViewById(R.id.tvten_hk);
            tvsodienthoai_hk = itemView.findViewById(R.id.tvsodienthoai_hk);
            //xoa tai khoan
            btnxoataikhoan = itemView.findViewById(R.id.btnxoataikhoan);
            btnkhoataikhoan = itemView.findViewById(R.id.btnkhoataikhoan);
            btnmokhoataikhoan = itemView.findViewById(R.id.btnmokhoataikhoan);
        }
    }
}
