package com.example.finalyearproject.Mentor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.finalyearproject.Models.MenteeFriendList;
import com.example.finalyearproject.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MentorList extends AppCompatActivity {

    private RecyclerView menteeRecyclerView;
    private DatabaseReference MenteeRef,MentorRef;
    private FirebaseAuth firebaseAuth;
    private String menteeOnline;
    private ImageView mHome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_list);

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

        mHome = findViewById(R.id.home);

        mHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MentorList.this, MentorMainActivity.class);
                startActivity(i);
                finish();
            }
        });

    }


    private void DisplayAllMentees() {

        FirebaseRecyclerAdapter<MenteeFriendList, MenteeFriendHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<MenteeFriendList, MenteeFriendHolder>(
                MenteeFriendList.class,
                R.layout.all_mentees_displayed,
                MenteeFriendHolder.class,
                MenteeRef

        ) {
            @Override
            protected void populateViewHolder(MenteeFriendHolder menteeFriendHolder, MenteeFriendList menteeFriendList, int i) {

                //menteeFriendHolder.setDate(menteeFriendList.getDate());
                final String users = getRef(i).getKey();
                MentorRef.child(users).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if(snapshot.exists()){
                            final String name = snapshot.child("name").getValue().toString();
                            final String language = snapshot.child("language").getValue().toString();
                            final String location = snapshot.child("location").getValue().toString();
                            final String college = snapshot.child("college").getValue().toString();
                            final String course = snapshot.child("course").getValue().toString();
                            final String photo = snapshot.child("profileimage").getValue().toString();
                            Glide.with(getApplicationContext()).load(photo).into(menteeFriendHolder.setProfileimage(photo));

                            menteeFriendHolder.setCollege(college);
                            menteeFriendHolder.setName(name);
                            menteeFriendHolder.setLanguage(language);
                            menteeFriendHolder.setLocation(location);
                            menteeFriendHolder.setCourse(course);
                            menteeFriendHolder.setProfileimage(photo);

                            menteeFriendHolder.mView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {


                                                Intent intent = new Intent(MentorList.this, MentorFriends.class);
                                                intent.putExtra("menteeid", users);
                                                startActivity(intent);
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


        public void setName(String name) {

            TextView myName = (TextView) mView.findViewById(R.id.searchMenteeName);
            myName.setText(name);
        }

        public void setLocation(String location) {

            TextView myLocation = (TextView) mView.findViewById(R.id.searchMenteeLocation);
            myLocation.setText(location);
        }

        public void setLanguage(String language) {

            TextView myLanguage = (TextView) mView.findViewById(R.id.searchMenteeLanguage);
            myLanguage.setText(language);
        }



        public void setCollege(String college) {
            TextView myCollege = (TextView) mView.findViewById(R.id.searchMenteeCollege);
            myCollege.setText(college);
        }

        public void setCourse(String course) {
            TextView myCourse = (TextView) mView.findViewById(R.id.searchMenteeCourse);
            myCourse.setText(course);
        }


        public ImageView setProfileimage(String profileimage){

            ImageView myPhoto = (ImageView) mView.findViewById(R.id.searchMenteeProfilePicture);

            return myPhoto;
        }

    }


}