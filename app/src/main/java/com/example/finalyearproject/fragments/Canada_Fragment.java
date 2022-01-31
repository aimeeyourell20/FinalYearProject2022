package com.example.finalyearproject.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalyearproject.FindMentor;
import com.example.finalyearproject.Person_Profile_Activity;
import com.example.finalyearproject.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class Canada_Fragment extends Fragment {

    private View CanadaView;
    private RecyclerView canadaRecyclerView;
    private DatabaseReference canada;
    private Button SearchButton;
    private EditText SearchInputText1;



    public Canada_Fragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        CanadaView = inflater.inflate(R.layout.fragment_canada_, container, false);

        canadaRecyclerView = (RecyclerView) CanadaView.findViewById(R.id.RecyclerView);
        canadaRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        canada = FirebaseDatabase.getInstance().getReference().child("users");

        SearchButton = (Button) CanadaView.findViewById(R.id.search_people_friends_button1);
        SearchInputText1 = (EditText) CanadaView.findViewById(R.id.search_box_input);

        SearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String searchBoxInput1 = SearchInputText1.getText().toString();
                onStart(searchBoxInput1);


            }
        });

        return CanadaView;
    }

    public void onStart(String searchBoxInput1) {
        super.onStart();

        Query searchPeopleAndFriendsQuery = canada.orderByChild("all1")
                .startAt(searchBoxInput1).endAt(searchBoxInput1 + "\uf8ff");
        FirebaseRecyclerAdapter<FindMentor, Canada_Fragment.FindMentorViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<FindMentor, Canada_Fragment.FindMentorViewHolder>(
                        FindMentor.class,
                        R.layout.all_mentors_displayed,
                        Canada_Fragment.FindMentorViewHolder.class,
                        searchPeopleAndFriendsQuery

                ) {
                    @Override
                    protected void populateViewHolder(Canada_Fragment.FindMentorViewHolder findMentorViewHolder, FindMentor findMentor, int i) {

                        if (findMentor.getType().equals("Mentee")) {
                            findMentorViewHolder.itemView.setVisibility(View.GONE);
                            findMentorViewHolder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
                        }
                        if(!findMentor.getLocation().equalsIgnoreCase("Canada")){
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

        canadaRecyclerView.setAdapter(firebaseRecyclerAdapter);
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
