package com.example.hanshinchat1.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;

import com.example.hanshinchat1.MatchFragment;
import com.example.hanshinchat1.R;
import com.example.hanshinchat1.SetIdeal2Activity;
import com.example.hanshinchat1.UserInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IdealAddressFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IdealAddressFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String TAG = "IdealAddressFragment";

    // TODO: Rename and change types of parameters
    public String address="왜안댐ㅁ";

    private String areas[];
    private String cities[];
    private String selectedArea;
    private String selectedCity;
    private Map<String, String[]> areaToCityMap;


    //NumberPicker firstAddressNumberPicker, secondAddressNumberPicker;
    NumberPicker areaNumberPicker, cityNumberPicker;

    public IdealAddressFragment() {
        // Required empty public constructor


    }

    public static IdealAddressFragment newInstance() {
        IdealAddressFragment fragment = new IdealAddressFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {


            address="null은아님"   ;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ideal_address, container, false);
        areaNumberPicker = view.findViewById(R.id.area_number_picker);
        cityNumberPicker = view.findViewById(R.id.city_number_picker);


        initializeNumberPicker();
        initializeListener();

        return view;
    }

    public void initializeNumberPicker() {
        areaToCityMap=new HashMap<>();

        areas = getResources().getStringArray(R.array.도);   //일단 지역배열 모두 가져오기
        areaNumberPicker.setWrapSelectorWheel(false);        //스크롤 0, max 넘어가지않게(취향차이)
        areaNumberPicker.setMaxValue(0);
        areaNumberPicker.setMaxValue(areas.length - 1);
        areaNumberPicker.setDisplayedValues(areas);

        //지역별 도시를 맵으로 저장
        for (String area : areas) {
            String cities[] = getResources().getStringArray(getResources().getIdentifier(area
                    , "array", getContext().getPackageName()));

            areaToCityMap.put(area, cities);
        }

        //이벤트가 없을때 첫 도시 numberpicker지정
        cities = areaToCityMap.get(areas[0]);
        cityNumberPicker.setWrapSelectorWheel(false);
        cityNumberPicker.setMinValue(0);
        cityNumberPicker.setMaxValue(cities.length - 1);
        cityNumberPicker.setDisplayedValues(cities);

        selectedArea=areas[0];
        selectedCity=cities[0];
        address = selectedArea +" "+ selectedCity;
        sendValueToActivity(address);
        Log.d(TAG, "주소는 "+address);


    }
    private void initializeListener() {
        //지역 numberpicker 이벤트발생 -> "지역" 별로 다른 "시" numberpicker 적용
        areaNumberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                cities=areaToCityMap.get(areas[newVal]);
                cityNumberPicker.setDisplayedValues(null);
                cityNumberPicker.setMinValue(0);
                cityNumberPicker.setMaxValue(cities.length - 1);
                cityNumberPicker.setValue(0);                         //항상 초기값을 0으로 설정
                cityNumberPicker.setDisplayedValues(cities);

                selectedArea=areas[newVal];
                selectedCity=cities[0];              // 첫 "시"는 index0 으로 설정
                address=selectedArea+" "+selectedCity;
                Log.d(TAG, "주소는 "+address);
                sendValueToActivity(address);


            }
        });

        //도시 numberpicker 이벤트 발생
        cityNumberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                selectedCity=cities[newVal];
                address=selectedArea+" "+selectedCity;
                Log.d(TAG, "주소는 "+address);
                sendValueToActivity(address);
            }
        });



    }
    private void sendValueToActivity(Object value) {
        if (getActivity() != null && getActivity() instanceof SetIdeal2Activity) {
            SetIdeal2Activity activity = (SetIdeal2Activity) getActivity();
            activity.onValueReceived(value); // 액티비티의 메서드를 호출하여 값을 전달
        }
    }

}






    /*private void setCityNumberPicker(int position) {
        if (position >= 0 && position < areaNumberPicker.getDisplayedValues().length) {
            // 선택된 "도"에 따라 "시" 배열 가져오기
            String[] cities = getResources().getStringArray(getResources().getIdentifier(areaNumberPicker
                    .getDisplayedValues()[position], "array", getContext().getPackageName()));

            cityNumberPicker.setWrapSelectorWheel(false);
            cityNumberPicker.setDisplayedValues(null);
            cityNumberPicker.setMinValue(0);
            cityNumberPicker.setMaxValue(cities.length - 1);
            cityNumberPicker.setDisplayedValues(cities);
            address= areaNumberPicker.getDisplayedValues()[position] + " "
                    +cities[areaNumberPicker.getValue()];
            Log.d(TAG, "주소는 " + address);

        }

        cityNumberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                address = areaNumberPicker.getDisplayedValues()[position] + " "
                        +cityNumberPicker.getDisplayedValues()[newVal];
                Log.d(TAG, "주소는 " + address);
            }
        });

    }*/



    /*
    private void setupNumberPickers() {
        String[] firstAddressOptions = getResources().getStringArray(R.array.도);
        firstAddressNumberPicker.setMinValue(0);
        firstAddressNumberPicker.setMaxValue(firstAddressOptions.length - 1);
        firstAddressNumberPicker.setDisplayedValues(firstAddressOptions);

        //최초 보이는거
        String[] initialSecondAddressOptions = secondAddressOptionsMap.get(firstAddressOptions[0]);
        secondAddressNumberPicker.setMinValue(0);
        if (initialSecondAddressOptions != null) {
            secondAddressNumberPicker.setMaxValue(initialSecondAddressOptions.length - 1);
            secondAddressNumberPicker.setDisplayedValues(initialSecondAddressOptions);
        }
        //firstAddressNumberPicker.setWrapSelectorWheel(false);

        //스크롤움직일때
        firstAddressNumberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
             *//*   String selectedFirstAddress = firstAddressOptions[newVal];  // 도 배열에서 선택한것

                //선택된 도에서의 시 배열 초기화
                String[] secondAddressOptions = secondAddressOptionsMap.get(selectedFirstAddress);
                if (secondAddressOptions != null) {
                    Log.d(TAG, "onValueChange: "+secondAddressOptions.length);
                    secondAddressNumberPicker.setMinValue(0);
                    secondAddressNumberPicker.setMaxValue(secondAddressOptions.length - 1);
                    secondAddressNumberPicker.setDisplayedValues(secondAddressOptions);
                    choice_first = selectedFirstAddress;
                }
                *//*

                String[] displayedValues = firstAddressNumberPicker.getDisplayedValues();
                if (newVal >= 0 && newVal < displayedValues.length) {
                    String[] secondAddressOptions = secondAddressOptionsMap.get(displayedValues[newVal]);
                    if (secondAddressOptions != null) {
                        Log.d(TAG, "onValueChange: "+secondAddressOptions.length);
                        secondAddressNumberPicker.setMinValue(0);
                        secondAddressNumberPicker.setMaxValue(secondAddressOptions.length - 1);
                        secondAddressNumberPicker.setDisplayedValues(secondAddressOptions);
                        choice_first = displayedValues[newVal];
                    }

                    //choice_first = displayedValues[newVal];
                }


            }
        });
        secondAddressNumberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                String[] displayedValues = secondAddressNumberPicker.getDisplayedValues();
                if (newVal >= 0 && newVal < displayedValues.length) {
                    choice_second = displayedValues[newVal];
                }
            }
        });
    }

    private void initializeSecondAddressOptionsMap() {
        String[] firstAddresses = getResources().getStringArray(R.array.도);

        for (String firstAddress : firstAddresses) {
            int resourceId = getResources().getIdentifier(firstAddress, "array", getContext().getPackageName());
            if (resourceId != 0) {
                String[] secondAddressOptions = getResources().getStringArray(resourceId);
                secondAddressOptionsMap.put(firstAddress, secondAddressOptions);
                Log.d(TAG, "각 도별 시 : "+secondAddressOptions.length);
            } else {
                secondAddressOptionsMap.put(firstAddress, new String[0]);
            }
        }
    }*/






