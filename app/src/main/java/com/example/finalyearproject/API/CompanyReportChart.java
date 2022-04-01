package com.example.finalyearproject.API;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalyearproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class CompanyReportChart extends AppCompatActivity {

    FirebaseDatabase rootNode;
    DatabaseReference databaseReference;
    LineGraphSeries series;
    Button backToMenu;
    FirebaseUser fbuser;
    GraphView graphView;
    int SAP;
    int Microsoft;
    int Facebook;
    private static final String tag = "SAP";
    private static final String tag1 = "Microsoft";
    private static final String tag2 = "Facebook";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");




        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    switch(dataSnapshot.child("company").getValue(String.class)){ //This statement is seeing what "category" is.
                        case "SAP":
                            ++SAP;
                            Log.d(tag, "SAP " + SAP);
                            break;
                        case "Microsoft":
                            ++Microsoft;
                            Log.d(tag1, "Microsoft " + Microsoft);
                            break;
                        case "Facebook":
                            ++Facebook;
                            Log.d(tag2, "Facebook " + Facebook);
                            break;

                    }

                    PieChart pieChart = findViewById(R.id.pieChart);
                    ArrayList<PieEntry> garage = new ArrayList<>();

                    garage.add(new PieEntry(SAP, "SAP"));
                    garage.add(new PieEntry(Microsoft, "Microsoft"));
                    garage.add(new PieEntry(Facebook, "Facebook"));

                    PieDataSet pieDataSet = new PieDataSet(garage, "Company");
                    pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                    pieDataSet.setValueTextColor(Color.BLACK);
                    pieDataSet.setValueTextSize(16f);

                    PieData pieData = new PieData(pieDataSet);

                    pieChart.setData(pieData);
                    pieChart.getDescription().setEnabled(false);
                    pieChart.setCenterText("Company");
                    pieChart.animate();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }
}