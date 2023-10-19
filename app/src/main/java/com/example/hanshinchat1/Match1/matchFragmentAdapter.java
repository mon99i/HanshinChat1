package com.example.hanshinchat1.Match1;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class matchFragmentAdapter extends FragmentStateAdapter {

    public int mCount;


    public matchFragmentAdapter(FragmentActivity fa, int count) {
        super(fa);
        mCount = count;
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        int index = getRealPosition(position);
        if(index==0) return new matchFragment1();
        else if(index==1) return new matchFragment2();
        else return new matchFragment2();

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public int getRealPosition(int position) { return position % mCount; }
}

