package com.example.finalyearproject.Industry_Fragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;



public class IndustryFragmentAdapter extends FragmentStateAdapter {

    public IndustryFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position){


            case 1: return new Aerospace_Fragment();

            case 2: return new AgricultureAnimals_Fragment();

            case 3: return new Business_Fragment();

            case 4: return new ComputerTechnology_Fragment();

            case 5: return new Construction_Fragment();

            case 6: return new Education_Fragment();

            case 7: return new Entertainment_Fragment();

            case 8: return new Fashion_Fragment();

            case 9: return new FoodBeverage_Fragment();

            case 10: return new Healthcare_Fragment();

            case 11: return new Hospitality_Fragment();

            case 12: return new Media_Fragment();

            case 13: return new Telecommunication_Fragment();

            case 14: return new STEM_Fragment();

        }

        return new RecommendedMentor_Fragment();
    }

    @Override
    public int getItemCount() {
        return 15;
    }
}
