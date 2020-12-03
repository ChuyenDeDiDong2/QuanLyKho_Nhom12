package com.team12.quanlykhohang_nhom12.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.team12.quanlykhohang_nhom12.R;

import java.util.ArrayList;

public class Dangkyhopdong1Activity extends AppCompatActivity {

    ListView myListView;
    DatabaseReference myDatabaseReference;
    ArrayList<String> myArrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangkyhopdong1);

        ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<>(Dangkyhopdong1Activity.this,
                android.R.layout.simple_list_item_1, myArrayList);
        myListView = (ListView) findViewById(R.id.lvKho);
        myDatabaseReference = FirebaseDatabase.getInstance().getReference();
        //myDatabaseReference.addChildEventListener(new  )
    }
}