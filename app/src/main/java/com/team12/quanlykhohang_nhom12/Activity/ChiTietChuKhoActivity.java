package com.team12.quanlykhohang_nhom12.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.team12.quanlykhohang_nhom12.Adapter.KhoUserAdapter;
import com.team12.quanlykhohang_nhom12.Library.ModelChuKho;
import com.team12.quanlykhohang_nhom12.Library.ModelKhoHang;
import com.team12.quanlykhohang_nhom12.R;

import java.util.ArrayList;

public class ChiTietChuKhoActivity extends AppCompatActivity {
    private ImageView chuhkhoiv;
    private TextView tenchukhotv, sodienthoaitv, emailchukhotv, mocuatv, delivereFeetv, diachichukhotv, tvkhohang_filter;
    private ImageButton btncall, btnmessenger, btnmap, btnFilterKhoHang;
    private RecyclerView recdanhsach_kho;
    private EditText txttim_kiem_chukho;
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
       loadKhoHang();

       
    }
    private void loadMyinfo() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tb_Users");
        reference.orderByChild("uid").equalTo(firebaseAuth.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds: snapshot.getChildren()){
                            String name =""+ds.child("name").getValue();
                            String email =""+ds.child("email").getValue();
                            String phone =""+ds.child("phone").getValue();
                            String profileImage =""+ds.child("profileImage").getValue();
                            String accountType =""+ds.child("accountType").getValue();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
    private void loadChuKhoDetails() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tb_Users");
        reference.child("chukhoUid").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = ""+snapshot.child("name").getValue();
                tentaikhoan = ""+snapshot.child("tentaikhoan").getValue();
                email = ""+snapshot.child("email").getValue();
                phone = ""+snapshot.child("phone").getValue();
                chukhodiachi = ""+snapshot.child("diachi").getValue();
                //String deliveryfee= ""+snapshot.child("deliveryfee");
                String profileImage= ""+snapshot.child("profileImage");
                String open = ""+snapshot.child("open");

                //setdata
                tenchukhotv.setText(tentaikhoan);
                emailchukhotv.setText(email);
                //delivereFeetv.setText("Delivery: $"+deliveryfee);
                diachichukhotv.setText(chukhodiachi);
                sodienthoaitv.setText(phone);
                if (snapshot.equals("true")){
                    mocuatv.setText("Open");
                }else {
                    mocuatv.setText("Closed");
                }
                try {
                    Picasso.get().load(profileImage).into(chuhkhoiv);
                }
                catch (Exception e){

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void loadKhoHang() {
        khohangList = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tb_Users");
        reference.child("chukhoUid").child("KhoHang")
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
        //init ui view
        chuhkhoiv = findViewById(R.id.chukhoiv_ct);
        tenchukhotv = findViewById(R.id.tenchukho_ct);
        sodienthoaitv = findViewById(R.id.phonechukho_ct);
        emailchukhotv = findViewById(R.id.emailchukho_ct);
        mocuatv = findViewById(R.id.mocuatv_ct);
        //delivereFeetv = findViewById(R.id.delivereFeetv);
        diachichukhotv = findViewById(R.id.diachichukhotv_ct);
        btncall = findViewById(R.id.goidienbtn);
        btnmessenger = findViewById(R.id.nhantinbtn);
        btnmap = findViewById(R.id.mapbtn);
        txttim_kiem_chukho = findViewById(R.id.txttim_kiem_chukho);
        btnFilterKhoHang = findViewById(R.id.btnFilterKhoHang);
        recdanhsach_kho = findViewById(R.id.redanhsach_khohang);
        tvkhohang_filter = findViewById(R.id.tvkhohang_filter);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Vui lòng đợi");
        progressDialog.setCanceledOnTouchOutside(false);
    }

    private void setControl() {

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