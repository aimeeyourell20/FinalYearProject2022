package com.example.finalyearproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Welcome_Activity extends AppCompatActivity {

    //Contains signup and login buttons

    private Button mAlreadyAccount, mSignup;
    private TextView mMoreInfoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mAlreadyAccount =    findViewById(R.id.loginButton);
        mSignup =            findViewById(R.id.registerButton);

        mAlreadyAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Welcome_Activity.this, Login_Activity.class);
                startActivity(i);
            }
        });

        mSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Welcome_Activity.this, Selection_Activity.class);
                startActivity(i);
            }
        });
    }
}