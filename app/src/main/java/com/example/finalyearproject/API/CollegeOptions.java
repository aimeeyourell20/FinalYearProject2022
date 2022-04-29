package com.example.finalyearproject.API;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.finalyearproject.Adapters.CollegeAdapter;
import com.example.finalyearproject.Mentees.Meetings_Activity_Mentee;
import com.example.finalyearproject.Mentees.MenteeMainActivity;
import com.example.finalyearproject.Models.College;
import com.example.finalyearproject.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CollegeOptions extends AppCompatActivity implements CollegeAdapter.OnItemClickListener{
    public static final String EXTRA_COUNTRY = "country";
    public static final String EXTRA_CITY = "city";
    public static final String EXTRA_RANKING = "rank_display";
    public static final String EXTRA_REGION = "region";
    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_SCORE= "score";

    private static final String tag1 = "step 1";

    private RecyclerView mRecyclerView;
    private CollegeAdapter mExampleAdapter;
    private ArrayList<College> mExampleList;
    private RequestQueue mRequestQueue;
    private EditText search;
    private Button button, button2;
    private ImageView mHome;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college_options);

        mRecyclerView = findViewById(R.id.search);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mExampleList = new ArrayList<>();

        mHome = findViewById(R.id.home);
        mHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CollegeOptions.this, CareerOptionsMentee.class);
                startActivity(i);
                finish();
            }
        });

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

    //creates Request objects which represents a desired request to be executed
    private void parseJSON(String string1) {
        mExampleList.clear();
        String url = "https://world-university-search-and-rankings.p.rapidapi.com/university/ranking?country=" + string1 + "&top=20";

        //All requests in Volley are placed in a queue first and then processed
        mRequestQueue = Volley.newRequestQueue(this);
        String uri = Uri.parse(url)
                .buildUpon()
                .build().toString();

        //To receive JSON Array from the server
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, uri, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                try {

                    for (int i = 0; i < response.length(); i++) {
                        JSONObject college = response.getJSONObject(i);
                        String country = college.getString("country");
                        String city = college.getString("city");
                        String title = college.getString("title");
                        String score = college.getString("score");
                        String rank_display = college.getString("rank_display");
                        String region = college.getString("region");



                    mExampleList.add(new College(country, city, title, score, rank_display, region));
                }

                mExampleAdapter = new CollegeAdapter(CollegeOptions.this, mExampleList);
                mExampleAdapter.notifyDataSetChanged();
                mRecyclerView.setAdapter(mExampleAdapter);
                mExampleAdapter.setOnItemClickListener(CollegeOptions.this);
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
            public Map<String,String> getHeaders() throws AuthFailureError {
                Map<String,String> params= new HashMap<>();
                params.put("x-rapidapi-host", "world-university-search-and-rankings.p.rapidapi.com");
                params.put("x-rapidapi-key", "b8fed76bedmsh94c42ec37f6f7b0p123d93jsna3049a5071e6");
                return params;
            }
        };
        //Fixes network timeout error
        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(request);
    }



    @Override
    public void onItemClick(int position) {

        Intent collegeDetails = new Intent(this, CollegeDetails.class);
        College clickedItem = mExampleList.get(position);
        collegeDetails.putExtra(EXTRA_COUNTRY, clickedItem.getCountry());
        collegeDetails.putExtra(EXTRA_CITY, clickedItem.getCity());
        collegeDetails.putExtra(EXTRA_TITLE, clickedItem.getTitle());
        collegeDetails.putExtra(EXTRA_SCORE, clickedItem.getScore());
        collegeDetails.putExtra(EXTRA_RANKING, clickedItem.getRank_display());
        collegeDetails.putExtra(EXTRA_REGION, clickedItem.getRegion());
        startActivity(collegeDetails);
    }
}