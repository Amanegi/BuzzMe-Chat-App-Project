package com.example.buzzme.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.buzzme.R;
import com.example.buzzme.utility.SharedPrefHelper;

public class LauncherActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    //set original theme after splash
                    setTheme(R.style.BuzzMeTheme);

                    //manage session
                    if (SharedPrefHelper.getPrefUserName(LauncherActivity.this) == null) {
                        startActivity(new Intent(LauncherActivity.this, LoginActivity.class));
                    } else {
                        startActivity(new Intent(LauncherActivity.this, DashboardActivity.class));
                    }
                    finish();
                }
            }
        };
        thread.start();
    }
}
