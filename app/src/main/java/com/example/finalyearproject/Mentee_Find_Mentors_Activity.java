package com.example.finalyearproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


public class Mentee_Find_Mentors_Activity extends AppCompatActivity {

        private Toolbar mtoolbar;
        private Button SearchButton,SearchButton2, SearchButton3, SearchButton4, SearchButton5;
        private EditText SearchInputText1,SearchInputText2, SearchInputText3, SearchInputText4, SearchInputText5;
        private RecyclerView SearchResultList;
        private DatabaseReference allUsersDatabaseRef;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_mentee_find_mentors);

                allUsersDatabaseRef = FirebaseDatabase.getInstance().getReference().child("users");

                mtoolbar = (Toolbar) findViewById(R.id.toolbar);
                setSupportActionBar(mtoolbar);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setTitle("Find Friends");

                SearchResultList = (RecyclerView) findViewById(R.id.search_result_list);
                SearchResultList.setHasFixedSize(true);
                SearchResultList.setLayoutManager(new LinearLayoutManager(this));

                SearchButton = (Button) findViewById(R.id.search_people_friends_button1);
                SearchButton2 = (Button) findViewById(R.id.search_people_friends_button2);
                //SearchButton3 = (Button) findViewById(R.id.search_people_friends_button3);
                //SearchButton4 = (Button) findViewById(R.id.search_people_friends_button4);
                //SearchButton5 = (Button) findViewById(R.id.search_people_friends_button5);
                SearchInputText1 = (EditText) findViewById(R.id.search_box_input);
                SearchInputText2 = (EditText) findViewById(R.id.search_box_input);
                //SearchInputText3 = (EditText) findViewById(R.id.search_box_input);
                //SearchInputText4 = (EditText) findViewById(R.id.search_box_input);
                //SearchInputText5 = (EditText) findViewById(R.id.search_box_input);

                SearchButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                                String searchBoxInput1 = SearchInputText1.getText().toString();
                                SearchPeopleAndFriends(searchBoxInput1);


                        }
                });

                SearchButton2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                String searchBoxInput2 = SearchInputText2.getText().toString();
                                SearchPeopleAndFriends2(searchBoxInput2);

                        }
                });

                /*SearchButton3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                String searchBoxInput3 = SearchInputText3.getText().toString();
                                SearchPeopleAndFriends3(searchBoxInput3);
                        }
                });
                SearchButton4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                String searchBoxInput4 = SearchInputText4.getText().toString();
                                SearchPeopleAndFriends4(searchBoxInput4);
                        }
                });
                SearchButton5.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                String searchBoxInput5 = SearchInputText5.getText().toString();
                                SearchPeopleAndFriends5(searchBoxInput5);
                        }
                });
*/


        }

       /* private void SearchPeopleAndFriends5(String searchBoxInput5) {
                Query searchPeopleAndFriendsQuery = allUsersDatabaseRef.orderByChild("skill3")
                        .startAt(searchBoxInput5).endAt(searchBoxInput5 + "\uf8ff");
                FirebaseRecyclerAdapter<FindMentor, FindMentorViewHolder> firebaseRecyclerAdapter =
                        new FirebaseRecyclerAdapter<FindMentor, FindMentorViewHolder>(
                                FindMentor.class,
                                R.layout.all_mentors_displayed,
                                FindMentorViewHolder.class,
                                searchPeopleAndFriendsQuery
                        ) {
                                @Override
                                protected void populateViewHolder(FindMentorViewHolder findMentorViewHolder, FindMentor findMentor, int i) {
                                        if (findMentor.getType().equals("Mentee")) {
                                                findMentorViewHolder.itemView.setVisibility(View.GONE);
                                                findMentorViewHolder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
                                        } else {
                                                findMentorViewHolder.setType(findMentor.getType());
                                                findMentorViewHolder.setName(findMentor.getName());
                                                findMentorViewHolder.setLanguage(findMentor.getLanguage());
                                                findMentorViewHolder.setLocation(findMentor.getLocation());
                                                findMentorViewHolder.setSkill1(findMentor.getSkill1());
                                                findMentorViewHolder.setSkill2(findMentor.getSkill2());
                                                findMentorViewHolder.setSkill3(findMentor.getSkill3());
                                                findMentorViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View view) {
                                                                String mentorid = getRef(i).getKey();
                                                                Intent i = new Intent(Mentee_Find_Mentors_Activity.this, Person_Profile_Activity.class);
                                                                i.putExtra("mentorid", mentorid);
                                                                startActivity(i);
                                                        }
                                                });
                                        }
                                }
                        };
                SearchResultList.setAdapter(firebaseRecyclerAdapter);
        }
        private void SearchPeopleAndFriends4(String searchBoxInput4) {
                Query searchPeopleAndFriendsQuery = allUsersDatabaseRef.orderByChild("language")
                        .startAt(searchBoxInput4).endAt(searchBoxInput4 + "\uf8ff");
                FirebaseRecyclerAdapter<FindMentor, FindMentorViewHolder> firebaseRecyclerAdapter =
                        new FirebaseRecyclerAdapter<FindMentor, FindMentorViewHolder>(
                                FindMentor.class,
                                R.layout.all_mentors_displayed,
                                FindMentorViewHolder.class,
                                searchPeopleAndFriendsQuery
                        ) {
                                @Override
                                protected void populateViewHolder(FindMentorViewHolder findMentorViewHolder, FindMentor findMentor, int i) {
                                        if (findMentor.getType().equals("Mentee")) {
                                                findMentorViewHolder.itemView.setVisibility(View.GONE);
                                                findMentorViewHolder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
                                        } else {
                                                findMentorViewHolder.setType(findMentor.getType());
                                                findMentorViewHolder.setName(findMentor.getName());
                                                findMentorViewHolder.setLanguage(findMentor.getLanguage());
                                                findMentorViewHolder.setLocation(findMentor.getLocation());
                                                findMentorViewHolder.setSkill1(findMentor.getSkill1());
                                                findMentorViewHolder.setSkill2(findMentor.getSkill2());
                                                findMentorViewHolder.setSkill3(findMentor.getSkill3());
                                                findMentorViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View view) {
                                                                String mentorid = getRef(i).getKey();
                                                                Intent i = new Intent(Mentee_Find_Mentors_Activity.this, Person_Profile_Activity.class);
                                                                i.putExtra("mentorid", mentorid);
                                                                startActivity(i);
                                                        }
                                                });
                                        }
                                }
                        };
                SearchResultList.setAdapter(firebaseRecyclerAdapter);
        }
        private void SearchPeopleAndFriends3(String searchBoxInput3) {
                Query searchPeopleAndFriendsQuery = allUsersDatabaseRef.orderByChild("location")
                        .startAt(searchBoxInput3).endAt(searchBoxInput3 + "\uf8ff");
                FirebaseRecyclerAdapter<FindMentor, FindMentorViewHolder> firebaseRecyclerAdapter =
                        new FirebaseRecyclerAdapter<FindMentor, FindMentorViewHolder>(
                                FindMentor.class,
                                R.layout.all_mentors_displayed,
                                FindMentorViewHolder.class,
                                searchPeopleAndFriendsQuery
                        ) {
                                @Override
                                protected void populateViewHolder(FindMentorViewHolder findMentorViewHolder, FindMentor findMentor, int i) {
                                        if (findMentor.getType().equals("Mentee")) {
                                                findMentorViewHolder.itemView.setVisibility(View.GONE);
                                                findMentorViewHolder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
                                        } else {
                                                findMentorViewHolder.setType(findMentor.getType());
                                                findMentorViewHolder.setName(findMentor.getName());
                                                findMentorViewHolder.setLanguage(findMentor.getLanguage());
                                                findMentorViewHolder.setLocation(findMentor.getLocation());
                                                findMentorViewHolder.setSkill1(findMentor.getSkill1());
                                                findMentorViewHolder.setSkill2(findMentor.getSkill2());
                                                findMentorViewHolder.setSkill3(findMentor.getSkill3());
                                                findMentorViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View view) {
                                                                String mentorid = getRef(i).getKey();
                                                                Intent i = new Intent(Mentee_Find_Mentors_Activity.this, Person_Profile_Activity.class);
                                                                i.putExtra("mentorid", mentorid);
                                                                startActivity(i);
                                                        }
                                                });
                                        }
                                }
                        };
                SearchResultList.setAdapter(firebaseRecyclerAdapter);
        }*/



        private void SearchPeopleAndFriends2(String searchBoxInput2) {
                Query searchPeopleAndFriendsQuery = allUsersDatabaseRef.orderByChild("all1")
                        .startAt(searchBoxInput2).endAt(searchBoxInput2 + "\uf8ff");
                FirebaseRecyclerAdapter<FindMentor, FindMentorViewHolder> firebaseRecyclerAdapter =
                        new FirebaseRecyclerAdapter<FindMentor, FindMentorViewHolder>(
                                FindMentor.class,
                                R.layout.all_mentors_displayed,
                                FindMentorViewHolder.class,
                                searchPeopleAndFriendsQuery

                        ) {
                                @Override
                                protected void populateViewHolder(FindMentorViewHolder findMentorViewHolder, FindMentor findMentor, int i) {

                                        if (findMentor.getType().equals("Mentee")) {
                                                findMentorViewHolder.itemView.setVisibility(View.GONE);
                                                findMentorViewHolder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
                                        } else {

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

                                                                Intent i = new Intent(Mentee_Find_Mentors_Activity.this, Person_Profile_Activity.class);
                                                                i.putExtra("mentorid", mentorid);
                                                                startActivity(i);

                                                        }
                                                });
                                        }
                                }
                        };

                SearchResultList.setAdapter(firebaseRecyclerAdapter);

        }


        private void SearchPeopleAndFriends(String searchBoxInput1) {

                Query searchPeopleAndFriendsQuery = allUsersDatabaseRef.orderByChild("all2")
                        .startAt(searchBoxInput1).endAt(searchBoxInput1 + "\uf8ff");
                FirebaseRecyclerAdapter<FindMentor, FindMentorViewHolder> firebaseRecyclerAdapter =
                        new FirebaseRecyclerAdapter<FindMentor, FindMentorViewHolder>(
                                FindMentor.class,
                                R.layout.all_mentors_displayed,
                                FindMentorViewHolder.class,
                                searchPeopleAndFriendsQuery

                        ) {
                                @Override
                                protected void populateViewHolder(FindMentorViewHolder findMentorViewHolder, FindMentor findMentor, int i) {

                                        if (findMentor.getType().equals("Mentee")) {
                                                findMentorViewHolder.itemView.setVisibility(View.GONE);
                                                findMentorViewHolder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
                                        } else {

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

                                                                Intent i = new Intent(Mentee_Find_Mentors_Activity.this, Person_Profile_Activity.class);
                                                                i.putExtra("mentorid", mentorid);
                                                                startActivity(i);

                                                        }
                                                });
                                        }
                                }
                        };

                SearchResultList.setAdapter(firebaseRecyclerAdapter);

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