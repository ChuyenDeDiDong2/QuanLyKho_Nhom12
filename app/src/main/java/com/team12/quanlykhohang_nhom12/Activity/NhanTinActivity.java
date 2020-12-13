package com.team12.quanlykhohang_nhom12.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
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
import androidx.recyclerview.widget.LinearLayoutManager;
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
import com.team12.quanlykhohang_nhom12.Adapter.ChatAdapter;
import com.team12.quanlykhohang_nhom12.Library.ModelChat;
import com.team12.quanlykhohang_nhom12.Library.ModelUser;
import com.team12.quanlykhohang_nhom12.Notifications.APIService;
import com.team12.quanlykhohang_nhom12.Notifications.Client;
import com.team12.quanlykhohang_nhom12.Notifications.Data;
import com.team12.quanlykhohang_nhom12.Notifications.Response;
import com.team12.quanlykhohang_nhom12.Notifications.Sender;
import com.team12.quanlykhohang_nhom12.Notifications.Token;
import com.team12.quanlykhohang_nhom12.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;

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
    //
    ValueEventListener seenListener;
    DatabaseReference userRefForSeen;

    List<ModelChat> chatList;
    ChatAdapter chatAdapter;

    String hisUid;
    String myUid;
    String hisImage;

    APIService apiService;
    boolean notify = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhan_tin);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setControl();
        setEvent();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        //re
        recyclerViewMes.setHasFixedSize(true);
        recyclerViewMes.setLayoutManager(layoutManager);
        apiService = Client.getRetrofit("https://fcm.googleapis.com/").create(APIService.class);
    }

    private void setEvent() {
        bntSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notify = true;
                String message = messageEt.getText().toString().trim();
                if (TextUtils.isEmpty(message)){
                    Toast.makeText(NhanTinActivity.this, "Không thể gửi tin nhắn...", Toast.LENGTH_SHORT).show();
                }else {
                    sendMessage(message);
                }
                messageEt.setText("");
            }
        });
        readMessages();
        seenMessage();
    }

    private void seenMessage() {
        userRefForSeen = FirebaseDatabase.getInstance().getReference("Chats");
        seenListener = userRefForSeen.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds:snapshot.getChildren()){
                    ModelChat chat = ds.getValue(ModelChat.class);
                    if (chat.getReceiver().equals(myUid) && chat.getSender().equals(hisUid)){
                        HashMap<String, Object> hasSeenHashMap = new HashMap<>();
                        hasSeenHashMap.put("isSeen",true);
                        ds.getRef().updateChildren(hasSeenHashMap);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void readMessages() {
        chatList = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatList.clear();
                for (DataSnapshot ds: snapshot.getChildren())
                {
                    ModelChat chat = ds.getValue(ModelChat.class);
                    if (chat.getReceiver().equals(myUid) && chat.getSender().equals(hisUid)||
                            chat.getReceiver().equals(hisUid) && chat.getSender().equals(myUid)){
                        chatList.add(chat);
                    }
                    //adapter
                    chatAdapter = new ChatAdapter(NhanTinActivity.this, chatList, hisImage);
                    chatAdapter.notifyDataSetChanged();
                    //set
                    recyclerViewMes.setAdapter(chatAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void sendMessage(String message) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        String timestamp =String.valueOf(System.currentTimeMillis());

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", myUid);
        hashMap.put("receiver", hisUid);
        hashMap.put("message", message);
        hashMap.put("timestamp", timestamp);
        hashMap.put("isSeen", false);
        reference.child("Chats").push().setValue(hashMap);
        ///
       // messageEt.setText("");

        String msg = message;
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Tb_Users").child(myUid);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ModelUser user  = snapshot.getValue(ModelUser.class);
                if (notify){
                    sendNotification(hisUid, user.getName(), message);
                }
                notify =false;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //create listchat
        DatabaseReference chatRef1  = FirebaseDatabase.getInstance().getReference("Chatlist")
                .child(myUid)
                .child(hisUid);
        chatRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()){
                    chatRef1.child("id").setValue(hisUid);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference chatRef2  = FirebaseDatabase.getInstance().getReference("Chatlist")
                .child(hisUid)
                .child(myUid);
        chatRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()){
                    chatRef2.child("id").setValue(myUid);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void sendNotification(String hisUid, String name, String message) {
        DatabaseReference allTokens = FirebaseDatabase.getInstance().getReference("Tokens");
        Query query  = allTokens.orderByKey().equalTo(hisUid);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds:snapshot.getChildren()){
                    Token token  = ds.getValue(Token.class);
                    Data data = new Data(myUid, name+":"+message, "Tin nhắn mới", hisUid, R.drawable.ic_people_24);
                    Sender sender = new Sender(data, token.getToken());
                    apiService.sendNotification(sender)
                            .enqueue(new Callback<Response>() {
                                @Override
                                public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                                    Toast.makeText(NhanTinActivity.this, ""+response.message(), Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onFailure(Call<Response> call, Throwable t) {

                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
    private void checkOnlineStatus(String status){
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Tb_Users").child(myUid);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("online", status);
        //
        dbRef.updateChildren(hashMap);
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
                    hisImage = ""+ds.child("profileImage").getValue();

                    String onlinestatus = ""+ds.child("online").getValue();
                    if (onlinestatus.equals("true")){

                        userStatusTv.setText(onlinestatus);
                    }else {
                        //moc thoi gian
                        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
                        cal.setTimeInMillis(Long.parseLong(onlinestatus));
                        String dateTime  = DateFormat.format("dd/MM/yyyy hh:mm aa", cal).toString();
                        userStatusTv.setText("Online lúc"+dateTime);
                    }

                    nameTv.setText(name);
                    try {
                        Picasso.get().load(hisImage).placeholder(R.drawable.ic_people_24).into(profile_iv);
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
        //
        checkOnlineStatus("true");
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        String timestamp = String.valueOf(System.currentTimeMillis());
        checkOnlineStatus(timestamp);
        userRefForSeen.removeEventListener(seenListener);
    }

    @Override
    protected void onResume() {
        checkOnlineStatus("true");
        super.onResume();
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