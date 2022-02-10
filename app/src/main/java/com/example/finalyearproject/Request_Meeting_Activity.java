package com.example.finalyearproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Request_Meeting_Activity extends AppCompatActivity {

    private RecyclerView menteeRecyclerView;
    private DatabaseReference MenteeRef,MentorRef;
    private FirebaseAuth firebaseAuth;
    private String menteeOnline;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_meeting);

        firebaseAuth = FirebaseAuth.getInstance();
        menteeOnline = firebaseAuth.getCurrentUser().getUid();
        MenteeRef = FirebaseDatabase.getInstance().getReference().child("Mentorship").child(menteeOnline);
        MentorRef = FirebaseDatabase.getInstance().getReference().child("users");

        menteeRecyclerView = findViewById(R.id.menteeRecyclerView);
        menteeRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        menteeRecyclerView.setLayoutManager(linearLayoutManager);

        DisplayAllMentees();

    }

    private void DisplayAllMentees() {

        FirebaseRecyclerAdapter<MentorFriendlist, MenteeList.MenteeFriendHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<MentorFriendlist, MenteeList.MenteeFriendHolder>(
                MentorFriendlist.class,
                R.layout.all_mentors_displayed,
                MenteeList.MenteeFriendHolder.class,
                MenteeRef

        ) {
            @Override
            protected void populateViewHolder(MenteeList.MenteeFriendHolder menteeFriendHolder, MentorFriendlist menteeFriendList, int i) {

                menteeFriendHolder.setDate(menteeFriendList.getDate());
                final String users = getRef(i).getKey();
                MentorRef.child(users).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if(snapshot.exists()){
                            final String name = snapshot.child("name").getValue().toString();
                            final String skills1 = snapshot.child("skill1").getValue().toString();
                            final String company = snapshot.child("company").getValue().toString();
                            final String industry = snapshot.child("industry").getValue().toString();
                            final String location = snapshot.child("location").getValue().toString();
                            final String type = snapshot.child("type").getValue().toString();

                            menteeFriendHolder.setType(type);
                            menteeFriendHolder.setName(name);
                            menteeFriendHolder.setCompany(company);
                            menteeFriendHolder.setLocation(location);
                            menteeFriendHolder.setSkills1(skills1);
                            menteeFriendHolder.setIndustry(industry);

                            menteeFriendHolder.mView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    CharSequence options[] = new CharSequence[]{
                                            name + " Profile"
                                    };

                                    AlertDialog.Builder builder = new AlertDialog.Builder(Request_Meeting_Activity.this);
                                    builder.setTitle("Select an option");

                                    builder.setItems(options, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                            if(i == 0){

                                                Intent intent = new Intent(Request_Meeting_Activity.this, MenteeRequestMentor.class);
                                                intent.putExtra("mentorid", users);
                                                startActivity(intent);

                                            }

                                        }
                                    });

                                    builder.show();
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        };

        menteeRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    public static class MenteeFriendHolder extends RecyclerView.ViewHolder {

        View mView;

        public MenteeFriendHolder(@NonNull View itemView) {
            super(itemView);

            mView = itemView;
        }


        public void setType(String type) {

            TextView myType = (TextView) mView.findViewById(R.id.searchMentorType);
            myType.setText(type);
        }

        public void setName(String name) {

            TextView myName = (TextView) mView.findViewById(R.id.searchMentorName);
            myName.setText(name);
        }

        public void setLocation(String location) {

            TextView myLocation = (TextView) mView.findViewById(R.id.searchMentorLocation);
            myLocation.setText(location);
        }

        public void setIndustry(String industry) {

            TextView myIndustry = (TextView) mView.findViewById(R.id.searchMentorIndustry);
            myIndustry.setText(industry);
        }

        public void setSkills1(String skills1) {

            TextView mySkills1 = (TextView) mView.findViewById(R.id.searchMentorBio1);
            mySkills1.setText(skills1);
        }
        public void setCompany(String company) {

            TextView myCompany = (TextView) mView.findViewById(R.id.searchMentorCompany);
            myCompany.setText(company);
        }


        public void setDate(String date) {
            TextView myDate = (TextView) mView.findViewById(R.id.searchMentorStatus);
            myDate.setText("Mentorship since:" + date);
        }

    }


}