package com.team12.quanlykhohang_nhom12.Adapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.team12.quanlykhohang_nhom12.Activity.DangKyTaoHopDongActivity;
import com.team12.quanlykhohang_nhom12.Library.ModelDangKyThue;
import com.team12.quanlykhohang_nhom12.R;

import java.util.ArrayList;
import java.util.HashMap;

public class DanhSachDangKyThueAdapter extends RecyclerView.Adapter<DanhSachDangKyThueAdapter.HolderChuKho> {
    private Context context;
    public ArrayList<ModelDangKyThue> list;

    public DanhSachDangKyThueAdapter(Context context, ArrayList<ModelDangKyThue> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public HolderChuKho onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //ìnfale layout row item show
        View view = LayoutInflater.from(context).inflate(R.layout.row_item_dangkythue, parent, false);
        return new HolderChuKho(view);
    }

    @Override
    //, , , , , , , , , ,
    public void onBindViewHolder(@NonNull HolderChuKho holder, int position) {
        ModelDangKyThue modelDangKyThue = list.get(position);
        String dientichthue = modelDangKyThue.getDientichthue();
        String hisUid = modelDangKyThue.getHisUid();
        String khohangId = modelDangKyThue.getKhohangId();
        String sodienthoai = modelDangKyThue.getSodienthoai();
        String tenkho = modelDangKyThue.getTenkho();
        String diachi = modelDangKyThue.getDiachi();
        String email = modelDangKyThue.getEmail();
        String tenthue = modelDangKyThue.getTenthue();
        String timstamp = modelDangKyThue.getTimstamp();
        String tongtien = modelDangKyThue.getTongtien();
        String uid = modelDangKyThue.getUid();
        String thoigianthue = modelDangKyThue.getThoigianthue();
        String thongbaothue = modelDangKyThue.getThongbaothue();
        //setdate
        holder.cvchukhodc.setAnimation(AnimationUtils.loadAnimation(context, R.anim.scale_list));
        holder.tvtenkhothue.setText(tenkho);
        holder.tvtennguoithue.setText(tenthue);


        loadKhoinfo();
        loaddieukhoan();
        holder.btnduyetchothue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Duyệt")
                        .setMessage("Bạn có chắc muốn duyệt?")
                        .setPositiveButton("Duyệt", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                                String thongbaothue = firebaseAuth.getUid() + "false";
                                ProgressDialog progressDialog = new ProgressDialog(context);
                                HashMap<String, Object> hashMap = new HashMap<>();

                                hashMap.put("thongbaothue", "" + thongbaothue);

                                //cap nhat db
                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tb_HopDong");
                                reference.child(timstamp)
                                        .updateChildren(hashMap)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                //update
                                                progressDialog.dismiss();
                                                Toast.makeText(context, "Đã phê duyệt!", Toast.LENGTH_SHORT).show();
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
                                addHopDong(modelDangKyThue);


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


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//
            }
        });

    }

    //load thong tin chu cho
    String tendoanhnghiep, diachibena, dienthoaibena, emailbena, giayphepso, masothue;

    private void loadKhoinfo() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tb_Users");
        reference.child(firebaseAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds : snapshot.getChildren()) {

                    tendoanhnghiep = "" + snapshot.child("tentaikhoan").getValue();
                    diachibena = "" + snapshot.child("diachi").getValue();
                    dienthoaibena = "" + snapshot.child("phone").getValue();
                    emailbena = "" + snapshot.child("email").getValue();
                    giayphepso = "" + snapshot.child("giayphepso").getValue();
                    masothue = "" + snapshot.child("masothue").getValue();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //load thong tin dieu khoan
    String noidungdieukhoan;

    private void loaddieukhoan() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("DieuKhoan");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                noidungdieukhoan = "" + snapshot.child("DieuKhoanApp").getValue();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void addHopDong(ModelDangKyThue modelDangKyThue) {
        String dientichthue = modelDangKyThue.getDientichthue();
        String hisUid = modelDangKyThue.getHisUid();
        String khohangId = modelDangKyThue.getKhohangId();
        String sodienthoai = modelDangKyThue.getSodienthoai();
        String tenkho = modelDangKyThue.getTenkho();
        String tenthue = modelDangKyThue.getTenthue();
        String diachi = modelDangKyThue.getDiachi();
        String email = modelDangKyThue.getEmail();
        String timstamp = modelDangKyThue.getTimstamp();
        String tongtien = modelDangKyThue.getTongtien();
        String uid = modelDangKyThue.getUid();
        String thoigianthue = modelDangKyThue.getThoigianthue();
        String thongbaothue = modelDangKyThue.getThongbaothue();
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Đang thực hiện tạo hợp đồng");
        progressDialog.show();
        String timestamp = "" + System.currentTimeMillis();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("khohangId", "" + khohangId);
        hashMap.put("tenthue", "" + tenthue);
        hashMap.put("tenkho", "" + tenkho);
        hashMap.put("diachibenb", "" + diachi);
        hashMap.put("emailbenb", "" + email);
        hashMap.put("sodienthoaibenb", "" + sodienthoai);
        hashMap.put("tongtien", "" + tongtien);
        hashMap.put("dientichthue", "" + dientichthue);
        hashMap.put("thoigianthue", "" + thoigianthue);
        hashMap.put("thongbaothue", hisUid + "true");
        hashMap.put("timstamp", "" + timestamp);//
        hashMap.put("uid", "" + uid);//
        hashMap.put("hisUid", "" + hisUid);//
        //ben a ,, , ,
        hashMap.put("tendoanhnghiep", "" + tendoanhnghiep);
        hashMap.put("diachibena", "" + diachibena);
        hashMap.put("dienthoaibena", "" + dienthoaibena);
        hashMap.put("emailbena", "" + emailbena);
        hashMap.put("giayphepso", "" + giayphepso);
        hashMap.put("masothue", "" + masothue);
        //noi dung hop dong
        hashMap.put("noidungdieukhoan", "" + noidungdieukhoan);
        //add to db
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tb_HopDongChinhThuc");
        reference.child(timestamp).setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //
                        progressDialog.dismiss();
                        Toast.makeText(context, " Hoàn thành...", Toast.LENGTH_SHORT).show();
                        //clearData();
                    }
                })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //
                        progressDialog.dismiss();
                        Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    //view holder
    class HolderChuKho extends RecyclerView.ViewHolder {
        private TextView tvtenkhothue, tvtennguoithue;
        private CardView cvchukhodc;
        private ImageButton btnduyetchothue;

        public HolderChuKho(@NonNull View itemView) {
            super(itemView);

            cvchukhodc = itemView.findViewById(R.id.cvchukhodc);
            tvtennguoithue = itemView.findViewById(R.id.tvtennguoithue);
            tvtenkhothue = itemView.findViewById(R.id.tvtenkhothue);
            //
            btnduyetchothue = itemView.findViewById(R.id.btnduyetchothue);


        }
    }
}
