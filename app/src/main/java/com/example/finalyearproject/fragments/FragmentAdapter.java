package com.example.finalyearproject.fragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class FragmentAdapter extends FragmentStateAdapter {

    public FragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position){


            case 1: return new Argentina_Fragment();

            case 2: return new Australia_Fragment();

            case 3: return new Austria_Fragment();

            case 4: return new Belgium_Fragment();

            case 5: return new Brazil_Fragment();

            case 6: return new Bulgaria_Fragment();

            case 7: return new Canada_Fragment();

            case 8: return new China_Fragment();

            case 9: return new Columbia_Fragment();

            case 10: return new Croatia_Fragment();

            case 11: return new Denmark_Fragment();

            case 12: return new Egypt_Fragment();

            case 13: return new Finland_Fragment();

            case 14: return new France_Fragment();

            case 15: return new Germany_Fragment();

            case 16: return new Greece_Fragment();

            case 17: return new Hungry_Fragment();

            case 18: return new Iceland_Fragment();

            case 19: return new India_Fragment();

            case 20: return new Ireland_Fragment();

            case 21: return new Italy_Fragment();

            case 22: return new Japan_Fragment();

            case 23: return new Lithuania_Fragment();

            case 24: return new Malaysia_Fragment();

            case 25: return new Mexico_Fragment();

            case 26: return new Netherlands_Fragment();

            case 27: return new NewZealand_Fragment();

            case 28: return new Norway_Fragment();

            case 29: return new Pakistan_Fragment();

            case 30: return new Poland_Fragment();

            case 31: return new Portugal_Fragment();

            case 32: return new Romania_Fragment();

            case 33: return new Russia_Fragment();

            case 34: return new Spain_Fragment();

            case 35: return new Sweden_Fragment();

            case 36: return new Switzerland_Fragment();

            case 37: return new Thailand_Fragment();

            case 38: return new Turkey_Fragment();

            case 39: return new UnitedKingdom_Fragment();

            case 40: return new UnitedStates_Fragment();

            case 41: return new RecommendedMentor_Fragment();


        }

        return new RecommendedMentor_Fragment();
    }

    @Override
    public int getItemCount() {
        return 41;
    }
}
