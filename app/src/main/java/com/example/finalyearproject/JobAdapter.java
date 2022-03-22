package com.example.finalyearproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.ExampleViewHolder> {
    private Context mContext;
    private ArrayList<Jobs> mExampleList;
    private OnItemClickListener mListener;

    public interface  OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){

        mListener = listener;

    }

    public JobAdapter(Context context, ArrayList<Jobs> exampleList) {
        mContext = context;
        mExampleList = exampleList;
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.headline_items, parent, false);
        return new ExampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        Jobs currentItem = mExampleList.get(position);


        String job_title = currentItem.getJob_title();
        String job_location = currentItem.getJob_location();
        String id = currentItem.getId();
        String company_name = currentItem.getCompany_name();


        holder.mTextViewCreator.setText(job_title);
//        holder.mtext_view_description.setText(id);
        holder.mtext_view_downloads.setText(job_location);
        holder.mtext_view_type.setText(company_name);
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

    public class ExampleViewHolder extends RecyclerView.ViewHolder {

        public TextView mTextViewCreator;
        public TextView mtext_view_downloads;
        public TextView mtext_view_description;
        public TextView mtext_view_type;

        public ExampleViewHolder(View itemView) {
            super(itemView);
            mtext_view_downloads = itemView.findViewById(R.id.text_view_downloads);
            mTextViewCreator = itemView.findViewById(R.id.text_view_creator);
            mtext_view_type = itemView.findViewById(R.id.text_view_type);

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