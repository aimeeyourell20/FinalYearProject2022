package com.example.finalyearproject.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.finalyearproject.FindMentor;
import com.example.finalyearproject.Person_Profile_Activity;
import com.example.finalyearproject.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Argentina_Fragment extends Fragment {

    private View ArgentinaView;
    private RecyclerView argentinaRecyclerView;
    private DatabaseReference angentina;
    private Button SearchButton;
    private EditText SearchInputText1;



    public Argentina_Fragment() {
        //Empty constructor is required
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ArgentinaView = inflater.inflate(R.layout.fragment_argentina_, container, false);

        argentinaRecyclerView = (RecyclerView) ArgentinaView.findViewById(R.id.argentinaRecyclerView);
        argentinaRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        angentina = FirebaseDatabase.getInstance().getReference().child("users");

        SearchButton = (Button) ArgentinaView.findViewById(R.id.search_people_friends_button1);
        SearchInputText1 = (EditText) ArgentinaView.findViewById(R.id.search_box_input);

        SearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String searchBoxInput1 = SearchInputText1.getText().toString();
                onStart(searchBoxInput1);


            }
        });

        return ArgentinaView;
    }




    public void onStart(String searchBoxInput1) {
        super.onStart();

        Query searchPeopleAndFriendsQuery = angentina.orderByChild("all1")
                .startAt(searchBoxInput1).endAt(searchBoxInput1 + "\uf8ff");
        FirebaseRecyclerAdapter<FindMentor, Argentina_Fragment.FindMentorViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<FindMentor, Argentina_Fragment.FindMentorViewHolder>(
                        FindMentor.class,
                        R.layout.all_mentors_displayed,
                        Argentina_Fragment.FindMentorViewHolder.class,
                        searchPeopleAndFriendsQuery


                ) {
                    @Override
                    protected void populateViewHolder(Argentina_Fragment.FindMentorViewHolder findMentorViewHolder, FindMentor findMentor, int i) {

                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        String currentUserId = user.getUid();

                        final String postKey = getRef(i).getKey();

                        if (findMentor.getType().equals("Mentee")) {
                            findMentorViewHolder.itemView.setVisibility(View.GONE);
                            findMentorViewHolder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
                        }
                        if(!findMentor.getLocation().equalsIgnoreCase("Argentina")){
                            findMentorViewHolder.itemView.setVisibility(View.GONE);
                            findMentorViewHolder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
                        }
                        else {
                            findMentorViewHolder.setType(findMentor.getType());
                            findMentorViewHolder.setName(findMentor.getName());
                            findMentorViewHolder.setLanguage(findMentor.getLanguage());
                            findMentorViewHolder.setLocation(findMentor.getLocation());
                            findMentorViewHolder.setSkill1(findMentor.getSkill1());
                            findMentorViewHolder.setSkill2(findMentor.getSkill2());




                            findMentorViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    String mentorid = getRef(i).getKey();

                                    Intent i = new Intent(getActivity(), Person_Profile_Activity.class);
                                    i.putExtra("mentorid", mentorid);
                                    startActivity(i);

                                }
                            });
                        }
                    }
                };



        argentinaRecyclerView.setAdapter(firebaseRecyclerAdapter);
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

            public void setLanguage(String language) {

                TextView myLanguage = (TextView) mView.findViewById(R.id.searchMentorLanguage);
                myLanguage.setText(language);
            }

            public void setSkill1(String skill1) {

                TextView mySkill1 = (TextView) mView.findViewById(R.id.searchMentorBio1);
                mySkill1.setText(skill1);
            }

            public void setSkill2(String skill2) {

                TextView mySkill2 = (TextView) mView.findViewById(R.id.searchMentorBio2);
                mySkill2.setText(skill2);
            }
        }
}