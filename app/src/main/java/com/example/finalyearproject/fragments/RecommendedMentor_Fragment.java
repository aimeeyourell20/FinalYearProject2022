package com.example.finalyearproject.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalyearproject.AdapterClass;
import com.example.finalyearproject.FindMentor;
import com.example.finalyearproject.Person_Profile_Activity;
import com.example.finalyearproject.R;
import com.example.finalyearproject.RecommendedMentors;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RecommendedMentor_Fragment extends Fragment {

    private View View;
    private RecyclerView RecyclerView;
    private DatabaseReference dr;
    private Button SearchButton;
    private EditText SearchInputText1;
    private AdapterClass adapterClass;
    private List<FindMentor> userList;


    public RecommendedMentor_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View = inflater.inflate(R.layout.fragment_recommendedmentor_, container, false);

        RecyclerView = (RecyclerView) View.findViewById(R.id.RecyclerView);
        RecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        userList = new ArrayList<>();
        adapterClass = new AdapterClass(getActivity(), userList);
        RecyclerView.setAdapter(adapterClass);


        dr = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        SearchButton = (Button) View.findViewById(R.id.search_people_friends_button1);
        SearchInputText1 = (EditText) View.findViewById(R.id.search_box_input);

                onStart();





        return View;
    }

    public void onStart() {
        super.onStart();

        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String result;
                String type = snapshot.child("type").getValue().toString();

                if (type.equals("Mentor")) {
                    result = "Mentee";
                } else {
                    result = "Mentor";
                }

                String skills = snapshot.child("skill1").getValue().toString();

                String language = snapshot.child("language").getValue().toString();

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users");

                Query query = reference.orderByChild("search").equalTo(result + skills + language);

                FirebaseRecyclerAdapter<FindMentor, RecommendedMentor_Fragment.FindMentorViewHolder> firebaseRecyclerAdapter =
                        new FirebaseRecyclerAdapter<FindMentor, RecommendedMentor_Fragment.FindMentorViewHolder>(
                                FindMentor.class,
                                R.layout.all_mentors_displayed,
                                RecommendedMentor_Fragment.FindMentorViewHolder.class,
                                query

                        ) {
                            @Override
                            protected void populateViewHolder(RecommendedMentor_Fragment.FindMentorViewHolder findMentorViewHolder, FindMentor findMentor, int i) {


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

                        };

                RecyclerView.setAdapter(firebaseRecyclerAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

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