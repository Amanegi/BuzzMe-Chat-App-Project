package com.example.buzzme.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.buzzme.R;
import com.example.buzzme.adapter.DashboardPagerAdapter;
import com.example.buzzme.databinding.ActivityDashboardBinding;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

public class DashboardActivity extends AppCompatActivity {
    private ActivityDashboardBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard);

        //finish activity if user logged out
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ProfileActivity.LOGOUT_BROADCAST);
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                finish();
                unregisterReceiver(this);
            }
        }, intentFilter);


        //setting toolbar as action bar
        setSupportActionBar(mBinding.dashToolbar);

        DashboardPagerAdapter pagerAdapter = new DashboardPagerAdapter(getSupportFragmentManager(), mBinding.dashTabLayout.getTabCount());
        mBinding.dashViewPager.setAdapter(pagerAdapter);

        //set initial icon tint color as selected
        mBinding.dashTabLayout.getTabAt(0).getIcon().setColorFilter(getColor(R.color.tabSelectedColor), PorterDuff.Mode.SRC_IN);

        mBinding.dashViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mBinding.dashTabLayout));

        mBinding.dashTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mBinding.dashViewPager.setCurrentItem(tab.getPosition());

                TabLayout tabLayout = mBinding.dashTabLayout;
                switch (tab.getPosition()) {

                    case 0:
                        tab.getIcon().setColorFilter(getColor(R.color.tabSelectedColor), PorterDuff.Mode.SRC_IN);
                        tabLayout.getTabAt(1).getIcon().setColorFilter(getColor(R.color.tabUnselectedColor), PorterDuff.Mode.SRC_IN);
                        tabLayout.getTabAt(2).getIcon().setColorFilter(getColor(R.color.tabUnselectedColor), PorterDuff.Mode.SRC_IN);
                        break;
                    case 1:
                        tabLayout.getTabAt(0).getIcon().setColorFilter(getColor(R.color.tabUnselectedColor), PorterDuff.Mode.SRC_IN);
                        tab.getIcon().setColorFilter(getColor(R.color.tabSelectedColor), PorterDuff.Mode.SRC_IN);
                        tabLayout.getTabAt(2).getIcon().setColorFilter(getColor(R.color.tabUnselectedColor), PorterDuff.Mode.SRC_IN);
                        break;
                    case 2:
                        tabLayout.getTabAt(0).getIcon().setColorFilter(getColor(R.color.tabUnselectedColor), PorterDuff.Mode.SRC_IN);
                        tabLayout.getTabAt(1).getIcon().setColorFilter(getColor(R.color.tabUnselectedColor), PorterDuff.Mode.SRC_IN);
                        tab.getIcon().setColorFilter(getColor(R.color.tabSelectedColor), PorterDuff.Mode.SRC_IN);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dashboard_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_profile:
                startActivity(new Intent(DashboardActivity.this, ProfileActivity.class));
                return true;
            case R.id.menu_settings:
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
