package com.example.finalyearproject.Location_Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalyearproject.Models.FindMentor;
import com.example.finalyearproject.Mentees.Mentee_Request_Activity;
import com.example.finalyearproject.R;
import com.example.finalyearproject.Reports.LocationReportChartMentee;
import com.example.finalyearproject.Skills_Fragments.Attention_To_Detail_Fragment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class Denmark_Fragment extends Fragment {

    private View View;
    private androidx.recyclerview.widget.RecyclerView RecyclerView;
    private DatabaseReference dr;
    private Button IndustryButton, SkillsButton;
    private EditText SearchInputText1;
    String searchBoxInput1;
    private ImageView mHome;

    public Denmark_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View = inflater.inflate(R.layout.country_fragment_layout, container, false);

        RecyclerView = (androidx.recyclerview.widget.RecyclerView) View.findViewById(R.id.RecyclerView);
        RecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        dr = FirebaseDatabase.getInstance().getReference().child("users");

        IndustryButton = (Button) View.findViewById(R.id.industryButton);
        SkillsButton = (Button) View.findViewById(R.id.skillButton);
        SearchInputText1 = (EditText) View.findViewById(R.id.search_box_input);

        mHome = View.findViewById(R.id.home);

        mHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), LocationReportChartMentee.class);
                startActivity(i);

            }
        });


        industry(searchBoxInput1);
        IndustryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                searchBoxInput1 = SearchInputText1.getText().toString();
                industry(searchBoxInput1);


            }
        });

        SkillsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String searchBoxInput2 = SearchInputText1.getText().toString();
                onStart(searchBoxInput2);


            }
        });

        return View;
    }

    public void industry(String searchBoxInput1) {
        super.onStart();


        Query searchPeopleAndFriendsQuery = dr.orderByChild("industry")
                .startAt(searchBoxInput1).endAt(searchBoxInput1 + "\uf8ff");
        FirebaseRecyclerAdapter<FindMentor, Attention_To_Detail_Fragment.FindMentorViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<FindMentor, Attention_To_Detail_Fragment.FindMentorViewHolder>(
                        FindMentor.class,
                        R.layout.all_mentors_displayed_2,
                        Attention_To_Detail_Fragment.FindMentorViewHolder.class,
                        searchPeopleAndFriendsQuery

                ) {
                    @Override
                    protected void populateViewHolder(Attention_To_Detail_Fragment.FindMentorViewHolder findMentorViewHolder, FindMentor findMentor, int i) {

                        if (findMentor.getType().equals("Mentee")) {
                            findMentorViewHolder.itemView.setVisibility(View.GONE);
                            findMentorViewHolder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
                        }
                        if(!findMentor.getLocation().equalsIgnoreCase("Denmark")){
                            findMentorViewHolder.itemView.setVisibility(View.GONE);
                            findMentorViewHolder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
                        }
                        else {

                            findMentorViewHolder.setName(findMentor.getName());
                            findMentorViewHolder.setCompany(findMentor.getCompany());
                            findMentorViewHolder.setLocation(findMentor.getLocation());
                            findMentorViewHolder.setSkills1(findMentor.getSkill1());
                            findMentorViewHolder.setIndustry(findMentor.getIndustry());


                            findMentorViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    String mentorid = getRef(i).getKey();

                                    Intent i = new Intent(getActivity(), Mentee_Request_Activity.class);
                                    i.putExtra("mentorid", mentorid);
                                    startActivity(i);

                                }
                            });
                        }
                    }
                };

        RecyclerView.setAdapter(firebaseRecyclerAdapter);
    }




    public void onStart(String searchBoxInput2) {
        super.onStart();


        Query searchPeopleAndFriendsQuery = dr.orderByChild("skill1")
                .startAt(searchBoxInput2).endAt(searchBoxInput2 + "\uf8ff");
        FirebaseRecyclerAdapter<FindMentor, Attention_To_Detail_Fragment.FindMentorViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<FindMentor, Attention_To_Detail_Fragment.FindMentorViewHolder>(
                        FindMentor.class,
                        R.layout.all_mentors_displayed_2,
                        Attention_To_Detail_Fragment.FindMentorViewHolder.class,
                        searchPeopleAndFriendsQuery

                ) {
                    @Override
                    protected void populateViewHolder(Attention_To_Detail_Fragment.FindMentorViewHolder findMentorViewHolder, FindMentor findMentor, int i) {
                        if (findMentor.getType().equals("Mentee")) {
                            findMentorViewHolder.itemView.setVisibility(View.GONE);
                            findMentorViewHolder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
                        }
                        if(!findMentor.getLocation().equalsIgnoreCase("Denmark")){
                            findMentorViewHolder.itemView.setVisibility(View.GONE);
                            findMentorViewHolder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
                        }
                        else {

                            findMentorViewHolder.setName(findMentor.getName());
                            findMentorViewHolder.setCompany(findMentor.getCompany());
                            findMentorViewHolder.setLocation(findMentor.getLocation());
                            findMentorViewHolder.setSkills1(findMentor.getSkill1());
                            findMentorViewHolder.setIndustry(findMentor.getIndustry());

                            findMentorViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    String mentorid = getRef(i).getKey();

                                    Intent i = new Intent(getActivity(), Mentee_Request_Activity.class);
                                    i.putExtra("mentorid", mentorid);
                                    startActivity(i);

                                }
                            });
                        }
                    }
                };

        RecyclerView.setAdapter(firebaseRecyclerAdapter);
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