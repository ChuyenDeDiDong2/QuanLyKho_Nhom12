package com.team12.quanlykhohang_nhom12.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.team12.quanlykhohang_nhom12.Library.ModelChuKho;
import com.team12.quanlykhohang_nhom12.Library.ModelUser;
import com.team12.quanlykhohang_nhom12.R;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.HolderUser> {
    private Context context;
    public ArrayList<ModelUser> userlist;

    public UserAdapter(Context context, ArrayList<ModelUser> userlist) {
        this.context = context;
        this.userlist = userlist;
    }

    @NonNull
    @Override
    public HolderUser onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //ìnfale layout row item show
        View view = LayoutInflater.from(context).inflate(R.layout.row_item_admin, parent, false);
        return new HolderUser(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderUser holder, int position) {
        ModelUser modelUser = userlist.get(position);
        String accountType = modelUser.getAccountType();
        String name = modelUser.getName();
        final String uid = modelUser.getUid();
        String phone = modelUser.getPhone();
        final String email = modelUser.getEmail();
        String sotaikhoan = modelUser.getSotaikhoan();
        String tentaikhoan = modelUser.getTentaikhoan();
        String online = modelUser.getOnline();
        String cuahangOpen = modelUser.getOpen();
        String noibat = modelUser.getNoibat();
        String profileImage = modelUser.getProfileImage();
        //setdate

        holder.cvchukhodc.setAnimation(AnimationUtils.loadAnimation(context, R.anim.scale_list));
        holder.tvten_hk.setText(name);
        holder.tvsodienthoai_hk.setText(phone);

        if (online.equals("true")){
            //chu kho online
            holder.ivonline_status.setVisibility(View.VISIBLE);
        }
        else {
            //chu kho offline
            holder.ivonline_status.setVisibility(View.GONE);
        }

        try {
            Picasso.get().load(profileImage).placeholder(R.drawable.google).into(holder.ivpeople_hk);
        }catch (Exception e)
        {
            holder.ivpeople_hk.setImageResource(R.drawable.google);
        }
        //nhận vào để xem chi tiết kho
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
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
    }

    @Override
    public int getItemCount() {
        return userlist.size();
    }

    //view holder
    class HolderUser extends RecyclerView.ViewHolder{
        private ImageView ivpeople_hk, ivonline_status;
        private TextView tvten_hk, tvsodienthoai_hk;
        private RatingBar ratingbarchukho;
        private CardView cvchukhodc;
        private ImageButton btnxoataikhoan;

        public HolderUser(@NonNull View itemView) {
            super(itemView);
            cvchukhodc = itemView.findViewById(R.id.cvchukhodc);
            ivpeople_hk = itemView.findViewById(R.id.ivpeople_hk);
            ivonline_status = itemView.findViewById(R.id.ivonline_status);
            tvten_hk = itemView.findViewById(R.id.tvten_hk);
            tvsodienthoai_hk = itemView.findViewById(R.id.tvsodienthoai_hk);
            //xoa tai khoan
            btnxoataikhoan = itemView.findViewById(R.id.btnxoataikhoan);
        }
    }
}
