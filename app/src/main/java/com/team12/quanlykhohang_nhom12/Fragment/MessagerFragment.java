package com.team12.quanlykhohang_nhom12.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.team12.quanlykhohang_nhom12.Activity.DangNhapActivity;
import com.team12.quanlykhohang_nhom12.Adapter.ChatAdapter;
import com.team12.quanlykhohang_nhom12.Adapter.ChatListAdapter;
import com.team12.quanlykhohang_nhom12.Library.ModelChat;
import com.team12.quanlykhohang_nhom12.Library.ModelChatList;
import com.team12.quanlykhohang_nhom12.Library.ModelUser;
import com.team12.quanlykhohang_nhom12.R;

import java.util.ArrayList;
import java.util.List;

public class MessagerFragment extends Fragment {

    private FirebaseAuth firebaseAuth;
    RecyclerView recyclerViewchatlist;
    List<ModelChatList> chatLists;
    List<ModelUser> userList;
    DatabaseReference reference;
    FirebaseUser currentUser;
    ChatListAdapter chatListAdapter;

    public MessagerFragment(){

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.messager_fragment, container, false);
        firebaseAuth = FirebaseAuth.getInstance();
        recyclerViewchatlist = root.findViewById(R.id.recChatList);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        chatLists = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Chatlist").child(currentUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatLists.clear();
                for (DataSnapshot ds:snapshot.getChildren()){
                    ModelChatList modelChatList = ds.getValue(ModelChatList.class);
                    chatLists.add(modelChatList);
                }
                loadChatList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return root;
    }

    private void loadChatList() {
        userList = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Tb_Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
                for (DataSnapshot ds:snapshot.getChildren()){
                    ModelUser user = ds.getValue(ModelUser.class);
                    for (ModelChatList chatList:chatLists){
                        if (user.getUid() != null && user.getUid().equals(chatList.getId())){
                            userList.add(user);
                            break;
                        }
                    }
                    chatListAdapter = new ChatListAdapter(getContext(), userList);
                    recyclerViewchatlist.setAdapter(chatListAdapter);
                    for (int i = 0; i<userList.size();i++){
                        lassMessage(userList.get(i).getUid());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void lassMessage(String userId) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String theLastMessage = "default";
                for (DataSnapshot ds: snapshot.getChildren()){
                    ModelChat chat = ds.getValue(ModelChat.class);
                    if (chat==null){
                        continue;
                    }
                    String sender = chat.getSender();
                    String receiver = chat.getReceiver();
                    if (sender == null || receiver == null){
                        continue;
                    }
                    if (chat.getReceiver().equals(currentUser.getUid())
                            && chat.getSender().equals(userId) ||
                            chat.getReceiver().equals(userId) && chat.getSender().equals(currentUser.getUid())){
                                theLastMessage = chat.getMessage();
                    }
                }
                chatListAdapter.setLastMessageMap(userId, theLastMessage);
                chatListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    // Kiểm tra tài khoản đã tồn tại hay chưa?
    private void checkUser() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user == null){
            startActivity(new Intent(MessagerFragment.this.getActivity(), DangNhapActivity.class));
            getActivity().finish();
        }
        else
        {
            //loadMyinfo();
        }
    }
}