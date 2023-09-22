package com.example.hanshinchat1;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import java.util.HashMap;
import java.util.Map;

public class SetProfile10AddressActivity extends MainActivity {

    private static final String TAG = "SetProfileAddressActivity";
    ProgressBar addressProgress;
    private String address;

    private String areas[];
    private String cities[];
    private String selectedArea;
    private String selectedCity;
    private Map<String, String[]> areaToCityMap;
    private NumberPicker areaNumberPicker, cityNumberPicker;

    private Button nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_profile_10_address);
        UserInfo userInfo=(UserInfo) getIntent().getSerializableExtra("UserInfo");

        nextBtn = findViewById(R.id.set_address_next);
        addressProgress = findViewById(R.id.address_progress);

        areaNumberPicker = findViewById(R.id.first_address_number_picker);
        cityNumberPicker = findViewById(R.id.second_address_number_picker);

        initializeNumberPicker();
        initializeListener();


      /*  firstAddressNumberPicker = findViewById(R.id.first_address_number_picker);
        secondAddressNumberPicker = findViewById(R.id.city_number_picker);

        secondAddressOptionsMap = new HashMap<>();
        initializeSecondAddressOptionsMap();

        setupNumberPickers();*/

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference usersRef = myRef.child("users").child(user.getUid());

                if (!address.isEmpty()) {
                    try {
                        userInfo.setAddress(address);
                        usersRef.setValue(userInfo);

                        Intent intent = new Intent(getApplicationContext(), SetProfile11ReligionActivity.class);
                        intent.putExtra("UserInfo",userInfo);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                    } catch (NumberFormatException e) {
                        Toast.makeText(getApplicationContext(), "올바른 주소를 선택해주세요", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "주소를 선택해주세요", Toast.LENGTH_SHORT).show();
                }

                /*usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            UserInfo userInfo = snapshot.getValue(UserInfo.class);

                            if (!address.isEmpty()) {
                                try {
                                    userInfo.setAddress(address);
                                    usersRef.setValue(userInfo);

                                    Intent intent = new Intent(getApplicationContext(), SetProfile11ReligionActivity.class);
                                    startActivity(intent);
                                    finish();
                                    overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                                } catch (NumberFormatException e) {
                                    Toast.makeText(getApplicationContext(), "올바른 주소를 선택해주세요", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "주소를 선택해주세요", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "오류 발생", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getApplicationContext(), "프로필 저장 실패", Toast.LENGTH_SHORT).show();
                    }
                });*/
            }
        });
    }

    private void initializeNumberPicker() {
        areaToCityMap=new HashMap<>();

        areas = getResources().getStringArray(R.array.도);   //일단 지역배열 모두 가져오기
        areaNumberPicker.setWrapSelectorWheel(false);        //스크롤 0, max 넘어가지않게(취향차이)
        areaNumberPicker.setMaxValue(0);
        areaNumberPicker.setMaxValue(areas.length - 1);
        areaNumberPicker.setDisplayedValues(areas);

        //지역별 도시를 맵으로 저장
        for (String area : areas) {
            String cities[] = getResources().getStringArray(getResources().getIdentifier(area
                    , "array", getPackageName()));

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

            }
        });

        //도시 numberpicker 이벤트 발생
        cityNumberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                selectedCity=cities[newVal];
                address=selectedArea+" "+selectedCity;
                Log.d(TAG, "주소는 "+address);

            }
        });

    }


   /* private void setupNumberPickers() {
        String[] firstAddressOptions = getResources().getStringArray(R.array.도);
        firstAddressNumberPicker.setMinValue(0);
        firstAddressNumberPicker.setMaxValue(firstAddressOptions.length - 1);
        firstAddressNumberPicker.setDisplayedValues(firstAddressOptions);

        String[] initialSecondAddressOptions = secondAddressOptionsMap.get(firstAddressOptions[0]);
        secondAddressNumberPicker.setMinValue(0);
        if (initialSecondAddressOptions != null) {
            secondAddressNumberPicker.setMaxValue(initialSecondAddressOptions.length - 1);
            secondAddressNumberPicker.setDisplayedValues(initialSecondAddressOptions);
        }

        firstAddressNumberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                String selectedFirstAddress = firstAddressOptions[newVal];
                String[] secondAddressOptions = secondAddressOptionsMap.get(selectedFirstAddress);
                if (secondAddressOptions != null) {
                    secondAddressNumberPicker.setDisplayedValues(secondAddressOptions);
                    secondAddressNumberPicker.setMinValue(0);
                    secondAddressNumberPicker.setMaxValue(secondAddressOptions.length - 1);
                    choice_first = selectedFirstAddress;
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
            int resourceId = getResources().getIdentifier(firstAddress, "array", getPackageName());
            if (resourceId != 0) {
                String[] secondAddressOptions = getResources().getStringArray(resourceId);
                secondAddressOptionsMap.put(firstAddress, secondAddressOptions);
            } else {
                secondAddressOptionsMap.put(firstAddress, new String[0]);
            }
        }
    }*/

    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), SetProfile9FormActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}
