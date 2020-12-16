package com.team12.quanlykhohang_nhom12.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.team12.quanlykhohang_nhom12.R;

public class DanhGiaActivity extends AppCompatActivity {

    TextView rateCount, txtXepHang;
    EditText edtNhanXet;
    Button btnXacNhan;
    RatingBar ratingBar;
    float rateValue;
    String temp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_gia);
        rateCount = findViewById(R.id.rateCount);
        ratingBar = findViewById(R.id.ratingBar);
        edtNhanXet = findViewById(R.id.edtNhanXet);
        btnXacNhan = findViewById(R.id.btnXacNhan);
        txtXepHang = findViewById(R.id.txtXepHang);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                rateValue = ratingBar.getRating();
                if (rateValue <= 1 && rateValue > 0){
                    rateCount.setText("Bad " + rateValue + "/5");
                }
                else  if (rateValue <= 2 && rateValue > 1){
                    rateCount.setText("Ok " + rateValue + "/5");
                }
                else  if (rateValue <= 3 && rateValue > 2){
                    rateCount.setText("Good " + rateValue + "/5");
                }
                else  if (rateValue <= 4 && rateValue > 3){
                    rateCount.setText("Very Good " + rateValue + "/5");
                }
                else  if (rateValue <= 5 && rateValue > 4){
                    rateCount.setText("Best " + rateValue + "/5");
                }
            }
        });
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                txtXepHang.setText("Your rating: \n" + temp + "\n" + edtNhanXet.getText());
                edtNhanXet.setText("");
                ratingBar.setRating(0);
                rateCount.setText("");
            }
        });
    }
}