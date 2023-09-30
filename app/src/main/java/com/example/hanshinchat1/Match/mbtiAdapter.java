package com.example.hanshinchat1.Match;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.hanshinchat1.R;
import com.example.hanshinchat1.UserInfo;
import com.example.hanshinchat1.board.ListViewItem;

import java.util.ArrayList;
import java.util.List;

public class mbtiAdapter  {

    private LayoutInflater layoutInflater;
    private Context context;
    //    private ArrayList<mbtiModel> mbtiList;
    private ArrayList<?> matchingUsers;
    private ArrayList<mbtiModel> mbtiDataList;
//    private List<Fragment> fragments;



//    public mbtiAdapter(Fragment fragment, List<Fragment> fragments) {
//        super(fragment);
//        this.fragments = fragments;
//    }

    public mbtiAdapter(Fragment fragment, ArrayList<mbtiModel> mbtiDataList) {
//        super(fragment);
        this.mbtiDataList = mbtiDataList;
    }
//    public mbtiAdapter(List<mbtiModel> mbtiDataList) {
//        this.mbtiDataList = mbtiDataList;
//    }


//    @NonNull  잠깐
//    @Override
//    public Fragment createFragment(int position) {
//
//        return fragments.get(position);
//    }


//    @NonNull
//    @Override
//    public Fragment getItem(int position) {
//        mbtiModel data = mbtiDataList.get(position);
//        return mbtiFragment.newInstance(String.valueOf(data.getAge()), data.getDepartment(), data.getMbti());
//    }
//
//    @Override
//    public int getItemCount() {
//
//        return mbtiDataList.size();
////        return fragments.size();
//    }
}

//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        TextView age;
//        TextView department;
//        TextView mbti;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//            age = itemView.findViewById(R.id.ageArea);
//            department = itemView.findViewById(R.id.departmentArea);
//            mbti = itemView.findViewById(R.id.mbtiArea);
//        }
//    }


