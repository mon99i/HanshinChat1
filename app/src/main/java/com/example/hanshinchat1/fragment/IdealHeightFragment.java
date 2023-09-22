package com.example.hanshinchat1.fragment;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hanshinchat1.R;
import com.example.hanshinchat1.SetIdeal2Activity;
import com.google.android.material.slider.LabelFormatter;
import com.google.android.material.slider.RangeSlider;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IdealHeightFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IdealHeightFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters

    private static final String TAG = "heigtFragment";
    private String mParam1;
    private String mParam2;


    ArrayList<Integer> selectedHeights;
    RangeSlider rangeSlider;

    public IdealHeightFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IdealHeightFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IdealHeightFragment newInstance(String param1, String param2) {
        IdealHeightFragment fragment = new IdealHeightFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_ideal_height, container, false);
        rangeSlider=view.findViewById(R.id.heightRangeSlider);
        selectedHeights=new ArrayList<>();

        initializeView();

        initializeListener();

        // Inflate the layout for this fragment
        return view;

    }

    private void initializeView(){
        //최초 Height 설정
        rangeSlider.setLabelBehavior(LabelFormatter.LABEL_VISIBLE);
        rangeSlider.setMinSeparationValue(5f);
        rangeSlider.setStepSize(5f);
        rangeSlider.setValues(160f,180f);
        rangeSlider.setTrackActiveTintList(ColorStateList.valueOf(Color.parseColor("#BEB6F2")));
        rangeSlider.setThumbTintList(ColorStateList.valueOf(Color.parseColor("#BEB6F2")));
        rangeSlider.setThumbStrokeColor(ColorStateList.valueOf(Color.parseColor("#BEB6F2")));
        rangeSlider.setTrackInactiveTintList(ColorStateList.valueOf(Color.parseColor("#E6E6FA")));

        float minValueIndex = rangeSlider.getValues().get(0);// 인덱스는 0부터 시작하므로 1을 뺍니다.
        float maxValueIndex=rangeSlider.getValues().get(1)+1;
        int min=(int) minValueIndex;
        int max=(int) maxValueIndex;

        for(int i=min;i<max;i++){
            selectedHeights.add(i);
        }

        sendValueToActivity(selectedHeights);
    }



    private void initializeListener(){
        rangeSlider.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                selectedHeights.clear();
                float minValueIndex = slider.getValues().get(0);// 인덱스는 0부터 시작하므로 1을 뺍니다.
                float maxValueIndex=slider.getValues().get(1)+1;
                int min=(int) minValueIndex;
                int max=(int) maxValueIndex;

                for(int i=min;i<max;i++){
                    selectedHeights.add(i);
                }
                Log.d(TAG, "onValueChange: "+selectedHeights);
                sendValueToActivity(selectedHeights);
            }
        });

    }

    private void sendValueToActivity(Object object){
        if (getActivity() != null && getActivity() instanceof SetIdeal2Activity) {
            SetIdeal2Activity activity = (SetIdeal2Activity) getActivity();
            activity.onValueReceived(object); // 액티비티의 메서드를 호출하여 값을 전달
        }

    }
}