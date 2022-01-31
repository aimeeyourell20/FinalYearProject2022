package com.example.finalyearproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class Mentee_Request_Activity extends AppCompatActivity {

    private Toolbar mtoolbar;
    private RecyclerView SearchResultList;
    private DatabaseReference allUsersDatabaseRef;
    private DatabaseReference MenteeRef,MentorRef;
    private ImageButton SearchButton;
    private EditText SearchInputText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentee_request);

        allUsersDatabaseRef = FirebaseDatabase.getInstance().getReference().child("users");

       // mtoolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(mtoolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setTitle("Mentee Requests");

        MenteeRef = FirebaseDatabase.getInstance().getReference().child("MentorshipRequests");



        SearchResultList = (RecyclerView) findViewById(R.id.search_result_list);
        SearchResultList.setHasFixedSize(true);
        SearchResultList.setLayoutManager(new LinearLayoutManager(this));

        SearchButton = (ImageButton) findViewById(R.id.search_people_friends_button);
        SearchInputText = (EditText) findViewById(R.id.search_box_input);

        SearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String searchBoxInput = SearchInputText.getText().toString();
                SearchPeopleAndFriends(searchBoxInput);
            }
        });
    }

    private void SearchPeopleAndFriends(String searchBoxInput) {

        Query searchPeopleAndFriendsQuery = allUsersDatabaseRef.orderByChild("name")
                .startAt(searchBoxInput).endAt(searchBoxInput + "\uf8ff");
        FirebaseRecyclerAdapter<Mentee, Mentee_Request_Activity.FindMentorViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Mentee, Mentee_Request_Activity.FindMentorViewHolder>(
                        Mentee.class,
                        R.layout.all_mentees_displayed,
                        Mentee_Request_Activity.FindMentorViewHolder.class,
                        searchPeopleAndFriendsQuery

                ) {
                    @Override
                    protected void populateViewHolder(FindMentorViewHolder findMentorViewHolder, Mentee mentee, int i) {
                        if(mentee.getType().equals("Mentor")){
                            findMentorViewHolder.itemView.setVisibility(View.GONE);
                            findMentorViewHolder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
                        }
                        else {


                            findMentorViewHolder.setName(mentee.getName());
                            findMentorViewHolder.setLanguage(mentee.getLanguage());
                            findMentorViewHolder.setLocation(mentee.getLocation());
                            findMentorViewHolder.setCollege(mentee.getCollege());
                            findMentorViewHolder.setCourse(mentee.getCourse());


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



    }
}