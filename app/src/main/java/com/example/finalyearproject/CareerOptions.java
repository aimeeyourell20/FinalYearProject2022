package com.example.finalyearproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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


public class CareerOptions extends AppCompatActivity implements JobAdapter.OnItemClickListener {

    public static final String EXTRA_URL = "jobUrl";
    public static final String EXTRA_JOB_TITLE = "jobTitle";
    public static final String EXTRA_COMPANY_NAME = "companyName";
    public static final String EXTRA_JOB_LOCATION = "jobLocation";
    public static final String EXTRA_JOB_DESCRIPTION = "jobDescription";

    private RecyclerView mRecyclerView;
    private JobAdapter mExampleAdapter;
    private ArrayList<Jobs> mExampleList;
    private RequestQueue mRequestQueue;
    private EditText search;
    private Button button;

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
        String url = "https://awesome-indeed.p.rapidapi.com/indeed_jobs?search_query=" + string1 + "&country_code=IE";

        mRequestQueue = Volley.newRequestQueue(this);
        String uri = Uri.parse(url)
                .buildUpon()
                .build().toString();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, uri, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray jsonArray = response.getJSONArray("jobs");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject job = jsonArray.getJSONObject(i);

                        String job_title = job.getString("job_title");
                        String job_location = job.getString("job_location");
                        String job_description = job.getString("job_description");
                        String company_name = job.getString("company_name");
                        String url = job.getString("url");

                        mExampleList.add(new Jobs(job_title, job_location, url, job_description, company_name));
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
                params.put("x-rapidapi-host", "awesome-indeed.p.rapidapi.com");
                params.put("x-rapidapi-key", "23b4c4b7dbmshb8c06bbf402c3a9p13c5c0jsn6a0894ccdf86");
                return params;
            }
        };
        mRequestQueue.add(request);
    }

    @Override
    public void onItemClick(int position) {

        Intent jobDetails = new Intent(this, CareerDetails.class);
        Jobs clickedItem = mExampleList.get(position);
        jobDetails.putExtra(EXTRA_URL, clickedItem.getUrl());
        jobDetails.putExtra(EXTRA_JOB_TITLE, clickedItem.getJob_title());
        jobDetails.putExtra(EXTRA_COMPANY_NAME, clickedItem.getCompany_name());
        jobDetails.putExtra(EXTRA_JOB_LOCATION, clickedItem.getJob_location());
        jobDetails.putExtra(EXTRA_JOB_DESCRIPTION, clickedItem.getJob_description());

        startActivity(jobDetails);
    }
}