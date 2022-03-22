package com.example.finalyearproject.LocationReports;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.finalyearproject.R;
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

public class IndustryMentorReportChartPie extends AppCompatActivity {

    DatabaseReference databaseReference;
    int Aerospace;
    int Agriculture;
    int Business;
    int Computer;
    int Construction;
    int Education;
    int Entertainment;
    int Fashion;
    int Food;
    int Healthcare;
    int Hospitality;
    int Media;
    int Telecommunications;
    int STEM;
    private static final String tag = "Aerospace";
    private static final String tag1 = "Agriculture/Animals";
    private static final String tag2 = "Business";
    private static final String tag3 = "Computer and Technology";
    private static final String tag4 = "Construction";
    private static final String tag5 = "Education";
    private static final String tag6 = "Entertainment";
    private static final String tag7 = "Fashion";
    private static final String tag8 = "Food/Beverage";
    private static final String tag9 = "Healthcare";
    private static final String tag10 = "Media";
    private static final String tag11 = "Telecommunications";
    private static final String tag12 = "STEM";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_industry_report_chart);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");




        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    switch(dataSnapshot.child("industry3").getValue(String.class)){ //This statement is seeing what "category" is.
                        case "Aerospace":
                            ++Aerospace;
                            Log.d(tag, "Aerospace" + Aerospace);
                            break;
                        case "Agriculture/Animals":
                            ++Agriculture;
                            Log.d(tag1, "Agriculture/Animals" + Agriculture);
                            break;
                        case "Business":
                            ++Business;
                            Log.d(tag2, "Business" + Business);
                            break;
                        case "Computer and Technology":
                            ++Computer;
                            Log.d(tag3, "Computer and Technology" + Computer);
                            break;
                        case "Construction":
                            ++Construction;
                            Log.d(tag4, "Construction" + Construction);
                            break;
                        case "Education":
                            ++Education;
                            Log.d(tag5, "Education" + Education);
                            break;
                        case "Entertainment":
                            ++Entertainment;
                            Log.d(tag6, "Entertainment" + Entertainment);
                            break;
                        case "Fashion":
                            ++Fashion;
                            Log.d(tag7, "Fashion" + Fashion);
                            break;
                        case "Food/Beverage":
                            ++Food;
                            Log.d(tag8, "Food/Beverage" + Food);
                            break;
                        case "Healthcare":
                            ++Healthcare;
                            Log.d(tag9, "Healthcare" + Healthcare);
                            break;
                        case "Hospitality":
                            ++Hospitality;
                            break;
                        case "Media":
                            ++Media;
                            Log.d(tag10, "Media" + Media);
                            break;
                        case "Telecommunications":
                            ++Telecommunications;
                            Log.d(tag11, "Telecommunications" + Telecommunications);
                            break;
                        case "STEM":
                            ++STEM;
                            Log.d(tag12, "STEM" + STEM);
                            break;
                    }

                    PieChart pieChart = findViewById(R.id.pieChart);
                    ArrayList<PieEntry> garage = new ArrayList<>();

                    garage.add(new PieEntry(Aerospace, "Aerospace"));
                    garage.add(new PieEntry(Agriculture, "Agriculture/Animals"));
                    garage.add(new PieEntry(Business, "Business"));
                    garage.add(new PieEntry(Computer, "Computer and Technology"));
                    garage.add(new PieEntry(Construction, "Construction"));
                    garage.add(new PieEntry(Education, "Education"));
                    garage.add(new PieEntry(Entertainment, "Entertainment"));
                    garage.add(new PieEntry(Fashion, "Fashion"));
                    garage.add(new PieEntry(Food, "Food/Beverage"));
                    garage.add(new PieEntry(Healthcare, "Healthcare"));
                    garage.add(new PieEntry(Hospitality, "Hospitality"));
                    garage.add(new PieEntry(Media, "Media"));
                    garage.add(new PieEntry(Telecommunications, "Telecommunications"));
                    garage.add(new PieEntry(STEM, "STEM"));


                    PieDataSet pieDataSet = new PieDataSet(garage, "Mentors Industry");
                    pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                    pieDataSet.setValueTextColor(Color.BLACK);
                    pieDataSet.setValueTextSize(16f);

                    PieData pieData = new PieData(pieDataSet);

                    pieChart.setData(pieData);
                    pieChart.getDescription().setEnabled(false);
                    pieChart.setCenterText("Mentors Industry");
                    pieChart.animate();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }
}