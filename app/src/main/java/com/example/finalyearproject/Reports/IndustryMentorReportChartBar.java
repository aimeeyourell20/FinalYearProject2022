package com.example.finalyearproject.Reports;

import static android.view.View.GONE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.finalyearproject.Industry_Fragments.Aerospace_Fragment;
import com.example.finalyearproject.Industry_Fragments.AgricultureAnimals_Fragment;
import com.example.finalyearproject.Industry_Fragments.Business_Fragment;
import com.example.finalyearproject.Industry_Fragments.ComputerTechnology_Fragment;
import com.example.finalyearproject.Industry_Fragments.Construction_Fragment;
import com.example.finalyearproject.Industry_Fragments.Education_Fragment;
import com.example.finalyearproject.Industry_Fragments.Entertainment_Fragment;
import com.example.finalyearproject.Industry_Fragments.Fashion_Fragment;
import com.example.finalyearproject.Industry_Fragments.FoodBeverage_Fragment;
import com.example.finalyearproject.Industry_Fragments.Healthcare_Fragment;
import com.example.finalyearproject.Industry_Fragments.Hospitality_Fragment;
import com.example.finalyearproject.Industry_Fragments.Media_Fragment;
import com.example.finalyearproject.Industry_Fragments.STEM_Fragment;
import com.example.finalyearproject.Industry_Fragments.Telecommunication_Fragment;
import com.example.finalyearproject.R;
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

public class IndustryMentorReportChartBar extends AppCompatActivity {


    private HorizontalBarChart mBarChart;
    DatabaseReference databaseReference;
    private static final String tag = "aaa";
    private static final String tag1 = "xxx";
    private ImageView mHome;


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_industry_report_chart_bar);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");

        mBarChart = findViewById(R.id.id_horizontal_barchart);

        mHome = findViewById(R.id.home);

        mHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(IndustryMentorReportChartBar.this, Mentee_Reports.class);
                startActivity(i);
                finish();

            }
        });


        // PREPARING THE ARRAY LIST OF BAR ENTRIES
        ArrayList<BarEntry> barEntries = new ArrayList<>();



        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    switch (dataSnapshot.child("industry3").getValue(String.class)) {
                        case "Aerospace":
                            ++Aerospace;
                            break;
                        case "Agriculture":
                            ++Agriculture;
                            break;
                        case "Business":
                            ++Business;
                            break;
                        case "Computer and Technology":
                            ++Computer;
                            break;
                        case "Construction":
                            ++Construction;
                            break;
                        case "Education":
                            ++Education;
                            break;
                        case "Entertainment":
                            ++Entertainment;
                            break;
                        case "Fashion":
                            ++Fashion;
                            break;
                        case "Food":
                            ++Food;
                            break;
                        case "Hospitality":
                            ++Hospitality;
                            break;
                        case "Healthcare":
                            ++Healthcare;
                            break;
                        case "Media":
                            ++Media;
                            break;
                        case "Telecommunications":
                            ++Telecommunications;
                            break;
                        case "STEM":
                            ++STEM;
                            break;

                    }

                    barEntries.add(new BarEntry(0, Aerospace, "Aerospace"));
                    barEntries.add(new BarEntry(1, Agriculture, "Agriculture"));
                    barEntries.add(new BarEntry(2, Business, "Business"));
                    barEntries.add(new BarEntry(3, Computer, "Computer and Technology"));
                    barEntries.add(new BarEntry(4, Construction, "Construction"));
                    barEntries.add(new BarEntry(5, Education, "Education"));
                    barEntries.add(new BarEntry(6, Entertainment, "Entertainment"));
                    barEntries.add(new BarEntry(7, Fashion, "Fashion"));
                    barEntries.add(new BarEntry(8, Food, "Food"));
                    barEntries.add(new BarEntry(9, Healthcare, "Healthcare"));
                    barEntries.add(new BarEntry(10, Hospitality, "Hospitality"));
                    barEntries.add(new BarEntry(11, Media, "Media"));
                    barEntries.add(new BarEntry(12, Telecommunications, "Telecommunications"));
                    barEntries.add(new BarEntry(13, STEM, "STEM"));


                    BarDataSet bardataset = new BarDataSet(barEntries, "Mentors Industry");

                    ArrayList<String> labels = new ArrayList<String>();

                    labels.add("Aerospace");
                    labels.add("Agriculture");
                    labels.add("Business");
                    labels.add("Computer and Technology");
                    labels.add("Construction");
                    labels.add("Education");
                    labels.add("Entertainment");
                    labels.add("Fashion");
                    labels.add("Food");
                    labels.add("Healthcare");
                    labels.add("Hospitality");
                    labels.add("Media");
                    labels.add("Telecommunications");
                    labels.add("STEM");

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
                                Aerospace_Fragment fragment = new Aerospace_Fragment();
                                fm.beginTransaction().replace(R.id.container,fragment).commit();
                                //Intent j = new Intent(IndustryMentorReportChartBar.this, Aerospace_Fragment.class);
                                //startActivity(j);
                            }
                            else if(x == 1){
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                AgricultureAnimals_Fragment fragment = new AgricultureAnimals_Fragment();
                                fm.beginTransaction().replace(R.id.container,fragment).commit();
                            }
                            else if(x == 2){
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                Business_Fragment fragment = new Business_Fragment();
                                fm.beginTransaction().replace(R.id.container,fragment).commit();
                            }
                            else if(x == 3){
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                ComputerTechnology_Fragment fragment = new ComputerTechnology_Fragment();
                                fm.beginTransaction().replace(R.id.container,fragment).commit();
                            }
                            else if(x == 4){
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                Construction_Fragment fragment = new Construction_Fragment();
                                fm.beginTransaction().replace(R.id.container,fragment).commit();
                            }
                            else if(x == 5){
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                Education_Fragment fragment = new Education_Fragment();
                                fm.beginTransaction().replace(R.id.container,fragment).commit();
                            }
                            else if(x == 6){
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                Entertainment_Fragment fragment = new Entertainment_Fragment();
                                fm.beginTransaction().replace(R.id.container,fragment).commit();
                            }
                            else if(x == 7){
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                Fashion_Fragment fragment = new Fashion_Fragment();
                                fm.beginTransaction().replace(R.id.container,fragment).commit();
                            }
                            else if(x == 8){
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                FoodBeverage_Fragment fragment = new FoodBeverage_Fragment();
                                fm.beginTransaction().replace(R.id.container,fragment).commit();
                            }
                            else if(x == 9){
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                Healthcare_Fragment fragment = new Healthcare_Fragment();
                                fm.beginTransaction().replace(R.id.container,fragment).commit();
                            }
                            else if(x == 10){
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                Hospitality_Fragment fragment = new Hospitality_Fragment();
                                fm.beginTransaction().replace(R.id.container,fragment).commit();
                            }
                            else if(x == 11){
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                Media_Fragment fragment = new Media_Fragment();
                                fm.beginTransaction().replace(R.id.container,fragment).commit();
                            }
                            else if(x == 12){
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                Telecommunication_Fragment fragment = new Telecommunication_Fragment();
                                fm.beginTransaction().replace(R.id.container,fragment).commit();
                            }
                            else if(x == 13){
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                STEM_Fragment fragment = new STEM_Fragment();
                                fm.beginTransaction().replace(R.id.container,fragment).commit();
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

    public static class FindMentorViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public FindMentorViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

        }

        public void setType(String type) {

            TextView myType = (TextView) mView.findViewById(R.id.searchMentorType);
            myType.setText(type);
        }

        public void setName(String name) {

            TextView myName = (TextView) mView.findViewById(R.id.searchMentorName);
            myName.setText(name);
        }

        public void setLocation(String location) {

            TextView myLocation = (TextView) mView.findViewById(R.id.searchMentorLocation);
            myLocation.setText(location);
        }

        public void setIndustry(String industry) {

            TextView myIndustry = (TextView) mView.findViewById(R.id.searchMentorIndustry);
            myIndustry.setText(industry);
        }

        public void setSkills1(String skills1) {

            TextView mySkills1 = (TextView) mView.findViewById(R.id.searchMentorBio1);
            mySkills1.setText(skills1);
        }

        public void setCompany(String company) {

            TextView myCompany = (TextView) mView.findViewById(R.id.searchMentorCompany);
            myCompany.setText(company);
        }


    }
}