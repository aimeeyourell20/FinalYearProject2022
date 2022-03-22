package com.example.finalyearproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class CareerOptions extends AppCompatActivity implements JobAdapter.OnItemClickListener {

    public static final String EXTRA_URL = "jobUrl";
    public static final String EXTRA_JOB_TITLE = "jobTitle";
    public static final String EXTRA_COMPANY_NAME = "companyName";
    public static final String EXTRA_JOB_LOCATION = "jobLocation";
    public static final String EXTRA_ID = "id";

    private RecyclerView mRecyclerView;
    private JobAdapter mExampleAdapter;
    private ArrayList<Jobs> mExampleList;
    private RequestQueue mRequestQueue;
    private EditText search;
    private Button button, button2;

    private static final String tag1 = "job_url";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_career_options);

        mRecyclerView = findViewById(R.id.search);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mExampleList = new ArrayList<>();

        search = findViewById(R.id.jobtitle);
        button = findViewById(R.id.searchButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String string1 = search.getText().toString();
                parseJSON(string1);

            }
        });

    }

    private void parseJSON(String string1) {
        mExampleList.clear();
        String url = "https://indeed12.p.rapidapi.com/jobs/search?query=" + string1 + "&location=Ireland&locality=ie";


        mRequestQueue = Volley.newRequestQueue(this);
        String uri = Uri.parse(url)
                .buildUpon()
                .build().toString();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, uri, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray jsonArray = response.getJSONArray("hits");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject job = jsonArray.getJSONObject(i);

                        String job_title = job.getString("title");
                        String job_location = job.getString("location");
                        String company_name = job.getString("company_name");
                        String url = job.getString("link");
                        String id = job.getString("id");


                        //Intent intent = new Intent(CareerOptions.this, MoreCareerDetails.class);
                        //intent.putExtra("id", id);


                        mExampleList.add(new Jobs(job_title, job_location, company_name, url, id));
                    }

                    mExampleAdapter = new JobAdapter(CareerOptions.this, mExampleList);
                    mExampleAdapter.notifyDataSetChanged();
                    mRecyclerView.setAdapter(mExampleAdapter);
                    mExampleAdapter.setOnItemClickListener(CareerOptions.this);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            @Override
            public Map<String,String> getHeaders() throws AuthFailureError{
                Map<String,String> params= new HashMap<>();
                params.put("x-rapidapi-host", "indeed12.p.rapidapi.com");
                params.put("x-rapidapi-key", "b8fed76bedmsh94c42ec37f6f7b0p123d93jsna3049a5071e6");
                return params;
            }
        };
        mRequestQueue.add(request);
    }



    @Override
    public void onItemClick(int position) {

        Intent jobDetails = new Intent(this, CareerDetails.class);
        Jobs clickedItem = mExampleList.get(position);
        jobDetails.putExtra(EXTRA_URL, clickedItem.getLink());
        jobDetails.putExtra(EXTRA_JOB_TITLE, clickedItem.getJob_title());
        jobDetails.putExtra(EXTRA_COMPANY_NAME, clickedItem.getCompany_name());
        jobDetails.putExtra(EXTRA_JOB_LOCATION, clickedItem.getJob_location());
        jobDetails.putExtra(EXTRA_ID, "https://www.indeed.com/viewjob?jk=" + clickedItem.getId() + "&vjs=3");
        startActivity(jobDetails);
    }
}