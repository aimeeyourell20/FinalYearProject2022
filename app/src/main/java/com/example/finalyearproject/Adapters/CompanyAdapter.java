package com.example.finalyearproject.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.finalyearproject.Models.Company;
import com.example.finalyearproject.R;

import java.util.ArrayList;

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.ExampleViewHolder> {
    private Context mContext;
    private ArrayList<Company> mExampleList;
    private CompanyAdapter.OnItemClickListener mListener;

    public interface  OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(CompanyAdapter.OnItemClickListener listener){

        mListener = listener;

    }

    public CompanyAdapter(Context context, ArrayList<Company> exampleList) {
        mContext = context;
        mExampleList = exampleList;
    }

    @Override
    public CompanyAdapter.ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.companyheaders, parent, false);
        return new CompanyAdapter.ExampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CompanyAdapter.ExampleViewHolder holder, int position) {
        Company currentItem = mExampleList.get(position);

        String location = currentItem.getHq_location();
        String rating = currentItem.getRating();
        String title = currentItem.getName();


        holder.mcompanyHeader.setText(title);
        holder.mlocationHeader.setText(location);
        holder.mratingHeader.setText(rating);

    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

    public class ExampleViewHolder extends RecyclerView.ViewHolder {

        public TextView mcompanyHeader;
        public TextView mlocationHeader;
        public TextView mratingHeader;


        public ExampleViewHolder(View itemView) {
            super(itemView);
            mcompanyHeader = itemView.findViewById(R.id.companyHeader);
            mlocationHeader = itemView.findViewById(R.id.locationHeader);
            mratingHeader = itemView.findViewById(R.id.ratingHeader);

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