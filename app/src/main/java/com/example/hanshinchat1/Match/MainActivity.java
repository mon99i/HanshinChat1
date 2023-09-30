package com.example.hanshinchat1.Match;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.hanshinchat1.R;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator3;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 mPager;
    private FragmentStateAdapter pagerAdapter;
    private int num_page;
    private CircleIndicator3 mIndicator;
    private MyAdapter adapter;
    private ArrayList<mbtiModel> mbtiList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ViewPager2
        mPager = findViewById(R.id.viewpager);
        //Adapter
        if(mbtiList != null) {
            pagerAdapter = new MyAdapter(this, mbtiList.size());
            Log.d("MyAdapter", "getItemCount: " + pagerAdapter.getItemCount());
        } else {

        }
        mPager.setAdapter(pagerAdapter);
        Log.d("MyAdapter", "Adapter set to ViewPager");
        //Indicator
        mIndicator = findViewById(R.id.indicator);
        mIndicator.setViewPager(mPager);
        mIndicator.createIndicators(num_page,0);
        //ViewPager Setting
        mPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);

        mbtiList = new ArrayList<>();
        adapter = new MyAdapter(this, mbtiList);

        mbtiList.clear();
        adapter.notifyDataSetChanged();

        mPager.setCurrentItem(1000); //시작 지점
//        mPager.setOffscreenPageLimit(4); //최대 이미지 수

        mPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                if (positionOffsetPixels == 0) {
                    mPager.setCurrentItem(position);
                }
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mIndicator.animatePageSelected(position%num_page);
            }
        });

//        mbtiList = new ArrayList<>();
//        adapter = new MyAdapter(this, mbtiList);

//        mbtiList.clear();

    }
}
