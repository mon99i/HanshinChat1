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

public class ProfileHeightFragment extends Fragment {

    private static final String TAG="height";

    private String selectedHeight;
    private String[] height;
    private NumberPicker numberPicker;

    DatabaseReference myRef;
    FirebaseUser user;

    public ProfileHeightFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_height_fragment, container, false);

        numberPicker = view.findViewById(R.id.height_number_picker_fragment);

        height = getResources().getStringArray(R.array.키);
        selectedHeight=height[0];
        Log.d(TAG, "onCreate: "+selectedHeight);

        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(height.length-1);
        numberPicker.setValue(20);

        numberPicker.setDisplayedValues(height);
        myRef = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();

        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldVal, int newVal) {
                selectedHeight = height[newVal];
            }
        });
        return view;
    }
    public void updateDB() {
        DatabaseReference userRef = myRef.child("users").child(user.getUid());
        int heightValue = Integer.parseInt(selectedHeight);
        userRef.child("height").setValue(heightValue);
    }

    public String editDB() {
        int heightValue = Integer.parseInt(selectedHeight);
        String newHeight = String.valueOf(heightValue);
        return newHeight;
    }
}
