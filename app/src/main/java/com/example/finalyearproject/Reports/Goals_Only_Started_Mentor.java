package com.example.finalyearproject.Reports;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.finalyearproject.Adapters.Goals_Adapter_Mentor;
import com.example.finalyearproject.Mentor.Goals_Activity_Mentor;
import com.example.finalyearproject.Mentor.MentorMainActivity;
import com.example.finalyearproject.Models.Goals_Model;
import com.example.finalyearproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Goals_Only_Started_Mentor extends AppCompatActivity {
    private DatabaseReference RootRef;
    private String menteeID = "";
    private RecyclerView menteeRecyclerView;
    private final ArrayList<Goals_Model> goals_models  = new ArrayList<>();
    private Goals_Adapter_Mentor goals_adapterMentee;
    private LinearLayoutManager linearLayoutManager;
    private ImageView mHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals_mentor);

        RootRef = FirebaseDatabase.getInstance().getReference();

        goals_adapterMentee = new Goals_Adapter_Mentor(goals_models);
        menteeRecyclerView = (RecyclerView) findViewById(R.id.goalsRecyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        menteeRecyclerView.setLayoutManager(linearLayoutManager);
        menteeRecyclerView.setAdapter(goals_adapterMentee);

        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();

            if (extras != null) {
                menteeID = (String) extras.get("menteeid");
            }
        }

        FetchMessages();

        mHome = findViewById(R.id.home);

        mHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Goals_Only_Started_Mentor.this, MentorMainActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    private void FetchMessages() {

        RootRef.child("Goals").child(FirebaseAuth.getInstance().getUid()).child(menteeID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                for(DataSnapshot uniqueKeySnapshot : snapshot.getChildren()){
                    for(DataSnapshot a : uniqueKeySnapshot.getChildren()) {
                        String status = a.child("status").getValue().toString();
                        if(status.equals("Only started")){
                            Goals_Model g = a.getValue(Goals_Model.class);
                            goals_models.add(g);
                        }

                    }

                }

                goals_adapterMentee = new Goals_Adapter_Mentor(Goals_Only_Started_Mentor.this, goals_models);
                menteeRecyclerView.setAdapter(goals_adapterMentee);
                goals_adapterMentee.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(Goals_Only_Started_Mentor.this, "Error", Toast.LENGTH_SHORT).show();

            }

        });

    }
}