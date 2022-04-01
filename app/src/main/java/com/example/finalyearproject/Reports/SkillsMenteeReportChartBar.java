package com.example.finalyearproject.Reports;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.finalyearproject.R;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SkillsMenteeReportChartBar extends AppCompatActivity {
    private HorizontalBarChart mBarChart;
    DatabaseReference databaseReference;
    private ImageView mHome;
    int ActiveListening;
    int Adaptability;
    int Attention;
    int Collaboration;
    int Communication;
    int Computer;
    int Creativity;
    int Critical;
    int Customer;
    int Decision;
    int Empathy;
    int Flexibility;
    int Interpersonal;
    int Leadership;
    int Management;
    int Multitasking;
    int Organisational;
    int People;
    int Positivity;
    int Problem;
    int Responsibility;
    int Self;
    int Teamwork;
    int Time;
    int Transferable;
    int Work;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skills_report_chart2);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");

        mBarChart = findViewById(R.id.id_horizontal_barchart1);
        // PREPARING THE ARRAY LIST OF BAR ENTRIES
        ArrayList<BarEntry> barEntries = new ArrayList<>();

        mHome = findViewById(R.id.home);

        mHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SkillsMenteeReportChartBar.this, Mentor_Reports.class);
                startActivity(i);
                finish();

            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    switch (dataSnapshot.child("skill4").getValue(String.class)) {
                        case "Active Listening":
                            ++ActiveListening;
                            break;
                        case "Adaptability":
                            ++Adaptability;
                            break;
                        case "Attention to Detail":
                            ++Attention;
                            break;
                        case "Collaboration":
                            ++Collaboration;
                            break;
                        case "Communication":
                            ++Communication;
                            break;
                        case "Computer Skills":
                            ++Computer;
                            break;
                        case "Creativity":
                            ++Creativity;
                            break;
                        case "Critical Thinking":
                            ++Critical;
                            break;
                        case "Customer Service":
                            ++Customer;
                            break;
                        case "Decision Making":
                            ++Decision;
                            break;
                        case "Empathy":
                            ++Empathy;
                            break;
                        case "Flexibility":
                            ++Flexibility;
                            break;
                        case "Interpersonal Skills":
                            ++Interpersonal;
                            break;
                        case "Leadership":
                            ++Leadership;
                            break;
                        case "Management Skills":
                            ++Management;
                            break;
                        case "Multitasking":
                            ++Multitasking;
                            break;
                        case "Organisational":
                            ++Organisational;
                            break;
                        case "People Skills":
                            ++People;
                            break;
                        case "Positivity":
                            ++Positivity;
                            break;
                        case "Problem Solving":
                            ++Problem;
                            break;
                        case "Responsibility":
                            ++Responsibility;
                            break;
                        case "Self-Motivation":
                            ++Self;
                            break;
                        case "Teamwork":
                            ++Teamwork;
                            break;
                        case "Time Management":
                            ++Time;
                            break;
                        case "Transferable Skills":
                            ++Transferable;
                            break;
                        case "Work Ethic":
                            ++Work;
                            break;

                    }

                    barEntries.add(new BarEntry(0, ActiveListening, "Active Listening"));
                    barEntries.add(new BarEntry(1, Adaptability, "Adaptability"));
                    barEntries.add(new BarEntry(2, Attention, "Attention to Detail"));
                    barEntries.add(new BarEntry(3, Collaboration, "Collaboration"));
                    barEntries.add(new BarEntry(4, Communication, "Communication"));
                    barEntries.add(new BarEntry(5, Computer, "Computer Skills"));
                    barEntries.add(new BarEntry(6, Creativity, "Creativity"));
                    barEntries.add(new BarEntry(7, Critical, "Critical Thinking"));
                    barEntries.add(new BarEntry(8, Customer, "Customer Service"));
                    barEntries.add(new BarEntry(9, Decision, "Decision Making"));
                    barEntries.add(new BarEntry(10, Empathy, "Empathy"));
                    barEntries.add(new BarEntry(11, Flexibility, "Flexibility"));
                    barEntries.add(new BarEntry(12, Interpersonal, "Interpersonal Skills"));
                    barEntries.add(new BarEntry(13, Leadership, "Leadership"));
                    barEntries.add(new BarEntry(14, Management, "Management Skills"));
                    barEntries.add(new BarEntry(15, Multitasking, "Multitasking"));
                    barEntries.add(new BarEntry(16, Organisational, "Organisational"));
                    barEntries.add(new BarEntry(17, People, "People Skills"));
                    barEntries.add(new BarEntry(18, Positivity, "Positivity"));
                    barEntries.add(new BarEntry(19, Problem, "Problem Solving"));
                    barEntries.add(new BarEntry(20, Responsibility, "Responsibility"));
                    barEntries.add(new BarEntry(21, Self, "Self-Motivation"));
                    barEntries.add(new BarEntry(22, Teamwork, "Teamwork"));
                    barEntries.add(new BarEntry(23, Time, "Time Management"));
                    barEntries.add(new BarEntry(24, Transferable, "Transferable Skills"));
                    barEntries.add(new BarEntry(25, Work, "Work Ethic"));



                    BarDataSet bardataset = new BarDataSet(barEntries, "Mentee Skills to Improve");

                    ArrayList<String> labels = new ArrayList<String>();


                    labels.add("Active Listening");
                    labels.add("Adaptability");
                    labels.add("Attention to Detail");
                    labels.add("Collaboration");
                    labels.add("Communication");
                    labels.add("Computer Skills");
                    labels.add("Creativity");
                    labels.add("Critical Thinking");
                    labels.add("Customer Service");
                    labels.add("Decision Making");
                    labels.add("Empathy");
                    labels.add("Flexibility");
                    labels.add("Interpersonal Skills");
                    labels.add("Leadership");
                    labels.add("Management Skills");
                    labels.add("Multitasking");
                    labels.add("Organisational");
                    labels.add("People Skills");
                    labels.add("Positivity");
                    labels.add("Problem Solving");
                    labels.add("Responsibility");
                    labels.add("Self-Motivation");
                    labels.add("Teamwork");
                    labels.add("Time Management");
                    labels.add("Transferable Skills");
                    labels.add("Work Ethic");




                    //BarData data = new BarData(labels, bardataset);
                    mBarChart.invalidate();//Refreshs
                    XAxis xAxis = mBarChart.getXAxis();
                    xAxis.setLabelCount(labels.size());
                    xAxis.setCenterAxisLabels(false);
                    //xAxis.setLabelCount(labels.size()+1, true);
                    xAxis.setGranularity(1f);
                    mBarChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
                    BarData theData = new BarData(bardataset);
                    mBarChart.setFitBars(true);
                    theData.setBarWidth(0.5f);
                    mBarChart.setData(theData); // set the data and list of labels into chart
                    bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
                    bardataset.setBarBorderWidth(1);
                    mBarChart.animateY(5000);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}