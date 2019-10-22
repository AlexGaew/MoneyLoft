package com.gaew.moneytracker;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.preference.PreferenceManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AuthActivity extends AppCompatActivity {

    private Api mApi;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);


        mApi = ((LoftApp) getApplication()).getApi();

      final Button authButton = findViewById(R.id.enter_button);

        authButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.diagonal,R.anim.fade_out);

                startActivity(new Intent(AuthActivity.this,MainActivity.class));

            }
        });
        final String token = PreferenceManager.getDefaultSharedPreferences(this).getString(MainActivity.TOKEN,"");

        if(TextUtils.isEmpty(token)){
            
            auth();

            //Получить токен и сохранить его
        }
        else {
            finish();
            startActivity(new Intent(this,MainActivity.class));



        }
    }

    private void auth() {
        String androidID = Settings.Secure.getString(getContentResolver(),Settings.Secure.ANDROID_ID);

        Call<Status> auth = mApi.auth(androidID);
        auth.enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response)
            {

                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(AuthActivity.this).edit();
                editor.putString(MainActivity.TOKEN, response.body().getToken());
                editor.apply();
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {

            }
        });
    }



}


