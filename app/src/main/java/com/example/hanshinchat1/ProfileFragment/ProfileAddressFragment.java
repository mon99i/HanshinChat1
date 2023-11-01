package com.example.hanshinchat1.ProfileFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.hanshinchat1.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Map;

public class ProfileAddressFragment extends Fragment {
    private static final String TAG = "SetProfileAddressActivity";
    private String address;
    private String areas[];
    private String cities[];
    private String selectedArea;
    private String selectedCity;
    private Map<String, String[]> areaToCityMap;
    private NumberPicker areaNumberPicker, cityNumberPicker;
    DatabaseReference myRef;
    FirebaseUser user;
    public ProfileAddressFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_address_fragment, container, false);

        myRef = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
        areaNumberPicker = view.findViewById(R.id.first_address_number_picker_fragment);
        cityNumberPicker = view.findViewById(R.id.second_address_number_picker_fragment);

        initializeNumberPicker();
        initializeListener();

        return view;
    }

    private void initializeNumberPicker() {
        areaToCityMap=new HashMap<>();
        areas = getResources().getStringArray(R.array.도);
        areaNumberPicker.setWrapSelectorWheel(false);
        areaNumberPicker.setMaxValue(0);
        areaNumberPicker.setMaxValue(areas.length - 1);
        areaNumberPicker.setDisplayedValues(areas);

        for (String area : areas) {
            int resourceId = getResources().getIdentifier(area, "array", getActivity().getPackageName());
            if (resourceId != 0) {
                String[] cities = getResources().getStringArray(resourceId);
                areaToCityMap.put(area, cities);
            } else {
                Log.e(TAG, "Resource not found for area: " + area);
            }
        }
        cities = areaToCityMap.get(areas[0]);
        cityNumberPicker.setWrapSelectorWheel(false);
        cityNumberPicker.setMinValue(0);
        cityNumberPicker.setMaxValue(cities.length - 1);
        cityNumberPicker.setDisplayedValues(cities);

        selectedArea=areas[0];
        selectedCity=cities[0];
        address = selectedArea +" "+ selectedCity;
        Log.d(TAG, "주소는 "+address);

    }

    private void initializeListener() {
        areaNumberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                cities=areaToCityMap.get(areas[newVal]);
                cityNumberPicker.setDisplayedValues(null);
                cityNumberPicker.setMinValue(0);
                cityNumberPicker.setMaxValue(cities.length - 1);
                cityNumberPicker.setValue(0);
                cityNumberPicker.setDisplayedValues(cities);

                selectedArea=areas[newVal];
                selectedCity=cities[0];
                address=selectedArea+" "+selectedCity;
                Log.d(TAG, "주소는 "+address);
            }
        });

        cityNumberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                selectedCity=cities[newVal];
                address=selectedArea+" "+selectedCity;
                Log.d(TAG, "주소는 "+address);
            }
        });
    }

    public void updateDB() {
        DatabaseReference userRef = myRef.child("users").child(user.getUid());
        userRef.child("address").setValue(address);
    }

    public String editDB() {
        String newAddress = selectedArea+" "+selectedCity;
        return newAddress;
    }
}
