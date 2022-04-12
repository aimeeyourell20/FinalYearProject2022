package com.example.finalyearproject.Industry_Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalyearproject.Adapters.AdapterClass;
import com.example.finalyearproject.Models.FindMentor;
import com.example.finalyearproject.Person_Profile_Activity;
import com.example.finalyearproject.R;
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
    private DatabaseReference RootRef;
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


        RootRef = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        //SearchButton = (Button) View.findViewById(R.id.search_people_friends_button1);
        //SearchInputText1 = (EditText) View.findViewById(R.id.search_box_input);

                onStart();





        return View;
    }

    public void onStart() {
        super.onStart();

        RootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String result;
                String type = snapshot.child("type").getValue().toString();

                //Only display mentors
                if (type.equals("Mentor")) {
                    result = "Mentee";
                } else {
                    result = "Mentor";
                }

                String skills = snapshot.child("skill1").getValue().toString();

                String industry = snapshot.child("industry").getValue().toString();

                String location = snapshot.child("location").getValue().toString();

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users");

                //Query to check if users are a match
                Query query = reference.orderByChild("search").equalTo(result + skills + industry + location);

                FirebaseRecyclerAdapter<FindMentor, FindMentorViewHolder> firebaseRecyclerAdapter =
                        new FirebaseRecyclerAdapter<FindMentor, FindMentorViewHolder>(
                                FindMentor.class,
                                R.layout.all_mentors_displayed,
                                FindMentorViewHolder.class,
                                query

                        ) {
                            @Override
                            protected void populateViewHolder(FindMentorViewHolder findMentorViewHolder, FindMentor findMentor, int i) {


                                //Set mentors details
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
                                        //If profile is selected then show more profile information
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