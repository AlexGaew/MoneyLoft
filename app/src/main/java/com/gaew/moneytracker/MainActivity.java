package com.gaew.moneytracker;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
    public static final String EXPENSE = "expense";
    public static final String INCOME = "income";
    private static final String USER_ID = "GTwoA";
    public static final String TOKEN = "token";
    private Api mApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout tablLayout = findViewById(R.id.tabs);
        final ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(new BudgetPagerAdapter(getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT));
        tablLayout.setupWithViewPager(viewPager);

        FloatingActionButton floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int activeFragmentIndex = viewPager.getCurrentItem();
                Fragment activeFragment = getSupportFragmentManager().getFragments().get(activeFragmentIndex);
                activeFragment.startActivityForResult(new Intent(MainActivity.this, AddItemActivity.class), BudgetFragment.REQUEST_CODE);

            }
        });


        tablLayout.getTabAt(0).setText(R.string.expences);
        tablLayout.getTabAt(1).setText(R.string.income);

        mApi = ((LoftApp) getApplication()).getApi();
        final String token = PreferenceManager.getDefaultSharedPreferences(this).getString(TOKEN,"");
        if(TextUtils.isEmpty(token)) {

            Call<Status> auth = mApi.auth(USER_ID);
            auth.enqueue(new Callback<Status>() {
                @Override
                public void onResponse(Call<Status> call, Response<Status> response) {


                    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(MainActivity.this).edit();
                    editor.putString(TOKEN, response.body().getToken());
                    editor.apply();
                }

                @Override
                public void onFailure(Call<Status> call, Throwable t) {

                }
            });
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
