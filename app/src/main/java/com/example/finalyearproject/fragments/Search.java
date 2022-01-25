package com.example.finalyearproject.fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.finalyearproject.R;
import com.example.finalyearproject.fragments.FragmentAdapter;
import com.google.android.material.tabs.TabLayout;


public class Search extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager2;
    FragmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager2 = findViewById(R.id.viewPager);

        FragmentManager fragmentManager = getSupportFragmentManager();
        adapter = new FragmentAdapter(fragmentManager, getLifecycle());
        viewPager2.setAdapter(adapter);

        tabLayout.addTab(tabLayout.newTab().setText("Recommended Mentors"));
        tabLayout.addTab(tabLayout.newTab().setText("Argentina"));
        tabLayout.addTab(tabLayout.newTab().setText("Australia"));
        tabLayout.addTab(tabLayout.newTab().setText("Austria"));
        tabLayout.addTab(tabLayout.newTab().setText("Belgium"));
        tabLayout.addTab(tabLayout.newTab().setText("Brazil"));
        tabLayout.addTab(tabLayout.newTab().setText("Bulgaria"));
        tabLayout.addTab(tabLayout.newTab().setText("Canada"));
        tabLayout.addTab(tabLayout.newTab().setText("China"));
        tabLayout.addTab(tabLayout.newTab().setText("Columbia"));
        tabLayout.addTab(tabLayout.newTab().setText("Croatia"));
        tabLayout.addTab(tabLayout.newTab().setText("Denmark"));
        tabLayout.addTab(tabLayout.newTab().setText("Egypt"));
        tabLayout.addTab(tabLayout.newTab().setText("Finland"));
        tabLayout.addTab(tabLayout.newTab().setText("France"));
        tabLayout.addTab(tabLayout.newTab().setText("Germany"));
        tabLayout.addTab(tabLayout.newTab().setText("Greece"));
        tabLayout.addTab(tabLayout.newTab().setText("Hungry"));
        tabLayout.addTab(tabLayout.newTab().setText("Iceland"));
        tabLayout.addTab(tabLayout.newTab().setText("India"));
        tabLayout.addTab(tabLayout.newTab().setText("Ireland"));
        tabLayout.addTab(tabLayout.newTab().setText("Italy"));
        tabLayout.addTab(tabLayout.newTab().setText("Japan"));
        tabLayout.addTab(tabLayout.newTab().setText("Lithuania"));
        tabLayout.addTab(tabLayout.newTab().setText("Malaysia"));
        tabLayout.addTab(tabLayout.newTab().setText("Mexico"));
        tabLayout.addTab(tabLayout.newTab().setText("Netherlands"));
        tabLayout.addTab(tabLayout.newTab().setText("New Zealand"));
        tabLayout.addTab(tabLayout.newTab().setText("Norway"));
        tabLayout.addTab(tabLayout.newTab().setText("Pakistan"));
        tabLayout.addTab(tabLayout.newTab().setText("Poland"));
        tabLayout.addTab(tabLayout.newTab().setText("Portugal"));
        tabLayout.addTab(tabLayout.newTab().setText("Romania"));
        tabLayout.addTab(tabLayout.newTab().setText("Russia"));
        tabLayout.addTab(tabLayout.newTab().setText("Spain"));
        tabLayout.addTab(tabLayout.newTab().setText("Sweden"));
        tabLayout.addTab(tabLayout.newTab().setText("Switzerland"));
        tabLayout.addTab(tabLayout.newTab().setText("Thailand"));
        tabLayout.addTab(tabLayout.newTab().setText("Turkey"));
        tabLayout.addTab(tabLayout.newTab().setText("United Kingdom"));
        tabLayout.addTab(tabLayout.newTab().setText("United States"));




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
