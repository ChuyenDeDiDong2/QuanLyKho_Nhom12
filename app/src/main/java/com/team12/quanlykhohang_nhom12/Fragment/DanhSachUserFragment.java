package com.team12.quanlykhohang_nhom12.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.team12.quanlykhohang_nhom12.Adapter.UserAdapter;
import com.team12.quanlykhohang_nhom12.Library.ModelUser;
import com.team12.quanlykhohang_nhom12.R;

import java.util.ArrayList;

public class DanhSachUserFragment extends Fragment {
    private RecyclerView rechienthidanhsach;
    private UserAdapter userAdapter;
    private FirebaseAuth firebaseAuth;
    private ArrayList<ModelUser> danhsachlist;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.activity_danh_sach_user_fragment, container, false);
        rechienthidanhsach = root.findViewById(R.id.redanhsach_hangkho);
        rechienthidanhsach.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false));
        firebaseAuth = FirebaseAuth.getInstance();
        loadchukho();
        return root;
    }
    // Thực hiện load thông tin kho nổi bật:
    private void loadchukho() {
        danhsachlist = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tb_Users");
        reference.orderByChild("accountType").equalTo("user")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        danhsachlist.clear();
                        for (DataSnapshot ds: snapshot.getChildren()){
                            ModelUser modelUser = ds.getValue(ModelUser.class);
                            danhsachlist.add(modelUser);
                        }

                        userAdapter = new UserAdapter(DanhSachUserFragment.this.getActivity(), danhsachlist);
                        rechienthidanhsach.setAdapter(userAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }
}