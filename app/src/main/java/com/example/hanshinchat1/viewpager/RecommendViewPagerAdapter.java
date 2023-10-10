package com.example.hanshinchat1.viewpager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.hanshinchat1.UserInfo;
import com.example.hanshinchat1.fragment.RecommendUserFragment1;
import com.example.hanshinchat1.fragment.RecommendUserFragment2;

import java.util.ArrayList;

public class RecommendViewPagerAdapter extends FragmentStateAdapter {
    UserInfo userInfo;
    ArrayList<Fragment> fragments=new ArrayList<>();
    public RecommendViewPagerAdapter(@NonNull Fragment fragment, UserInfo userInfo) {
        super(fragment);
        this.userInfo=userInfo;
        fragments.add(new RecommendUserFragment1(userInfo));
        fragments.add(new RecommendUserFragment2(userInfo));

    }

    public RecommendViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, UserInfo userInfo) {
        super(fragmentActivity);
        this.userInfo=userInfo;
        fragments.add(new RecommendUserFragment1(userInfo));
        fragments.add(new RecommendUserFragment2(userInfo));
    }

    public RecommendViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
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
