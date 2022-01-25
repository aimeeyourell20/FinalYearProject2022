package com.example.finalyearproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;

public class Rating_Activity extends AppCompatActivity {

    private Toolbar mmentorRatingToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        mmentorRatingToolbar = findViewById(R.id.mentorRatingToolbar);
        setSupportActionBar(mmentorRatingToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Mentor Rating");

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int i = item.getItemId();

        if(i  == android.R.id.home){
            Home();
        }
        return super.onOptionsItemSelected(item);
    }

    private void Home() {

        Intent i = new Intent(Rating_Activity.this, MenteeMainActivity.class);
        startActivity(i);
    }
}