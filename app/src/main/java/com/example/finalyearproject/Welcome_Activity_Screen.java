package com.example.finalyearproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Welcome_Activity_Screen extends AppCompatActivity {

    //Contains signup and login buttons

    private Button mentor, mentee;
    private TextView mmoreInfoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        mentor = (Button) findViewById(R.id.loginButton);
        mentee = (Button) findViewById(R.id.registerButton);
        mmoreInfoButton = findViewById(R.id.moreInfoButton);

        mmoreInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Welcome_Activity_Screen.this, MoreInfo_Activity.class);
                startActivity(i);
            }
        });

        mentor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Welcome_Activity_Screen.this, Mentor_Login_Activity.class);
                startActivity(i);
            }
        });

        mentee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Welcome_Activity_Screen.this, Mentee_Login_Activity.class);
                startActivity(i);
            }
        });
    }
}