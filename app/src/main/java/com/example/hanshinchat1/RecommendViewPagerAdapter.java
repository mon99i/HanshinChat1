package com.example.hanshinchat1;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.hanshinchat1.fragment.ShowUserFragment1;
import com.example.hanshinchat1.fragment.ShowUserFragment2;

import java.util.ArrayList;

public class RecommendViewPagerAdapter extends FragmentStateAdapter {
    public ArrayList<Fragment> fragments = new ArrayList<>();

    public RecommendViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, UserInfo userInfo, boolean isRoom) {
        super(fragmentActivity);
        fragments.add(new ShowUserFragment1(userInfo, isRoom));
        fragments.add(new ShowUserFragment2(userInfo));

    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }
}
