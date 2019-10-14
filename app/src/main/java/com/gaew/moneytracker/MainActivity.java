package com.gaew.moneytracker;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout tablLayout = findViewById(R.id.tabs);
        ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(new BudgetPagerAdapter(getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT));
        tablLayout.setupWithViewPager(viewPager);
        tablLayout.getTabAt(0).setText(R.string.expences);
        tablLayout.getTabAt(1).setText(R.string.income);

    }

    static class BudgetPagerAdapter extends FragmentPagerAdapter {


        public BudgetPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);

        }

        @NonNull
        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return  BudgetFragment.newInstancee(R.color.dark_sky_blue);
                case 1:
                    return  BudgetFragment.newInstancee(R.color.apple_green);

                default:
                    return null;

            }
        }


        @Override
        public int getCount() {
            return 2;
        }


    }


}
