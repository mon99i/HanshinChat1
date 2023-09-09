package com.example.hanshinchat1;


import android.content.Intent;
import android.os.Bundle;
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

    NumberPicker firstAddressNumberPicker, secondAddressNumberPicker;
    ProgressBar addressProgress;
    String choice_first = "";
    String choice_second = "";
    private Map<String, String[]> secondAddressOptionsMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_profile_10_address);

        Button nextBtn = findViewById(R.id.set_address_next);
        addressProgress = findViewById(R.id.address_progress);

        firstAddressNumberPicker = findViewById(R.id.first_address_number_picker);
        secondAddressNumberPicker = findViewById(R.id.second_address_number_picker);

        secondAddressOptionsMap = new HashMap<>();
        initializeSecondAddressOptionsMap();

        setupNumberPickers();

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference usersRef = myRef.child("users").child(user.getUid());
                usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            UserInfo userInfo = snapshot.getValue(UserInfo.class);
                            String strAddress = choice_first + " " + choice_second;
                            if (!strAddress.isEmpty()) {
                                try {
                                    userInfo.setAddress(strAddress);
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
                });
            }
        });
    }

    private void setupNumberPickers() {
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
    }

    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), SetProfile9FormActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}
