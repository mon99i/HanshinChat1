package com.example.hanshinchat1.Match;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class MyAdapter extends FragmentStateAdapter {

    private TextView age;
    private TextView department;
    private TextView mbti;
    private ArrayList<mbtiModel> mbtiList;
    public int mCount;

    public MyAdapter(FragmentActivity fa, int mCount) {
        super(fa);
        this.mCount = mCount;
    }
    public MyAdapter(FragmentActivity fa, ArrayList<mbtiModel> mbtiList) {
        super(fa);
        this.mbtiList = mbtiList;
        mCount = mbtiList.size();
    }
//    public MyAdapter(ArrayList<mbtiModel> mbtiList) {
//
//        this.mbtiList = mbtiList;
//    }



    @NonNull
    @Override
    public Fragment createFragment(int position) {
        int index = getRealPosition(position);

        if(index < mbtiList.size()) {
            mbtiModel data = mbtiList.get(index);
            Fragment fragment = new Fragment_2();

            // Fragment_2에 데이터를 전달하는 방식으로 수정
            Bundle bundle = new Bundle();
            bundle.putString("age", String.valueOf(data.getAge()));
            bundle.putString("department", data.getDepartment());
            bundle.putString("mbti", data.getMbti());
            fragment.setArguments(bundle);

            return fragment;
        }
//        if(index==0) return new Fragment_1();
//        else if(index==1) return new Fragment_2();
//        else if(index==2) return new Fragment_3();
//        else return new Fragment_4();
        return new Fragment();
    }

    @Override
    public int getItemCount() {
        return mbtiList.size();
    }

    public int getRealPosition(int position) { return position % mbtiList.size(); }

}