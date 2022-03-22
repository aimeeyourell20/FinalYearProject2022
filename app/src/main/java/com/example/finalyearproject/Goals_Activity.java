package com.example.finalyearproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Goals_Activity extends AppCompatActivity {

    private String messageReceiverID1 = "";
    private DatabaseReference RootRef;
    private RecyclerView SearchResultList;
    private String messageSenderID;
    private FirebaseAuth mAuth;
    private String title;
    private String description;
    private final ArrayList<Goals_Model> goals_models  = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals2);

        RootRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        messageSenderID = mAuth.getCurrentUser().getUid();
        String id = RootRef.getKey();


        SearchResultList = (RecyclerView) findViewById(R.id.goals);
        SearchResultList.setHasFixedSize(true);
        SearchResultList.setLayoutManager(new LinearLayoutManager(this));


        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();

            if (extras != null) {
                messageReceiverID1 = (String) extras.get("menteeid");
            }
        }


        RootRef.child("Goals").child(FirebaseAuth.getInstance().getUid()).child(messageReceiverID1);


    }

    protected void onStart() {

        super.onStart();
        Query searchPeopleAndFriendsQuery = RootRef;
        FirebaseRecyclerAdapter<Goals_Model,GoalsViewHolder > adapter = new FirebaseRecyclerAdapter<Goals_Model, GoalsViewHolder>(
                Goals_Model.class,
                R.layout.goals_details,
                GoalsViewHolder.class,
                searchPeopleAndFriendsQuery
        ) {
            @Override
            protected void populateViewHolder(GoalsViewHolder goalsViewHolder, Goals_Model goals_model, int i) {
                goalsViewHolder.setGoalsTitle(goals_model.getGoalsTitle());
                goalsViewHolder.setGoalsDescription(goals_model.getGoalsDescription());

                goalsViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String mentorid = getRef(i).getKey();

                        title = goals_model.getGoalsTitle();
                        description = goals_model.getGoalsDescription();

                        updateTask();
                    }
                });


            }
        };

        SearchResultList.setAdapter(adapter);
    }

    private void updateTask() {

        Toast.makeText(Goals_Activity.this, "Success", Toast.LENGTH_SHORT).show();
    }

    public static class GoalsViewHolder extends RecyclerView.ViewHolder{


        View mView;

        public GoalsViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

        }

        public void setGoalsTitle(String goalsTitle){
            TextView myTitle = (TextView) mView.findViewById(R.id.goalTitle);
            myTitle.setText(goalsTitle);
        }

        public void setGoalsDescription(String goalsDescription) {
            TextView myDescription = (TextView) mView.findViewById(R.id.goalDescription);
            myDescription.setText(goalsDescription);
        }
    }
}