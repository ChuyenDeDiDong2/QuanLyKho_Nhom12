package com.team12.quanlykhohang_nhom12.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.team12.quanlykhohang_nhom12.Adapter.KhoUserAdapter;
import com.team12.quanlykhohang_nhom12.Library.Constants_kho;
import com.team12.quanlykhohang_nhom12.Library.ModelDangKyThue;
import com.team12.quanlykhohang_nhom12.Library.ModelKhoHang;
import com.team12.quanlykhohang_nhom12.R;

import java.util.ArrayList;
import java.util.HashMap;

public class ChiTietChuKhoActivity extends AppCompatActivity {
    private ImageView chuhkhoiv;
    private TextView tenchukhotv, sodienthoaitv, emailchukhotv, tocao, diachichukhotv, tvkhohang_filter, tvDanhGia;
    private ImageButton btncall, btnmessenger, btnmap, btnFilterKhoHang;
    private RecyclerView recdanhsach_kho;
    private EditText txttim_kiem_chukho,edt_dialog_nd_to_cao;

    Toolbar toolbar;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private ArrayList<ModelKhoHang> khohangList;
    private KhoUserAdapter khoUserAdapter;




    private String chukhoId;
    private String myLatitude, myLongitude;
    private String tentaikhoan, email,phone,  chukhodiachi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_chu_kho);
        //toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setControl();
       setEvent();

        chukhoId = getIntent().getStringExtra("chukhoId");
       loadMyinfo();
       loadChuKhoDetails();
       loadKhoHang();//init ui view


       
    }

    String name, myUid;

    private void loadMyinfo() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tb_Users");
        reference.orderByChild("uid").equalTo(firebaseAuth.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds: snapshot.getChildren()){
                             name =""+ds.child("name").getValue();
                            String email =""+ds.child("email").getValue();
                            String phone =""+ds.child("phone").getValue();
                            String profileImage =""+ds.child("profileImage").getValue();
                            String accountType =""+ds.child("accountType").getValue();
                            myUid  =""+ds.child("uid").getValue();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
    //load thông tin chi tiết hãng kho
    private void loadChuKhoDetails() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tb_Users");
        reference.child(chukhoId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = ""+snapshot.child("name").getValue();
                tentaikhoan = ""+snapshot.child("tentaikhoan").getValue();
                email = ""+snapshot.child("email").getValue();
                phone = ""+snapshot.child("phone").getValue();
                chukhodiachi = ""+snapshot.child("diachi").getValue();
                String profileImage= ""+snapshot.child("profileImage");
                String open = ""+snapshot.child("open");

                //setdata
                tenchukhotv.setText(tentaikhoan);
                emailchukhotv.setText(email);
                diachichukhotv.setText(chukhodiachi);
                sodienthoaitv.setText(phone);
                try {
                    Picasso.get().load(profileImage).placeholder(R.drawable.ic_people_24).into(chuhkhoiv);
                }
                catch (Exception e){
                    //Picasso.get().load(chuhkhoiv).placeholder(R.drawable.google).into(holder.imgkho_hang);
                    chuhkhoiv.setImageResource(R.drawable.google);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    //load kho theo hãng kho
    private void loadKhoHang() {
        khohangList = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tb_Users");
        reference.child(chukhoId).child("KhoHang")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        khohangList.clear();
                        for (DataSnapshot ds:snapshot.getChildren()){
                            ModelKhoHang modelKhoHang =ds.getValue(ModelKhoHang.class);
                            khohangList.add(modelKhoHang);
                        }
                        //
                        khoUserAdapter = new KhoUserAdapter(ChiTietChuKhoActivity.this, khohangList);
                        recdanhsach_kho.setAdapter(khoUserAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }



    private void setEvent() {
        //tim kiếm kho hàng của hãng kho
        txttim_kiem_chukho.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                try {

                    khoUserAdapter.getFilter().filter(s);
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //lọc sản phẩn theo loại kho
        btnFilterKhoHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ChiTietChuKhoActivity.this);
                builder.setTitle("Chọn danh mục kho")
                        .setItems(Constants_kho.options2, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String selected = Constants_kho.options2[i];
                                tvkhohang_filter.setText(selected);
                                if(selected.equals("Tất cả kho")){
                                    loadKhoHang();
                                }
                                else {
                                    khoUserAdapter.getFilter().filter(selected);
                                }
                            }
                        })
                        .show();
            }
        });

        btncall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goidien();
            }
        });
        btnmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMap();
            }
        });
        btnmessenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChiTietChuKhoActivity.this, NhanTinActivity.class);
                intent.putExtra("hisUid", chukhoId);
                startActivity(intent);
            }
        });
        tvDanhGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChiTietChuKhoActivity.this, DanhGia1Activity.class);
                intent.putExtra("hisUid", chukhoId);
                startActivity(intent);
            }
        });

        tocao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogtocao();
            }
        });
    }

    //gửi tố cáo
    String noidung;
    private void dialogtocao(){
        AlertDialog.Builder builderadd = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_tocao, null);
        builderadd.setView(view);
        Button btn_dialog_gui_to_cao = view.findViewById(R.id.btn_dialog_gui_to_cao);

        EditText edt_dialog_nd_to_cao = view.findViewById(R.id.edt_dialog_nd_to_cao);
        btn_dialog_gui_to_cao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog progressDialog = new ProgressDialog(ChiTietChuKhoActivity.this);
                progressDialog.setMessage("Đang thực hiện gửi tố cáo");
                progressDialog.show();
                String timestamp = ""+System.currentTimeMillis();
                noidung= edt_dialog_nd_to_cao.getText().toString().trim();
                if(TextUtils.isEmpty(noidung)){
                    Toast.makeText(ChiTietChuKhoActivity.this, "Vui lòng nhập nội dung...", Toast.LENGTH_SHORT).show();
                    return;
                }
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("noidungtocao", "" + noidung);
                hashMap.put("tennguoigui", "" + name);
                hashMap.put("hangkho", "" + tentaikhoan);
                hashMap.put("timestamp", ""+ timestamp);
                hashMap.put("hisUid", "" + chukhoId);
                hashMap.put("myUid", "" + myUid);
                hashMap.put("uid", ""+ timestamp);
                hashMap.put("tocaochuadoc", "true");


                //add to db
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tb_ToCao");
                reference.child(timestamp).setValue(hashMap)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                //
                                progressDialog.dismiss();
                                Toast.makeText(ChiTietChuKhoActivity.this, " Gửi thành công...", Toast.LENGTH_SHORT).show();
                                //clearData();
                            }
                        })

                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                //
                                progressDialog.dismiss();
                                Toast.makeText(ChiTietChuKhoActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });
        builderadd.show();
    }


    private void openMap() {
        String diachi  ="https://maps.google.com/maps?saddr="+diachichukhotv+"&daddr";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(diachi));
        startActivity(intent);
    }

    private void goidien() {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+Uri.encode(phone))));
        Toast.makeText(this, ""+phone, Toast.LENGTH_SHORT).show();
    }

    private void setControl() {
        //init ui view
        chuhkhoiv = findViewById(R.id.chukhoiv_ct);
        tenchukhotv = findViewById(R.id.tenchukho_ct);
        sodienthoaitv = findViewById(R.id.phonechukho_ct);
        emailchukhotv = findViewById(R.id.emailchukho_ct);
        tocao = findViewById(R.id.tocao);
        diachichukhotv = findViewById(R.id.diachichukhotv_ct);
        btncall = findViewById(R.id.goidienbtn);
        btnmessenger = findViewById(R.id.nhantinbtn);
        btnmap = findViewById(R.id.mapbtn);
        txttim_kiem_chukho = findViewById(R.id.txttim_kiem_chukho);
        btnFilterKhoHang = findViewById(R.id.btnFilterKhoHang);
        recdanhsach_kho = findViewById(R.id.redanhsach_khohang);
        tvkhohang_filter = findViewById(R.id.tvkhohanguser_filter);
        tvDanhGia = findViewById(R.id.tvDanhGia);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Vui lòng đợi");
        progressDialog.setCanceledOnTouchOutside(false);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Chi tiết chủ kho");

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:  // Sự kiện nút back
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}