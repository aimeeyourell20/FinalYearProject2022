package com.example.finalyearproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

public class MentorshipReportChart extends AppCompatActivity {

    DatabaseReference databaseReference;
    int Mentor;
    int Mentee;
    private static final String tag = "Mentor";
    private static final String tag1 = "Mentee";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barchart);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");




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

                    PieChart pieChart = findViewById(R.id.pieChart);
                    ArrayList<PieEntry> garage = new ArrayList<>();

                    garage.add(new PieEntry(Mentor, "Mentor"));
                    garage.add(new PieEntry(Mentee, "Mentee"));


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