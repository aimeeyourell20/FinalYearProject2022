package com.example.finalyearproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RecommendedMentors extends AppCompatActivity {

    private AdapterClass adapterClass;
    private List<FindMentor> userList;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommended_mentors);

        recyclerView = findViewById(R.id.recommendedRecyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        userList = new ArrayList<>();
        adapterClass = new AdapterClass(RecommendedMentors.this, userList);
        recyclerView.setAdapter(adapterClass);

        recommendedUsers();
    }

    private void recommendedUsers() {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String result;
                String type = snapshot.child("type").getValue().toString();

                if(type.equals("Mentor")){
                    result = "Mentee";
                }else{
                    result = "Mentor";
                }

                String skills = snapshot.child("skill1").getValue().toString();

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users");

                Query query = reference.orderByChild("search").equalTo(result+skills);

                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        userList.clear();

                        for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                            FindMentor findMentor = dataSnapshot.getValue(FindMentor.class);
                            userList.add(findMentor);
                        }

                        adapterClass.notifyDataSetChanged();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}