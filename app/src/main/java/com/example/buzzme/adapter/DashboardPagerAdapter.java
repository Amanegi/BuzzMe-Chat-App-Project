package com.example.buzzme.adapter;

import com.example.buzzme.fragments.RequestsFragment;
import com.example.buzzme.fragments.ChatFragment;
import com.example.buzzme.fragments.FriendsFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class DashboardPagerAdapter extends FragmentPagerAdapter {
    private int tabCount;

    public DashboardPagerAdapter(@NonNull FragmentManager fm, int tabCount) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.tabCount = tabCount;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ChatFragment();
            case 1:
                return new FriendsFragment();
            case 2:
                return new RequestsFragment();
            default:
                return new ChatFragment();
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
