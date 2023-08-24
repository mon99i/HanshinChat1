package com.example.hanshinchat1;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class MatchPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<String> requestedUidList;
    private ArrayList<UserInfo> userInfoList;
    private ArrayList<String> matchRoomKeyList;
    private ArrayList<MatchRoom> matchRoomList;

    public MatchPagerAdapter(FragmentManager fm, ArrayList<String> matchRoomKeyList,ArrayList<UserInfo> userInfoList) {
        super(fm);
        this.matchRoomKeyList=matchRoomKeyList;
        this.userInfoList = userInfoList;
    }

    @Override
    public Fragment getItem(int position) {
        // 각 페이지에 해당하는 프래그먼트를 생성하고 데이터를 전달
        return MatchFragment.newInstance(userInfoList.get(position));
    }

    @Override
    public int getCount() {
        // 페이지 수는 데이터의 크기와 같음
        return userInfoList.size();
    }
}
