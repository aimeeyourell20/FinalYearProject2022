package com.example.finalyearproject.Mentees;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.widget.SearchView;
import android.widget.Toast;

import com.example.finalyearproject.Adapters.Goals_Adapter;
import com.example.finalyearproject.Models.Goals_Model;
import com.example.finalyearproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class Goals_Activity_Mentee extends AppCompatActivity {
    private DatabaseReference RootRef;
    private String menteeID = "";
    private FirebaseAuth mAuth;
    private RecyclerView menteeRecyclerView;
    private final ArrayList<Goals_Model> goals_models  = new ArrayList<>();
    private Goals_Adapter goals_adapter;
    private LinearLayoutManager linearLayoutManager;
    private ImageView mHome;
    private SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals_mentee);

        mAuth = FirebaseAuth.getInstance();
        RootRef = FirebaseDatabase.getInstance().getReference();

        goals_adapter = new Goals_Adapter(goals_models);
        menteeRecyclerView = (RecyclerView) findViewById(R.id.goalsRecyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        menteeRecyclerView.setLayoutManager(linearLayoutManager);
        menteeRecyclerView.setAdapter(goals_adapter);
        mSearchView = findViewById(R.id.search_box_input);



        //Gets mentees id
        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();

            if (extras != null) {
                menteeID = (String) extras.get("menteeid");
            }
        }

        mHome = findViewById(R.id.home);
        mHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Goals_Activity_Mentee.this, MenteeMainActivity.class);
                startActivity(i);
                finish();
            }
        });

        FetchGoals();
    }


    private void FetchGoals() {
        if(RootRef != null) {
            RootRef.child("Goals").child(FirebaseAuth.getInstance().getUid()).child(menteeID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    //Loops through to retrieve goals
                    for (DataSnapshot uniqueKeySnapshot : snapshot.getChildren()) {
                        for (DataSnapshot a : uniqueKeySnapshot.getChildren()) {
                            Goals_Model g = a.getValue(Goals_Model.class);
                            goals_models.add(g);
                        }
                    }

                    goals_adapter = new Goals_Adapter(Goals_Activity_Mentee.this, goals_models);
                    menteeRecyclerView.setAdapter(goals_adapter);
                    goals_adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(Goals_Activity_Mentee.this, "Error", Toast.LENGTH_SHORT).show();
                }

            });
        }
        if(mSearchView != null){
            mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    search(newText);
                    return false;
                }
            });
        }
    }

    private void search(String newText) {

        ArrayList<Goals_Model> goals_modelsFull = new ArrayList<>();

        for(Goals_Model g : goals_models){
            if(g.getStatus().toLowerCase().contains(newText.toLowerCase()) || g.getGoalsMentor().toLowerCase().contains(newText.toLowerCase())){
                goals_modelsFull.add(g);
            }
        }
        goals_adapter = new Goals_Adapter(goals_modelsFull);
        menteeRecyclerView.setAdapter(goals_adapter);
    }
}