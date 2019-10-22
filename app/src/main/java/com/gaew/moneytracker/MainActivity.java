package com.gaew.moneytracker;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;


public class MainActivity extends AppCompatActivity {
    public static final String EXPENSE = "expense";
    public static final String INCOME = "income";
    public static final String TOKEN = "token";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout tabLayout = findViewById(R.id.tabs);
        final ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(new BudgetPagerAdapter(getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT));
        tabLayout.setupWithViewPager(viewPager);

        FloatingActionButton floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int activeFragmentIndex = viewPager.getCurrentItem();
                Fragment activeFragment = getSupportFragmentManager().getFragments().get(activeFragmentIndex);
                activeFragment.startActivityForResult(new Intent(MainActivity.this, AddItemActivity.class),
                        BudgetFragment.REQUEST_CODE);
        overridePendingTransition(R.anim.diagonal, R.anim.from_left_out);

            }
        });


        tabLayout.getTabAt(0).setText(R.string.expences);
        tabLayout.getTabAt(1).setText(R.string.income);

        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            if (fragment instanceof BudgetFragment) {
                ((BudgetFragment) fragment).loadItems();

            }
        }
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
                    return BudgetFragment.newInstancee(R.color.dark_sky_blue, EXPENSE);
                case 1:
                    return BudgetFragment.newInstancee(R.color.apple_green, INCOME);

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
