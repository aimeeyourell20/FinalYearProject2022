package com.example.finalyearproject.Mentees;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.finalyearproject.Mentor.Mentor_Profile_Details_Activity;
import com.example.finalyearproject.Models.MentorFriendlist;
import com.example.finalyearproject.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MenteeList extends AppCompatActivity {

    private RecyclerView mMenteeRecyclerView;
    private DatabaseReference MentorshipRef,RootRef;
    private FirebaseAuth firebaseAuth;
    private String MenteeOnline;
    private ImageView mHome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentee_list);

        firebaseAuth = FirebaseAuth.getInstance();
        MenteeOnline = firebaseAuth.getCurrentUser().getUid();
        //Goes into mentorship child and finds the current users unique id
        MentorshipRef = FirebaseDatabase.getInstance().getReference().child("Mentorship").child(MenteeOnline);
        //Goes into users child
        RootRef = FirebaseDatabase.getInstance().getReference().child("users");


        mMenteeRecyclerView = findViewById(R.id.menteeRecyclerView);
        mMenteeRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        mMenteeRecyclerView.setLayoutManager(linearLayoutManager);

        mHome = findViewById(R.id.home);

        mHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MenteeList.this, MenteeMainActivity.class);
                startActivity(i);
                finish();
            }
        });

        DisplayAllMentors();

    }

    //Displays all mentors
    private void DisplayAllMentors() {

        FirebaseRecyclerAdapter<MentorFriendlist, MenteeFriendHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<MentorFriendlist, MenteeFriendHolder>(
                MentorFriendlist.class,
                R.layout.all_mentors_displayed,
                MenteeFriendHolder.class,
                MentorshipRef

        ) {
            @Override
            protected void populateViewHolder(MenteeFriendHolder menteeFriendHolder, MentorFriendlist menteeFriendList, int i) {

                //Sets the mentorship date
                menteeFriendHolder.setDate(menteeFriendList.getDate());
                final String users = getRef(i).getKey();
                RootRef.child(users).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        //Gets the mentors details
                        if(snapshot.exists()){
                            final String name = snapshot.child("name").getValue().toString();
                            final String skills1 = snapshot.child("skill1").getValue().toString();
                            final String industry = snapshot.child("industry").getValue().toString();
                            final String company = snapshot.child("company").getValue().toString();
                            final String location = snapshot.child("location").getValue().toString();
                            final String type = snapshot.child("type").getValue().toString();
                            final String photo = snapshot.child("profileimage").getValue().toString();
                            Glide.with(getApplicationContext()).load(photo).into(menteeFriendHolder.setProfileimage(photo));

                            //Sets mentors details
                            menteeFriendHolder.setType(type);
                            menteeFriendHolder.setName(name);
                            menteeFriendHolder.setCompany(company);
                            menteeFriendHolder.setLocation(location);
                            menteeFriendHolder.setSkills1(skills1);
                            menteeFriendHolder.setIndustry(industry);
                            menteeFriendHolder.setProfileimage(photo);

                            //If mentor is clicked alert dialog pops up
                            menteeFriendHolder.mView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    CharSequence options[] = new CharSequence[]{
                                            name + " Profile",
                                            
                                            "Rate Mentor"
                                    };

                                    AlertDialog.Builder builder = new AlertDialog.Builder(MenteeList.this);
                                    builder.setTitle("Select an option");

                                    builder.setItems(options, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                            if(i == 0){

                                                Intent intent = new Intent(MenteeList.this, Mentor_Profile_Details_Activity.class);
                                                intent.putExtra("mentorid", users);
                                                startActivity(intent);


                                            }

                                            if(i == 1){

                                                Intent intent = new Intent(MenteeList.this, Rating_Activity.class);
                                                intent.putExtra("mentorid", users);
                                                intent.putExtra("menteeid", MenteeOnline);
                                                startActivity(intent);
                                                finish();
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

        mMenteeRecyclerView.setAdapter(firebaseRecyclerAdapter);
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

        public ImageView setProfileimage(String profileimage){

            ImageView myPhoto = (ImageView) mView.findViewById(R.id.searchMentorProfilePicture);

            return myPhoto;
        }

    }


}