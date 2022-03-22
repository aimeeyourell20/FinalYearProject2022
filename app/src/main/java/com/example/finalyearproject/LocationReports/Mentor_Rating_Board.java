package com.example.finalyearproject.LocationReports;

import static android.view.View.GONE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.finalyearproject.FindMentor;
import com.example.finalyearproject.Mentee_Find_Mentors_Activity;
import com.example.finalyearproject.Person_Profile_Activity;
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
import com.firebase.ui.database.FirebaseRecyclerAdapter;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Mentor_Rating_Board extends AppCompatActivity {

    private HorizontalBarChart mBarChart;
    DatabaseReference databaseReference;
    int Five;
    int Four;
    int Three;
    int Two;
    int One;
    private ImageView mHome;
    private static final String tag = "Five";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_rating_board);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");

        mBarChart = findViewById(R.id.id_horizontal_barchart);
        // PREPARING THE ARRAY LIST OF BAR ENTRIES
        ArrayList<BarEntry> barEntries = new ArrayList<>();

        mHome = findViewById(R.id.home);

        mHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Mentor_Rating_Board.this, Mentee_Reports.class);
                startActivity(i);

            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    switch (dataSnapshot.child("rating").getValue(Integer.class)) {
                        case 5:
                            ++Five;
                            Log.d(tag, "Five" + Five);
                            break;
                        case 4:
                            ++Four;
                            break;
                        case 3:
                            ++Three;
                            break;
                        case 2:
                            ++Two;
                            break;
                        case 1:
                            ++One;
                            break;
                    }

                    barEntries.add(new BarEntry(0, Five, 5));
                    barEntries.add(new BarEntry(1, Four, 4));
                    barEntries.add(new BarEntry(2, Three, 3));
                    barEntries.add(new BarEntry(3, Two, 2));
                    barEntries.add(new BarEntry(4, One, 1));

                    BarDataSet bardataset = new BarDataSet(barEntries, "Mentors Skills");

                    ArrayList<String> labels = new ArrayList<String>();

                    labels.add("Five");
                    labels.add("Four");
                    labels.add("Three");
                    labels.add("Two");
                    labels.add("One");

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