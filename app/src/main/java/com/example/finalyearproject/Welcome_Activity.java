package com.example.finalyearproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Welcome_Activity extends AppCompatActivity {

    //Contains signup and login buttons

    private Button alreadyAccount, signup;
    private TextView mmoreInfoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        alreadyAccount = (Button) findViewById(R.id.loginButton);
        signup = (Button) findViewById(R.id.registerButton);
        mmoreInfoButton = findViewById(R.id.moreInfoButton);

        mmoreInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Welcome_Activity.this, MoreInfo_Activity.class);
                startActivity(i);
            }
        });

        alreadyAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Welcome_Activity.this, Login_Activity.class);
                startActivity(i);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Welcome_Activity.this, Selection_Activity.class);
                startActivity(i);
            }
        });
    }
}