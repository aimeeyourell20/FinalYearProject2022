package com.example.finalyearproject.Industry_Fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.finalyearproject.R;
import com.google.android.material.tabs.TabLayout;


public class SearchIndustryFragment extends AppCompatActivity {

    TabLayout tabLayout, tabLayout2;
    ViewPager2 viewPager2;
    IndustryFragmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_industry_search);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager2 = findViewById(R.id.viewPager);

        FragmentManager fragmentManager = getSupportFragmentManager();
        adapter = new IndustryFragmentAdapter(fragmentManager, getLifecycle());
        viewPager2.setAdapter(adapter);

        tabLayout.addTab(tabLayout.newTab().setText("Recommended Mentors"));
        tabLayout.addTab(tabLayout.newTab().setText("Aerospace"));
        tabLayout.addTab(tabLayout.newTab().setText("Agriculture/Animals"));
        tabLayout.addTab(tabLayout.newTab().setText("Business"));
        tabLayout.addTab(tabLayout.newTab().setText("Computer/Technology"));
        tabLayout.addTab(tabLayout.newTab().setText("Construction"));
        tabLayout.addTab(tabLayout.newTab().setText("Education"));
        tabLayout.addTab(tabLayout.newTab().setText("Entertainment"));
        tabLayout.addTab(tabLayout.newTab().setText("Fashion"));
        tabLayout.addTab(tabLayout.newTab().setText("Food/Beverage"));
        tabLayout.addTab(tabLayout.newTab().setText("Healthcare"));
        tabLayout.addTab(tabLayout.newTab().setText("Hospitality"));
        tabLayout.addTab(tabLayout.newTab().setText("Media"));
        tabLayout.addTab(tabLayout.newTab().setText("Telecommunications"));
        tabLayout.addTab(tabLayout.newTab().setText("STEM"));

        //tabLayout2.addTab(tabLayout2.newTab().setText("Hi"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });
    }


}
