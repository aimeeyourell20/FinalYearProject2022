package com.example.finalyearproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.ImageDecoder;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreenActivity extends AppCompatActivity {

    private ImageView mimageView;
    private TextView mtextView1, mtextView2;

    Animation top_animation, bottom_animation;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        mimageView = findViewById(R.id.imageview);
        mtextView1 = findViewById(R.id.textView1);
        mtextView2 = findViewById(R.id.textView2);

        top_animation = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottom_animation = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        mimageView.setAnimation(top_animation);
        mtextView1.setAnimation(bottom_animation);
        mtextView2.setAnimation(bottom_animation);

        int SPLASH_SCREEN = 4300;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreenActivity.this, Welcome_Activity.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_SCREEN);

    }
}