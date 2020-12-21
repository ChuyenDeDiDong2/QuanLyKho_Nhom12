package com.team12.quanlykhohang_nhom12;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.team12.quanlykhohang_nhom12.Activity.DangNhapActivity;

public class MainActivity extends AppCompatActivity implements Animation.AnimationListener {
    private static int SPLASH_TIME = 5000;
    ImageView imageView;
    TextView textView;
    //animation
    Animation sideAnim, bottomAnim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        //Intent intent = new Intent(MainActivity.this, DangNhapActivity.class);
        //startActivity(intent);
        imageView = findViewById(R.id.imagebackg);
        textView = findViewById(R.id.by_line);
        // load the animation
        sideAnim = AnimationUtils.loadAnimation(this, R.anim.side_anim);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_anim);
        //set Animations on elements
        imageView.setAnimation(sideAnim);
        textView.setAnimation(bottomAnim);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), DangNhapActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME);

    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        Toast.makeText(getApplicationContext(), "Animation Stopped", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}