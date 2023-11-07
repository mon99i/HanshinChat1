package com.example.hanshinchat1;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import androidx.viewpager.widget.ViewPager;

public class QuestionImageViewerDialog extends Dialog {

    private Context context;
    private int[] imageResIds = {R.drawable.question_1, R.drawable.question_2, R.drawable.question_3};
    private ViewPager viewPager;
    private LinearLayout indicatorLayout;
    private View[] indicators;

    public QuestionImageViewerDialog(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_question_viewer);

        viewPager = findViewById(R.id.viewPager);
        QuestionImagePagerAdapter adapter = new QuestionImagePagerAdapter(context);
        viewPager.setAdapter(adapter);
        indicatorLayout = findViewById(R.id.indicatorLayout);
        indicatorLayout.removeAllViews();
        setupIndicators();

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                updateIndicators(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void setupIndicators() {
        indicators = new View[3];
        for (int i = 0; i < 3; i++) {
            indicators[i] = new View(context);
            int size = 20;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);
            params.setMargins(5, 0, 5, 0); // 점 사이의 간격 조절
            indicators[i].setLayoutParams(params);
            indicators[i].setBackgroundResource(R.drawable.question_indicator_unselected);
            indicatorLayout.addView(indicators[i]);
        }
        indicators[0].setBackgroundResource(R.drawable.question_indicator_selected);
    }

    private void updateIndicators(int position) {
        for (int i = 0; i < indicators.length; i++) {
            if (i == position) {
                indicators[i].setBackgroundResource(R.drawable.question_indicator_selected);
            } else {
                indicators[i].setBackgroundResource(R.drawable.question_indicator_unselected);
            }
        }
    }


}
