package com.team12.quanlykhohang_nhom12.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.team12.quanlykhohang_nhom12.Library.ModelChat;
import com.team12.quanlykhohang_nhom12.R;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyHolder> {

    private static final int MSG_TYPE_LEFT = 0;
    private static final int MSG_TYPE_RIGHT = 1;
    Context context;
    List<ModelChat> chatList;
    String imageUrl;
    FirebaseUser fUser;

    public ChatAdapter(Context context, List<ModelChat> chatList, String imageUrl) {
        this.context = context;
        this.chatList = chatList;
        this.imageUrl = imageUrl;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType==MSG_TYPE_RIGHT){
            View view = LayoutInflater.from(context).inflate(R.layout.row_chat_right, parent, false);
            return new MyHolder(view);
        }
        else {

            View view = LayoutInflater.from(context).inflate(R.layout.row_chat_left, parent, false);
            return new MyHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, final int position) {
        String mesage = chatList.get(position).getMessage();
        String timeStamp = chatList.get(position).getTimestamp();
        //moc thoi gian
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(Long.parseLong(timeStamp));
        String dateTime  = DateFormat.format("dd/MM/yyyy hh:mm aa", cal).toString();
        //set data
        holder.messageTv.setText(mesage);
        holder.timeTv.setText(dateTime);
        try{
            Picasso.get().load(imageUrl).into(holder.profileiv);
        }catch (Exception e)
        {

        }
        holder.messageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xóa");
                builder.setMessage("Bạn có muốn xóa tin nhắn này?");
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteMesage(position);
                    }
                });
                builder.setNegativeButton("Quay lại", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.create().show();
            }
        });
        //set seen/delivered status ms
        if(position == chatList.size() - 1)
        {
            if (chatList.get(position).isSeen()){
                holder.isSeenTv.setText("Đã xem");
            }
            else {

                holder.isSeenTv.setText("Đã gửi");
            }
        }
        else {
            holder.isSeenTv.setVisibility(View.GONE);
        }
    }

    private void deleteMesage(int position) {
        String myUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String msgTimestamp = chatList.get(position).getTimestamp();
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Chats");
        Query query =  dbRef.orderByChild("timestamp").equalTo(msgTimestamp);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds:snapshot.getChildren()){
                    if (ds.child("sender").getValue().equals(myUID)){

                        ds.getRef().removeValue();
//                        HashMap<String, Object> hashMap = new HashMap<>();
//                        hashMap.put("message", "Tin nhắn đã được xóa...");
//                        ds.getRef().updateChildren(hashMap);
                        Toast.makeText(context,"Tin nhắn đã bị xóa...", Toast.LENGTH_SHORT).show();
                    }else {

                        Toast.makeText(context,"Bạn chỉ có thể xóa tin nhắn của mình...", Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }




    @Override
    public int getItemCount() {
        return chatList.size();
    }

    @Override
    public int getItemViewType(int position) {
        fUser = FirebaseAuth.getInstance().getCurrentUser();
        if (chatList.get(position).getSender().equals(fUser.getUid())){
            return MSG_TYPE_RIGHT;
        }else {
            return MSG_TYPE_LEFT;
        }
        //return super.getItemViewType(position);
    }

    class MyHolder extends RecyclerView.ViewHolder{

        //view
        ImageView profileiv;
        TextView messageTv, timeTv, isSeenTv;
        LinearLayout messageLayout;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            //init views
            profileiv = itemView.findViewById(R.id.profile_ivl);
            messageTv = itemView.findViewById(R.id.messagetvl);
            timeTv = itemView.findViewById(R.id.timeTv);
            isSeenTv = itemView.findViewById(R.id.isSeenTv);
            messageLayout = itemView.findViewById(R.id.messageLayout);
        }
    }
}
