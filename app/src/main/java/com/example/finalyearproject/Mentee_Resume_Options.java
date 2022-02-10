package com.example.finalyearproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Mentee_Resume_Options extends AppCompatActivity {

    private Button create, view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentee_resume_options);

        create = findViewById(R.id.createResume);
        view = findViewById(R.id.viewResume);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Mentee_Resume_Options.this, Mentee_Add_Resume.class);
                startActivity(i);
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Mentee_Resume_Options.this, Mentee_View_Resumes.class);
                startActivity(i);
            }
        });


    }
}