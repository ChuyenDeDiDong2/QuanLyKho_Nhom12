package com.team12.quanlykhohang_nhom12.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.team12.quanlykhohang_nhom12.Activity.ChiTietChuKhoActivity;
import com.team12.quanlykhohang_nhom12.Activity.NhanTinActivity;
import com.team12.quanlykhohang_nhom12.Library.ModelUser;
import com.team12.quanlykhohang_nhom12.R;

import java.util.HashMap;
import java.util.List;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.MyHolder>{
    Context context;
    List<ModelUser> userList;
    private HashMap<String , String > lastMessageMap;

    public ChatListAdapter(Context context, List<ModelUser> userList) {
        this.context = context;
        this.userList = userList;
        lastMessageMap = new HashMap<>();
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_chat_list, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        String hisUid = userList.get(position).getUid();
        String profileImage = userList.get(position).getProfileImage();
        String name = userList.get(position).getName();
        String lastMessage = lastMessageMap.get(hisUid);
        //set data
        holder.tvten_chat.setText(name);
        if (lastMessage==null || lastMessage.equals("default")){
            holder.tvthe_last_message.setVisibility(View.GONE);
        }else {
            holder.tvthe_last_message.setVisibility(View.VISIBLE);
            holder.tvthe_last_message.setText(lastMessage);
        }
        try{
            Picasso.get().load(profileImage).placeholder(R.drawable.ic_people_24).into(holder.ivpeople_chat);
        }catch (Exception e){
            Picasso.get().load(R.drawable.ic_people_24).into(holder.ivpeople_chat);
        }
        //set online
        if (userList.get(position).getOnline().equals("true"))
        {
            holder.ivonline_status.setImageResource(R.drawable.kho_online);
        }else {

            holder.ivonline_status.setImageResource(R.drawable.kho_offline);
        }
        //
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, NhanTinActivity.class);
                intent.putExtra("hisUid", hisUid);
                context.startActivity(intent);
            }
        });

    }
    public void setLastMessageMap(String userId, String lastMessage){
        lastMessageMap.put(userId, lastMessage);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{
        ImageView ivpeople_chat, ivonline_status;
        TextView  tvten_chat, tvthe_last_message;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            ivpeople_chat =itemView.findViewById(R.id.ivpeople_chat);
            ivonline_status =itemView.findViewById(R.id.ivonline_status);
            tvten_chat =itemView.findViewById(R.id.tvten_chat);
            tvthe_last_message =itemView.findViewById(R.id.tvthe_last_message);
        }
    }
}
