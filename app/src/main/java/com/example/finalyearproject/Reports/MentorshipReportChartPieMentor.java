package com.example.finalyearproject.Reports;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalyearproject.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MentorshipReportChartPieMentor extends AppCompatActivity {

    DatabaseReference databaseReference;
    private ImageView mHome;
    int Mentor;
    int Mentee;
    private static final String tag = "Mentor";
    private static final String tag1 = "Mentee";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barchart);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");


        mHome = findViewById(R.id.home);

        mHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MentorshipReportChartPieMentor.this, Mentor_Reports.class);
                startActivity(i);
                finish();
            }
        });


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    switch(dataSnapshot.child("type").getValue(String.class)){ //This statement is seeing what "category" is.
                        case "Mentor":
                            ++Mentor;
                            Log.d(tag, "Mentor " + Mentor);
                            break;
                        case "Mentee":
                            ++Mentee;
                            Log.d(tag1, "Mentee " + Mentee);
                            break;


                    }

                    int totalUsers = Mentor + Mentee;


                    PieChart pieChart = findViewById(R.id.pieChart);
                    ArrayList<PieEntry> garage = new ArrayList<>();

                    garage.add(new PieEntry(Mentor, "Mentor"));
                    garage.add(new PieEntry(Mentee, "Mentee"));
                    garage.add(new PieEntry(totalUsers, "Total Users"));


                    PieDataSet pieDataSet = new PieDataSet(garage, "Type");
                    pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                    pieDataSet.setValueTextColor(Color.BLACK);
                    pieDataSet.setValueTextSize(16f);

                    PieData pieData = new PieData(pieDataSet);

                    pieChart.setData(pieData);
                    pieChart.getDescription().setEnabled(false);
                    pieChart.setCenterText("Type");
                    pieChart.animate();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }
}