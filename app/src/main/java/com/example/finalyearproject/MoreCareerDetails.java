package com.example.finalyearproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import static com.example.finalyearproject.CareerOptions.EXTRA_ID;


public class MoreCareerDetails extends AppCompatActivity{

    private RecyclerView mRecyclerView;
    private JobAdapter mExampleAdapter;
    //private ArrayList<Jobs> mExampleList;
    private RequestQueue mRequestQueue;
    String job_id = "";
    String job_url;
    TextView mjobLink;
    private static final String tag1 = "job_url";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_career_details);

        //mRecyclerView = findViewById(R.id.search);
        //mRecyclerView.setHasFixedSize(true);
        //mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mjobLink = findViewById(R.id.jobLink);

        //mExampleList = new ArrayList<>();

        Intent intent = getIntent();
        job_id = intent.getStringExtra("job_id");



        parseJSON1(job_id);

    }



        private void parseJSON1(String job_id){
            String url= "https://indeed12.p.rapidapi.com/job/" + job_id;



            mRequestQueue = Volley.newRequestQueue(MoreCareerDetails.this);
            String uri = Uri.parse(url)
                    .buildUpon()
                    .build().toString();

            Log.d(tag1, "job2" + uri);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, uri, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    try {

                        JSONArray jsonArray = response.getJSONArray("");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject job = jsonArray.getJSONObject(i);

                            String job_title = job.getString("description");
                            mjobLink.setText(job_title);

                            //mExampleList.add(new Jobs(job_title));
                        }
                        //mExampleList.add(new Jobs(job_title));
                        //mExampleAdapter = new JobAdapter(MoreCareerDetails.this, mExampleList);
                        //mExampleAdapter.notifyDataSetChanged();
                        //mRecyclerView.setAdapter(mExampleAdapter);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    Log.d(tag1, "job" + error);


                }
            }){
                @Override
                public Map<String,String> getHeaders() throws AuthFailureError {
                    Map<String,String> params= new HashMap<>();
                    params.put("x-rapidapi-host", "indeed12.p.rapidapi.com");
                    params.put("x-rapidapi-key", "2b8fed76bedmsh94c42ec37f6f7b0p123d93jsna3049a5071e6");
                    return params;
                }
            };
            mRequestQueue.add(request);



        }



}
