package com.example.hanshinchat1.simulation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.viewpager.widget.PagerAdapter;

import com.example.hanshinchat1.R;

public class SimulationGuidePagerAdapter extends PagerAdapter {

    private Context context;
    private int[] imageResIds = {R.drawable.simulation_guide1, R.drawable.simulation_guide2, R.drawable.simulation_guide3};

    public SimulationGuidePagerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return imageResIds.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_pager_item, container, false);
        ImageView imageView = view.findViewById(R.id.imageView);
        imageView.setImageResource(imageResIds[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
