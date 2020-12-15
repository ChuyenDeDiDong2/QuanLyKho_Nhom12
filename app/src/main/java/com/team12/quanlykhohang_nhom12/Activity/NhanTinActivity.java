package com.team12.quanlykhohang_nhom12.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.team12.quanlykhohang_nhom12.R;

import java.util.HashMap;

public class NhanTinActivity extends AppCompatActivity {
    Toolbar toolbar;
    private RecyclerView recyclerViewMes;
    private ImageView profile_iv;
    private TextView nameTv, userStatusTv;
    private ImageButton bntSend;
    private EditText messageEt;
    FirebaseAuth firebaseAuth;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference userreference;

    String hisUid;
    String myUid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhan_tin);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setControl();
        setEvent();
    }

    private void setEvent() {
        bntSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = messageEt.getText().toString().trim();
                if (TextUtils.isEmpty(message)){
                    Toast.makeText(NhanTinActivity.this, "Không thể gửi tin nhắn...", Toast.LENGTH_SHORT).show();
                }else {
                    sendMessage(message);
                }
            }
        });
    }

    private void sendMessage(String message) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", myUid);
        hashMap.put("receiver", hisUid);
        hashMap.put("message", message);
        reference.child("Chats").push().setValue(hashMap);
        ///
        messageEt.setText("");
    }

    private void checkUserStatus(){
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null){
            myUid = user.getUid();
        }else {
            startActivity(new Intent(this, HomeToRentActivity.class));
            finish();
        }
    }
    private void setControl() {

        recyclerViewMes = findViewById(R.id.chat_recyclerview);
        profile_iv = findViewById(R.id.profile_iv);
        nameTv = findViewById(R.id.name_tv);
        userStatusTv = findViewById(R.id.status_tv);
        messageEt = findViewById(R.id.messageEt);
        bntSend = findViewById(R.id.btnSend);
        firebaseAuth =FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();


        Intent intent =  getIntent();
        hisUid = intent.getStringExtra("hisUid");
        userreference = firebaseDatabase.getReference("Tb_Users");

        Query userquery = userreference.orderByChild("uid").equalTo(hisUid);
        userquery. addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    String name = ""+ds.child("name").getValue();
                    String image = ""+ds.child("profileImage").getValue();

                    nameTv.setText(name);
                    try {
                        Picasso.get().load(image).placeholder(R.drawable.ic_people_24).into(profile_iv);
                    }
                    catch (Exception e){
                        Picasso.get().load(R.drawable.ic_people_24).into(profile_iv);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Nhắn tin");

    }

    @Override
    protected void onStart() {
        checkUserStatus();
        super.onStart();
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