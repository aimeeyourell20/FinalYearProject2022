package com.example.finalyearproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.finalyearproject.Industry_Fragments.Aerospace_Fragment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class DisplayMentees extends AppCompatActivity {
    private static final String tag1 = "qqq";

    DatabaseReference databaseReference;
    private String x;
    private RecyclerView menteeRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_mentees);

        menteeRecyclerView = findViewById(R.id.menteeRecyclerView1);
        menteeRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        menteeRecyclerView.setLayoutManager(linearLayoutManager);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");

        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();

            if (extras != null) {
                x = (String) extras.get("0.0");

                x = (String) extras.get("4");
                x = (String) extras.get("5");
                if(("0.0").equals(x)){
                    Log.d(tag1, "x" + x);
                   Aerospace();
//                }else if(x.equals("4")) {
//                    Construction();
//                }
//                else if(x.equals("5")){
//                    Education();
               }



            }

        }
//Aerospace();



    }

    private void Aerospace() {
        Query searchPeopleAndFriendsQuery = databaseReference.orderByChild("industry3");
        FirebaseRecyclerAdapter<FindMentor, DisplayMentees.FindMentorViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<FindMentor, DisplayMentees.FindMentorViewHolder>(
                        FindMentor.class,
                        R.layout.all_mentors_displayed,
                        DisplayMentees.FindMentorViewHolder.class,
                        searchPeopleAndFriendsQuery

                ) {
                    @Override
                    protected void populateViewHolder(DisplayMentees.FindMentorViewHolder findMentorViewHolder, FindMentor findMentor, int i) {
                        //if (("0.0").equals(x)) {
                            if (!findMentor.getIndustry().equalsIgnoreCase("Aerospace")) {
                                findMentorViewHolder.itemView.setVisibility(View.GONE);
                                findMentorViewHolder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
                            }
                            findMentorViewHolder.setType(findMentor.getType());
                            findMentorViewHolder.setName(findMentor.getName());
                            findMentorViewHolder.setCompany(findMentor.getCompany());
                            findMentorViewHolder.setLocation(findMentor.getLocation());
                            findMentorViewHolder.setSkills1(findMentor.getSkill1());
                            findMentorViewHolder.setIndustry(findMentor.getIndustry());


                            findMentorViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    String mentorid = getRef(i).getKey();

                                    Intent i = new Intent(DisplayMentees.this, Person_Profile_Activity.class);
                                    i.putExtra("mentorid", mentorid);
                                    startActivity(i);

                                }
                            });
                        //}
                    }
                };

        menteeRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    private void Education() {
        Query searchPeopleAndFriendsQuery = databaseReference.orderByChild("industry3");
        FirebaseRecyclerAdapter<FindMentor, DisplayMentees.FindMentorViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<FindMentor, DisplayMentees.FindMentorViewHolder>(
                        FindMentor.class,
                        R.layout.all_mentors_displayed,
                        DisplayMentees.FindMentorViewHolder.class,
                        searchPeopleAndFriendsQuery

                ) {
                    @Override
                    protected void populateViewHolder(DisplayMentees.FindMentorViewHolder findMentorViewHolder, FindMentor findMentor, int i) {

                        if(!findMentor.getIndustry().equalsIgnoreCase("Education")) {
                            findMentorViewHolder.itemView.setVisibility(View.GONE);
                            findMentorViewHolder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
                        }

                        findMentorViewHolder.setType(findMentor.getType());
                        findMentorViewHolder.setName(findMentor.getName());
                        findMentorViewHolder.setCompany(findMentor.getCompany());
                        findMentorViewHolder.setLocation(findMentor.getLocation());
                        findMentorViewHolder.setSkills1(findMentor.getSkill1());
                        findMentorViewHolder.setIndustry(findMentor.getIndustry());


                        findMentorViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String mentorid = getRef(i).getKey();

                                Intent i = new Intent(DisplayMentees.this, Person_Profile_Activity.class);
                                i.putExtra("mentorid", mentorid);
                                startActivity(i);

                            }
                        });

                    }
                };

        menteeRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    private void Construction() {
        Query searchPeopleAndFriendsQuery = databaseReference.orderByChild("industry3");
        FirebaseRecyclerAdapter<FindMentor, DisplayMentees.FindMentorViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<FindMentor, DisplayMentees.FindMentorViewHolder>(
                        FindMentor.class,
                        R.layout.all_mentors_displayed,
                        DisplayMentees.FindMentorViewHolder.class,
                        searchPeopleAndFriendsQuery

                ) {
                    @Override
                    protected void populateViewHolder(DisplayMentees.FindMentorViewHolder findMentorViewHolder, FindMentor findMentor, int i) {

                        if(!findMentor.getIndustry().equalsIgnoreCase("Construction")) {
                            findMentorViewHolder.itemView.setVisibility(View.GONE);
                            findMentorViewHolder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
                        }

                        findMentorViewHolder.setType(findMentor.getType());
                        findMentorViewHolder.setName(findMentor.getName());
                        findMentorViewHolder.setCompany(findMentor.getCompany());
                        findMentorViewHolder.setLocation(findMentor.getLocation());
                        findMentorViewHolder.setSkills1(findMentor.getSkill1());
                        findMentorViewHolder.setIndustry(findMentor.getIndustry());


                        findMentorViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String mentorid = getRef(i).getKey();

                                Intent i = new Intent(DisplayMentees.this, Person_Profile_Activity.class);
                                i.putExtra("mentorid", mentorid);
                                startActivity(i);

                            }
                        });

                    }
                };

        menteeRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }


    public static class FindMentorViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public FindMentorViewHolder(View itemView) {
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
    }
}