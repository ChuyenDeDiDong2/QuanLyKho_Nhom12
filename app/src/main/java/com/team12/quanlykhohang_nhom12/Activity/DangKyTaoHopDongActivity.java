package com.team12.quanlykhohang_nhom12.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.team12.quanlykhohang_nhom12.Library.Constants_kho;
import com.team12.quanlykhohang_nhom12.Library.ModelHangHoa;
import com.team12.quanlykhohang_nhom12.R;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class
DangKyTaoHopDongActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView tvtenuser, tvdientichdathue,tvdientichkho, tvsotienuoctinh,spchonnammuonthue, tvsodienthoaiuser, tvtenkhodangky, tvgiakhothuemotthang, ngaydangkyhopdong, spChonthangmuonthue;
    TextView tvnoidungdieukhoan, tvemailuser, tvdiachiuser;
    String hisUid;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private String khohangId;
    private Button btndangkythue;
    private EditText txtchondientichthue;
    private Switch swdocdieukhoan;
    String timestamp = ""+System.currentTimeMillis();
    private Locale localeVN = new Locale("vi", "VN");
    private NumberFormat vn = NumberFormat.getInstance(localeVN);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangky_tao_hop_dong);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Đăng ký thuê kho");

        setControl();// anh xa
        tinhtongtien();
        setEvent();//tat ca cac su kien
        loadThueinfo();
        loadKhoinfo();
        loaddieukhoan();

    }

    private void setEvent() {
        //su kien chon thang muon thue
        spChonthangmuonthue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                chonthangDialog();
            }
        });
        //tắt mở điều khoản thuê
        tvnoidungdieukhoan.setVisibility(View.GONE);
        swdocdieukhoan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    tvnoidungdieukhoan.setVisibility(View.VISIBLE);
                }else {
                    tvnoidungdieukhoan.setVisibility(View.GONE);
                }
            }
        });
        //su kien chon thang muon thue
        spchonnammuonthue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                chonNamDialog();
            }
        });
        btndangkythue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                addHopDong();
                CapNhatDienTichDathue();
                progressDialog.dismiss();
            }
        });
    }
    //chon thang muon thue
    private void chonthangDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chọn tháng muốn thuê")
                .setItems(Constants_kho.options4, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String tinhtrang = Constants_kho.options4[i];
                        spChonthangmuonthue.setText(tinhtrang);
                        tinhtongtien();
                    }
                })

                .show();
    }
    //chon thang muon thue
    private void chonNamDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chọn năm muốn thuê")
                .setItems(Constants_kho.options5, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String tinhtrang = Constants_kho.options5[i];
                        spchonnammuonthue.setText(tinhtrang);
                        tinhtongtien();
                    }
                })

                .show();
    }
    //tinh tong tien
    private int thoigianthue;
    private int tamp = 0;
    private void tinhtongtien(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tb_Users");
        reference.child(hisUid).child("KhoHang").child(khohangId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    String dientichkho = ""+snapshot.child("dientichkho").getValue();
                    String giachothue = ""+snapshot.child("giachothue").getValue();
                    String giamoi = ""+snapshot.child("giamoi").getValue();

                    String thangthue = spChonthangmuonthue.getText().toString().trim();
                    String namthue = spchonnammuonthue.getText().toString().trim();
                    thoigianthue = Integer.parseInt(thangthue) + (Integer.parseInt(namthue)*12);
                    String dientichthue = txtchondientichthue.getText().toString().trim();
                    if (giamoi.equals("0")){
                        tamp = (((Integer.parseInt(thangthue) * Integer.parseInt(giachothue)) + (Integer.parseInt(giachothue) * Integer.parseInt(namthue) * 12))/(Integer.parseInt(dientichkho))) * (Integer.parseInt(dientichthue));
                        tvsotienuoctinh.setText(vn.format(tamp)+"");
                    }else {
                        tamp = (((Integer.parseInt(thangthue) * Integer.parseInt(giamoi)) + (Integer.parseInt(giamoi) * Integer.parseInt(namthue) * 12))/(Integer.parseInt(dientichkho))) * (Integer.parseInt(dientichthue)) ;
                        tvsotienuoctinh.setText(vn.format(tamp)+"");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    private String dientichthuethem, dientichdathue, tiendathue;
    private void CapNhatDienTichDathue(){
        progressDialog.setMessage("Cập nhật diện tích đã thuê");
        progressDialog.show();
        dientichthuethem = txtchondientichthue.getText().toString().trim();
        dientichdathue = tvdientichdathue.getText().toString().trim();

        if(Integer.parseInt(dientichthuethem) > (Integer.parseInt(dientichkho) - Integer.parseInt(dientichdathue))){
            Toast.makeText(this, "Diện tích bạn muốn thuê vượt quá diện tích kho còn lại...", Toast.LENGTH_SHORT).show();
            return;
        }
        int dientichdathuetam =  0;
        int tongthunhap =  0;
        tongthunhap = Integer.parseInt(tiendathue) + tamp;

        dientichdathuetam = Integer.parseInt(dientichdathue ) + Integer.parseInt(dientichthuethem);
        HashMap<String, Object> hashMap = new HashMap<>();
        //
        hashMap.put("dientichdathue", ""+ dientichdathuetam);
        hashMap.put("tongthunhap", ""+ tongthunhap);


        //cap nhat db
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tb_Users");
        reference.child(hisUid).child("KhoHang").child(khohangId)
                .updateChildren(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //update
                        progressDialog.dismiss();
                        Toast.makeText(DangKyTaoHopDongActivity.this, "",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //update failed
                        progressDialog.dismiss();
                        Toast.makeText(DangKyTaoHopDongActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void setControl() {
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Vui lòng đợi");
        progressDialog.setCanceledOnTouchOutside(false);
        tvtenuser = findViewById(R.id.tvtenuser);
        tvsodienthoaiuser = findViewById(R.id.tvsodienthoaiuser);
        tvtenkhodangky = findViewById(R.id.tvtenkhodk);
        tvgiakhothuemotthang = findViewById(R.id.tvgiakhothuemotthang);
        ngaydangkyhopdong = findViewById(R.id.ngaydangkyhopdong);
        spChonthangmuonthue = findViewById(R.id.spChonthangmuonthue);
        tvsotienuoctinh = findViewById(R.id.tvsotienuoctinh);
        tvdientichdathue = findViewById(R.id.tvdientichdathue);
        spchonnammuonthue = findViewById(R.id.spchonnammuonthue);
        txtchondientichthue = findViewById(R.id.txtchondientichthue);
        tvdientichkho = findViewById(R.id.tvdientichkho);
        btndangkythue = findViewById(R.id.btndangkythue);
        swdocdieukhoan = findViewById(R.id.swdocdieukhoan);
        tvdiachiuser = findViewById(R.id.tvdiachiuser);
        tvemailuser = findViewById(R.id.tvemailuser);
        tvnoidungdieukhoan = findViewById(R.id.tvnoidungdieukhoan);
        hisUid = getIntent().getStringExtra("hisUid");
        khohangId = getIntent().getStringExtra("khohangId");

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
    private String tenThue, soDT, dientichkho, diachi, email;
    private void loadThueinfo(){//moc thoi gian

        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(Long.parseLong(timestamp));
        String dateTime  = DateFormat.format("dd/MM/yyyy", cal).toString();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tb_Users");
        reference.child(firebaseAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    tenThue = ""+snapshot.child("name").getValue();
                    soDT = ""+snapshot.child("phone").getValue();
                    diachi = ""+snapshot.child("diachi").getValue();
                    soDT = ""+snapshot.child("phone").getValue();
                    email = ""+snapshot.child("email").getValue();

                    tvtenuser.setText(tenThue);
                    tvdiachiuser.setText(diachi);
                    tvemailuser.setText(email);
                    tvsodienthoaiuser.setText(soDT);

                    ngaydangkyhopdong.setText(dateTime);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private String tenkho;
    private void loadKhoinfo(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tb_Users");
        reference.child(hisUid).child("KhoHang").child(khohangId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds: snapshot.getChildren()){

                    tenkho = ""+snapshot.child("tenkho").getValue();
                    dientichdathue = ""+snapshot.child("dientichdathue").getValue();
                    tiendathue = ""+snapshot.child("tongthunhap").getValue();
                    dientichkho = ""+snapshot.child("dientichkho").getValue();
                    String giachothue = ""+snapshot.child("giachothue").getValue();
                    String giamoi = ""+snapshot.child("giamoi").getValue();

                    tvtenkhodangky.setText(tenkho);
                    tvdientichdathue.setText(dientichdathue);
                    tvdientichkho.setText(dientichkho);
                    if (giamoi.equals("0")){
                        tvgiakhothuemotthang.setText(vn.format(Integer.parseInt(giachothue)) +" vnd");
                    }else {

                        tvgiakhothuemotthang.setText(vn.format(Integer.parseInt(giamoi)) +" vnd");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    String dieukhoanapp;
    private void loaddieukhoan(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("DieuKhoan");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dieukhoanapp = ""+snapshot.child("DieuKhoanApp").getValue();
                tvnoidungdieukhoan.setText(dieukhoanapp);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void addHopDong() {
        progressDialog.setMessage("Đang thực hiện đăng kí hợp đồng");
        progressDialog.show();
        String timestamp = ""+System.currentTimeMillis();

        //String tongtienthu = tvsotienuoctinh.getText().toString().trim();
        String dientichthue = txtchondientichthue.getText().toString().trim();
        if(Integer.parseInt(dientichthue) > (Integer.parseInt(dientichkho) - Integer.parseInt(dientichdathue))){
            Toast.makeText(this, "Diện tích bạn muốn thuê vượt quá diện tích kho còn lại...", Toast.LENGTH_SHORT).show();
            return;
        }
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("khohangId", ""+khohangId);
        hashMap.put("tenthue", ""+ tenThue);
        hashMap.put("tenkho", ""+tenkho);
        hashMap.put("diachi", ""+diachi);
        hashMap.put("email", ""+email);
        hashMap.put("sodienthoai", ""+soDT);
        hashMap.put("tongtien", ""+tamp);
        hashMap.put("dientichthue", ""+dientichthue);
        hashMap.put("thoigianthue", ""+thoigianthue);
        hashMap.put("thongbaothue", hisUid+"true");
        hashMap.put("timstamp", ""+timestamp);//
        hashMap.put("uid", ""+firebaseAuth.getUid());//
        hashMap.put("hisUid", ""+hisUid);//
        //add to db
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tb_HopDong");
        reference.child(timestamp).setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //
                        progressDialog.dismiss();
                        Toast.makeText(DangKyTaoHopDongActivity.this, "Đăng ký thành công...", Toast.LENGTH_SHORT).show();
                        //clearData();
                    }
                })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //
                        progressDialog.dismiss();
                        Toast.makeText(DangKyTaoHopDongActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }


}