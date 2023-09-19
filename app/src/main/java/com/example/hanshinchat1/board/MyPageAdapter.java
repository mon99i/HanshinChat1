package com.example.hanshinchat1.board;


import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MyPageAdapter extends FragmentPagerAdapter {

    int numTabs;

    public MyPageAdapter(@NonNull FragmentManager fm, int numTabs) {
        super(fm);
        this.numTabs = numTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                BoardFragment1 f1 = new BoardFragment1();
                return f1;
            case 1:
                BoardFragment2 f2 = new BoardFragment2();
                return f2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numTabs;
    }
}
