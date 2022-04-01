package com.example.finalyearproject.Reports;

import static android.view.View.GONE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.finalyearproject.R;
import com.example.finalyearproject.Skills_Fragments.Active_Listening_Fragment;
import com.example.finalyearproject.Skills_Fragments.Adaptability_Fragment;
import com.example.finalyearproject.Skills_Fragments.Attention_To_Detail_Fragment;
import com.example.finalyearproject.Skills_Fragments.Collaboration_Fragment;
import com.example.finalyearproject.Skills_Fragments.Communication_Fragment;
import com.example.finalyearproject.Skills_Fragments.Computer_Skills_Fragment;
import com.example.finalyearproject.Skills_Fragments.Creativity_Fragment;
import com.example.finalyearproject.Skills_Fragments.Critical_Thinking_Fragment;
import com.example.finalyearproject.Skills_Fragments.Customer_Service_Fragment;
import com.example.finalyearproject.Skills_Fragments.Decision_Making_Fragment;
import com.example.finalyearproject.Skills_Fragments.Empathy_Fragment;
import com.example.finalyearproject.Skills_Fragments.Flexibility_Fragment;
import com.example.finalyearproject.Skills_Fragments.Interpersonal_Skills_Fragment;
import com.example.finalyearproject.Skills_Fragments.Leadership_Fragment;
import com.example.finalyearproject.Skills_Fragments.Managment_Skills_Fragment;
import com.example.finalyearproject.Skills_Fragments.Multitasking_Fragment;
import com.example.finalyearproject.Skills_Fragments.Organisational_Fragment;
import com.example.finalyearproject.Skills_Fragments.People_Skills_Fragment;
import com.example.finalyearproject.Skills_Fragments.Positivty_Fragment;
import com.example.finalyearproject.Skills_Fragments.Problem_Solving_Fragment;
import com.example.finalyearproject.Skills_Fragments.Responsibility_Fragment;
import com.example.finalyearproject.Skills_Fragments.Self_Motivation_Fragment;
import com.example.finalyearproject.Skills_Fragments.Teamwork_Fragment;
import com.example.finalyearproject.Skills_Fragments.Time_Management_Fragment;
import com.example.finalyearproject.Skills_Fragments.Transferable_Skills_Fragment;
import com.example.finalyearproject.Skills_Fragments.Work_Ethic_Fragment;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SkillsMentorReportChartBar extends AppCompatActivity {
    private HorizontalBarChart mBarChart;
    DatabaseReference databaseReference;
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
    private ImageView mHome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal_bar_chart);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");

        mBarChart = findViewById(R.id.id_horizontal_barchart);
        // PREPARING THE ARRAY LIST OF BAR ENTRIES
        ArrayList<BarEntry> barEntries = new ArrayList<>();

        mHome = findViewById(R.id.home);

        mHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SkillsMentorReportChartBar.this, Mentee_Reports.class);
                startActivity(i);
                finish();

            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    switch (dataSnapshot.child("skill3").getValue(String.class)) {
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

                    BarDataSet bardataset = new BarDataSet(barEntries, "Mentors Skills");

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
                    xAxis.setGranularity(1f);
                    mBarChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
                    BarData theData = new BarData(bardataset);
                    mBarChart.setFitBars(true);
                    mBarChart.setTouchEnabled(true);
                    mBarChart.setPinchZoom(false);
                    mBarChart.setDoubleTapToZoomEnabled(false);
                    theData.setBarWidth(0.5f);
                    mBarChart.setData(theData); // set the data and list of labels into chart
                    bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
                    bardataset.setBarBorderWidth(1);
                    mBarChart.animateY(5000);

                    mBarChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                        @Override
                        public void onValueSelected(Entry e, Highlight h) {
                            float x = e.getX();

                            if (x == 0) {
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                Active_Listening_Fragment fragment = new Active_Listening_Fragment();
                                fm.beginTransaction().replace(R.id.container, fragment).commit();
                            } else if (x == 1) {
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                Adaptability_Fragment fragment = new Adaptability_Fragment();
                                fm.beginTransaction().replace(R.id.container, fragment).commit();
                            } else if (x == 2) {
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                Attention_To_Detail_Fragment fragment = new Attention_To_Detail_Fragment();
                                fm.beginTransaction().replace(R.id.container, fragment).commit();
                            } else if (x == 3) {
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                Collaboration_Fragment fragment = new Collaboration_Fragment();
                                fm.beginTransaction().replace(R.id.container, fragment).commit();
                            } else if (x == 4) {
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                Communication_Fragment fragment = new Communication_Fragment();
                                fm.beginTransaction().replace(R.id.container, fragment).commit();
                            } else if (x == 5) {
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                Computer_Skills_Fragment fragment = new Computer_Skills_Fragment();
                                fm.beginTransaction().replace(R.id.container, fragment).commit();
                            } else if (x == 6) {
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                Creativity_Fragment fragment = new Creativity_Fragment();
                                fm.beginTransaction().replace(R.id.container, fragment).commit();
                            } else if (x == 7) {
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                Critical_Thinking_Fragment fragment = new Critical_Thinking_Fragment();
                                fm.beginTransaction().replace(R.id.container, fragment).commit();
                            } else if (x == 8) {
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                Customer_Service_Fragment fragment = new Customer_Service_Fragment();
                                fm.beginTransaction().replace(R.id.container, fragment).commit();
                            } else if (x == 9) {
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                Decision_Making_Fragment fragment = new Decision_Making_Fragment();
                                fm.beginTransaction().replace(R.id.container, fragment).commit();
                            } else if (x == 10) {
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                Empathy_Fragment fragment = new Empathy_Fragment();
                                fm.beginTransaction().replace(R.id.container, fragment).commit();
                            } else if (x == 11) {
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                Flexibility_Fragment fragment = new Flexibility_Fragment();
                                fm.beginTransaction().replace(R.id.container, fragment).commit();
                            } else if (x == 12) {
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                Interpersonal_Skills_Fragment fragment = new Interpersonal_Skills_Fragment();
                                fm.beginTransaction().replace(R.id.container, fragment).commit();
                            } else if (x == 13) {
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                Leadership_Fragment fragment = new Leadership_Fragment();
                                fm.beginTransaction().replace(R.id.container, fragment).commit();
                            }
                            else if (x == 14) {
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                Interpersonal_Skills_Fragment fragment = new Interpersonal_Skills_Fragment();
                                fm.beginTransaction().replace(R.id.container, fragment).commit();
                            } else if (x == 15) {
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                Leadership_Fragment fragment = new Leadership_Fragment();
                                fm.beginTransaction().replace(R.id.container, fragment).commit();
                            }
                            else if (x == 16) {
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                Managment_Skills_Fragment fragment = new Managment_Skills_Fragment();
                                fm.beginTransaction().replace(R.id.container, fragment).commit();
                            } else if (x == 17) {
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                Multitasking_Fragment fragment = new Multitasking_Fragment();
                                fm.beginTransaction().replace(R.id.container, fragment).commit();
                            }
                            else if (x == 18) {
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                Organisational_Fragment fragment = new Organisational_Fragment();
                                fm.beginTransaction().replace(R.id.container, fragment).commit();
                            } else if (x == 19) {
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                People_Skills_Fragment fragment = new People_Skills_Fragment();
                                fm.beginTransaction().replace(R.id.container, fragment).commit();
                            }
                            else if (x == 20) {
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                Positivty_Fragment fragment = new Positivty_Fragment();
                                fm.beginTransaction().replace(R.id.container, fragment).commit();
                            } else if (x == 21) {
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                Problem_Solving_Fragment fragment = new Problem_Solving_Fragment();
                                fm.beginTransaction().replace(R.id.container, fragment).commit();
                            }
                            else if (x == 22) {
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                Responsibility_Fragment fragment = new Responsibility_Fragment();
                                fm.beginTransaction().replace(R.id.container, fragment).commit();
                            } else if (x == 23) {
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                Self_Motivation_Fragment fragment = new Self_Motivation_Fragment();
                                fm.beginTransaction().replace(R.id.container, fragment).commit();
                            }
                            else if (x == 24) {
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                Teamwork_Fragment fragment = new Teamwork_Fragment();
                                fm.beginTransaction().replace(R.id.container, fragment).commit();
                            } else if (x == 25) {
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                Time_Management_Fragment fragment = new Time_Management_Fragment();
                                fm.beginTransaction().replace(R.id.container, fragment).commit();
                            }
                            else if (x == 26) {
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                Transferable_Skills_Fragment fragment = new Transferable_Skills_Fragment();
                                fm.beginTransaction().replace(R.id.container, fragment).commit();
                            } else if (x == 27) {
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                Work_Ethic_Fragment fragment = new Work_Ethic_Fragment();
                                fm.beginTransaction().replace(R.id.container, fragment).commit();
                            }
                        }

                        @Override
                        public void onNothingSelected() {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}