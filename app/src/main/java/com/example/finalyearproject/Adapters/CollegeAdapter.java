package com.example.finalyearproject.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.finalyearproject.Models.College;
import com.example.finalyearproject.R;

import java.util.ArrayList;

public class CollegeAdapter extends RecyclerView.Adapter<CollegeAdapter.ExampleViewHolder> {
    private Context mContext;
    private ArrayList<College> mExampleList;
    private CollegeAdapter.OnItemClickListener mListener;

    public interface  OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(CollegeAdapter.OnItemClickListener listener){

        mListener = listener;

    }

    public CollegeAdapter(Context context, ArrayList<College> exampleList) {
        mContext = context;
        mExampleList = exampleList;
    }

    @Override
    public CollegeAdapter.ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.collegeheaders, parent, false);
        return new CollegeAdapter.ExampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CollegeAdapter.ExampleViewHolder holder, int position) {
        College currentItem = mExampleList.get(position);


        String name = currentItem.getTitle();
        String country = currentItem.getCountry();
        String city = currentItem.getCity();
        String score = currentItem.getScore();

        holder.mcollegeName.setText(name);
        holder.mcollegeCountry.setText(country);
        holder.mcollegeCity.setText(city);
        holder.mcollegeScore.setText(score);

    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

    public class ExampleViewHolder extends RecyclerView.ViewHolder {

        public TextView mcollegeName;
        public TextView mcollegeCountry;
        public TextView mcollegeCity;
        public TextView mcollegeScore;


        public ExampleViewHolder(View itemView) {
            super(itemView);
            mcollegeName= itemView.findViewById(R.id.collegeName);
            mcollegeCountry = itemView.findViewById(R.id.collegeCountry);
            mcollegeCity = itemView.findViewById(R.id.collegeCity);
            mcollegeScore = itemView.findViewById(R.id.collegeScore);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mListener != null){
                        int position = getAdapterPosition();

                        if(position != RecyclerView.NO_POSITION){
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
