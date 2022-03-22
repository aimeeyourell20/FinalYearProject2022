package com.example.finalyearproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Mentee_Request_Activity extends AppCompatActivity {

    private Toolbar mtoolbar;
    private RecyclerView SearchResultList;
    private DatabaseReference allUsersDatabaseRef;
    private DatabaseReference MenteeRef,MentorRef;
    private ImageButton SearchButton;
    private EditText SearchInputText;
    private FirebaseAuth mAuth;
    private String senderUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentee_request);

        mAuth = FirebaseAuth.getInstance();
        senderUserId = mAuth.getCurrentUser().getUid();

        allUsersDatabaseRef = FirebaseDatabase.getInstance().getReference().child("users");
        MenteeRef = FirebaseDatabase.getInstance().getReference().child("MentorshipRequests").child(senderUserId);



        SearchResultList = (RecyclerView) findViewById(R.id.search_result_list);
        SearchResultList.setHasFixedSize(true);
        SearchResultList.setLayoutManager(new LinearLayoutManager(this));

        SearchPeopleAndFriends();
    }

    private void SearchPeopleAndFriends() {

        Query searchPeopleAndFriendsQuery = MenteeRef;
        FirebaseRecyclerAdapter<Mentee, Mentee_Request_Activity.FindMentorViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Mentee, Mentee_Request_Activity.FindMentorViewHolder>(
                        Mentee.class,
                        R.layout.all_mentees_displayed,
                        Mentee_Request_Activity.FindMentorViewHolder.class,
                        MenteeRef


                ) {
                    @Override
                    protected void populateViewHolder(FindMentorViewHolder findMentorViewHolder, Mentee mentee, int i) {

                        final String user_id = getRef(i).getKey();

                        allUsersDatabaseRef.child(user_id).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String name = snapshot.child("name").getValue().toString();
                                String language = snapshot.child("language").getValue().toString();
                                String location = snapshot.child("location").getValue().toString();
                                String college = snapshot.child("college").getValue().toString();
                                String course = snapshot.child("course").getValue().toString();
                                String photo = snapshot.child("profileimage").getValue().toString();
                                Glide.with(getApplicationContext()).load(photo).into(findMentorViewHolder.setPhoto(photo));

                                findMentorViewHolder.setName(name);
                                findMentorViewHolder.setLanguage(language);
                                findMentorViewHolder.setLocation(location);
                                findMentorViewHolder.setCollege(college);
                                findMentorViewHolder.setCourse(course);


                                findMentorViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    String menteeid = getRef(i).getKey();

                                    Intent i = new Intent(Mentee_Request_Activity.this, Person_Profile_Activity_2.class);
                                    i.putExtra("menteeid", menteeid);
                                    startActivity(i);
                                    }
                                });
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        }
                };

        SearchResultList.setAdapter(firebaseRecyclerAdapter);

    }


    public static class FindMentorViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public FindMentorViewHolder(View itemView){
            super(itemView);
            mView = itemView;

        }


        public void setName(String name){

            TextView myName = (TextView) mView.findViewById(R.id.searchMenteeName);
            myName.setText(name);
        }

        public void setLocation(String location){

            TextView myLocation = (TextView) mView.findViewById(R.id.searchMenteeLocation);
            myLocation.setText(location);
        }

        public void setLanguage(String language){

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

        public ImageView setPhoto(String photo) {
            ImageView myPhoto = (ImageView) mView.findViewById(R.id.searchMenteeProfilePicture);
            return myPhoto;
        }



    }
}