package com.example.finalyearproject.Mentees;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.finalyearproject.Adapters.Resume_Adapter;
import com.example.finalyearproject.Models.Resume_Model;
import com.example.finalyearproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Mentee_View_Resumes extends AppCompatActivity {
    private DatabaseReference RootRef;
    private String messageSenderID;
    private FirebaseAuth mAuth;
    private RecyclerView resumeRecyclerView;
    private final ArrayList<Resume_Model> resume_models  = new ArrayList<>();
    private Resume_Adapter resume_adapter;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentee_view_resumes);

        mAuth = FirebaseAuth.getInstance();
        messageSenderID = mAuth.getCurrentUser().getUid();

        RootRef = FirebaseDatabase.getInstance().getReference("CV").child(messageSenderID);

        resume_adapter = new Resume_Adapter(resume_models);
        resumeRecyclerView = (RecyclerView) findViewById(R.id.resumeRecyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        resumeRecyclerView.setLayoutManager(linearLayoutManager);
        resumeRecyclerView.setAdapter(resume_adapter);

        FetchResumes();
    }

    private void FetchResumes() {

        RootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot uniqueKeySnapshot : snapshot.getChildren()){
                        Resume_Model g = uniqueKeySnapshot.getValue(Resume_Model.class);
                        resume_models.add(g);


                }

                resume_adapter = new Resume_Adapter(Mentee_View_Resumes.this, resume_models);
                resumeRecyclerView.setAdapter(resume_adapter);
                resume_adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(Mentee_View_Resumes.this, "Error", Toast.LENGTH_SHORT).show();

            }

        });

    }




}