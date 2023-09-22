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
 * Use the {@link IdealAgeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IdealAgeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static final String TAG = "ageFragment";

    ArrayList<Integer> selectedValues;
    RangeSlider rangeSlider;


    public IdealAgeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IdealAgeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IdealAgeFragment newInstance(String param1, String param2) {
        IdealAgeFragment fragment = new IdealAgeFragment();
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
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_ideal_age, container, false);
        rangeSlider=view.findViewById(R.id.ageRangeSlider);
        selectedValues=new ArrayList<>();

        initializeView();

        initializeListener();

        // Inflate the layout for this fragment
        return view;
    }



    private void initializeView(){
        //최초 Height 설정
        rangeSlider.setLabelBehavior(LabelFormatter.LABEL_VISIBLE);
        rangeSlider.setMinSeparationValue( 1f);
        rangeSlider.setStepSize(1f);
        rangeSlider.setValues(23f,27f);
        rangeSlider.setTrackActiveTintList(ColorStateList.valueOf(Color.parseColor("#BEB6F2")));
        rangeSlider.setThumbTintList(ColorStateList.valueOf(Color.parseColor("#BEB6F2")));
        rangeSlider.setThumbStrokeColor(ColorStateList.valueOf(Color.parseColor("#BEB6F2")));
        rangeSlider.setTrackInactiveTintList(ColorStateList.valueOf(Color.parseColor("#E6E6FA")));

        float minValueIndex = rangeSlider.getValues().get(0);// 인덱스는 0부터 시작하므로 1을 뺍니다.
        float maxValueIndex=rangeSlider.getValues().get(1)+1;
        int min=(int) minValueIndex;
        int max=(int) maxValueIndex;

        for(int i=min;i<max;i++){
            selectedValues.add(i);
        }

        sendValueToActivity(selectedValues);
    }



    private void initializeListener(){
        rangeSlider.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                selectedValues.clear();
                float minValueIndex = slider.getValues().get(0);// 인덱스는 0부터 시작하므로 1을 뺍니다.
                float maxValueIndex=slider.getValues().get(1)+1;
                int min=(int) minValueIndex;
                int max=(int) maxValueIndex;

                for(int i=min;i<max;i++){
                    selectedValues.add(i);
                }
                Log.d(TAG, "onValueChange: "+selectedValues);
                sendValueToActivity(selectedValues);
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