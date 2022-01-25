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
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MenteeList extends AppCompatActivity {

    private RecyclerView menteeRecyclerView;
    private DatabaseReference MenteeRef,MentorRef;
    private FirebaseAuth firebaseAuth;
    private String menteeOnline;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentee_list);

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

        FirebaseRecyclerAdapter<MentorFriendlist, MenteeFriendHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<MentorFriendlist, MenteeFriendHolder>(
                MentorFriendlist.class,
                R.layout.all_mentors_displayed,
                MenteeFriendHolder.class,
                MenteeRef

        ) {
            @Override
            protected void populateViewHolder(MenteeFriendHolder menteeFriendHolder, MentorFriendlist menteeFriendList, int i) {

                menteeFriendHolder.setDate(menteeFriendList.getDate());
                final String users = getRef(i).getKey();
                MentorRef.child(users).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if(snapshot.exists()){
                            final String name = snapshot.child("name").getValue().toString();
                            final String skills1 = snapshot.child("skill1").getValue().toString();
                            final String skills2 = snapshot.child("skill2").getValue().toString();
                            final String language = snapshot.child("language").getValue().toString();
                            final String location = snapshot.child("location").getValue().toString();
                            final String type = snapshot.child("type").getValue().toString();

                            menteeFriendHolder.setType(type);
                            menteeFriendHolder.setName(name);
                            menteeFriendHolder.setLanguage(language);
                            menteeFriendHolder.setLocation(location);
                            menteeFriendHolder.setSkills1(skills1);
                            menteeFriendHolder.setSkills2(skills2);

                            menteeFriendHolder.mView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    CharSequence options[] = new CharSequence[]{
                                            name + " Profile",

                                            "Send Message",

                                            "Request Meeting"
                                    };

                                    AlertDialog.Builder builder = new AlertDialog.Builder(MenteeList.this);
                                    builder.setTitle("Select an option");

                                    builder.setItems(options, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                            if(i == 0){

                                                Intent intent = new Intent(MenteeList.this, Person_Profile_Activity.class);
                                                intent.putExtra("mentorid", users);
                                                startActivity(intent);

                                            }
                                            if(i == 1){

                                                Intent intent = new Intent(MenteeList.this, Message_Mentor.class);
                                                intent.putExtra("mentorid", users);
                                                intent.putExtra("name", name);
                                                startActivity(intent);
                                            }

                                            if(i == 2){

                                                Intent intent = new Intent(MenteeList.this, MeetingRequest.class);
                                                intent.putExtra("mentorid", users);
                                                intent.putExtra("name", name);
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

        public void setLanguage(String language) {

            TextView myLanguage = (TextView) mView.findViewById(R.id.searchMentorLanguage);
            myLanguage.setText(language);
        }

        public void setSkills1(String skills1) {

            TextView mySkills1 = (TextView) mView.findViewById(R.id.searchMentorBio1);
            mySkills1.setText(skills1);
        }
        public void setSkills2(String skills2) {

            TextView mySkills2 = (TextView) mView.findViewById(R.id.searchMentorBio2);
            mySkills2.setText(skills2);
        }


        public void setDate(String date) {
            TextView myDate = (TextView) mView.findViewById(R.id.searchMentorStatus);
            myDate.setText("Mentorship since:" + date);
        }

    }


}