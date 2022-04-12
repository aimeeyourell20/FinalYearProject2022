package com.example.finalyearproject.API;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.finalyearproject.Adapters.CompanyAdapter;
import com.example.finalyearproject.Mentees.Meetings_Activity_Mentee;
import com.example.finalyearproject.Mentees.MenteeMainActivity;
import com.example.finalyearproject.Models.Company;
import com.example.finalyearproject.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CompanyInformation extends AppCompatActivity implements CompanyAdapter.OnItemClickListener{
    public static final String EXTRA_DESCRIPTION = "description";
    public static final String EXTRA_EMPLOYEES = "employees";
    public static final String EXTRA_LOCATION = "location";
    public static final String EXTRA_RATING = "rating";
    public static final String EXTRA_TITLE = "title";



    private RecyclerView mRecyclerView;
    private CompanyAdapter mExampleAdapter;
    private ArrayList<Company> mExampleList;
    private RequestQueue mRequestQueue;
    private EditText search;
    private Button button;
    private ImageView mHome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_information);

        mRecyclerView = findViewById(R.id.search);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mExampleList = new ArrayList<>();

        mHome = findViewById(R.id.home);
        mHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CompanyInformation.this, CareerOptionsMentee.class);
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

    private void parseJSON(String string1) {
        mExampleList.clear();
        String url = "https://indeed12.p.rapidapi.com/company/" + string1;


        mRequestQueue = Volley.newRequestQueue(this);
        String uri = Uri.parse(url)
                .buildUpon()
                .build().toString();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, uri, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {

                    //JSONArray jsonArray = response.getJSONArray("");

                    //for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject job = new JSONObject(String.valueOf(response));

                        String description = job.getString("description");
                        String employees = job.getString("employees");
                        String location = job.getString("hq_location");
                        String name = job.getString("name");
                        String rating = job.getString("rating");


                        mExampleList.add(new Company(description, employees, location, name, rating));
                    } catch (JSONException jsonException) {
                    jsonException.printStackTrace();
                }

                mExampleAdapter = new CompanyAdapter(CompanyInformation.this, mExampleList);
                    mExampleAdapter.notifyDataSetChanged();
                    mRecyclerView.setAdapter(mExampleAdapter);
                    mExampleAdapter.setOnItemClickListener(CompanyInformation.this);
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
                params.put("x-rapidapi-host", "indeed12.p.rapidapi.com");
                params.put("x-rapidapi-key", "b8fed76bedmsh94c42ec37f6f7b0p123d93jsna3049a5071e6");
                return params;
            }
        };
        mRequestQueue.add(request);
    }



    @Override
    public void onItemClick(int position) {

        Intent companyDetails = new Intent(this, CompanyDetails.class);
        Company clickedItem = mExampleList.get(position);
        companyDetails.putExtra(EXTRA_DESCRIPTION, clickedItem.getDescription());
        companyDetails.putExtra(EXTRA_EMPLOYEES, clickedItem.getEmployees());
        companyDetails.putExtra(EXTRA_LOCATION, clickedItem.getHq_location());
        companyDetails.putExtra(EXTRA_RATING, clickedItem.getRating());
        companyDetails.putExtra(EXTRA_TITLE, clickedItem.getName());
        startActivity(companyDetails);
    }
}