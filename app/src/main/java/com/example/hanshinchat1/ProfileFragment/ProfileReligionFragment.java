package com.example.hanshinchat1.ProfileFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.hanshinchat1.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileReligionFragment extends Fragment {

    DatabaseReference myRef;
    FirebaseUser user;

    private RadioGroup radioGroup;
    private RadioButton radioButton1, radioButton2, radioButton3, radioButton4, radioButton5;

    private RadioButton selectedRadioButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_religion_fragment, container, false);

        radioGroup = view.findViewById(R.id.religion_radio_group_fragment);
        radioButton1 = view.findViewById(R.id.religion_radio_btn_1_fragment);
        radioButton2 = view.findViewById(R.id.religion_radio_btn_2_fragment);
        radioButton3 = view.findViewById(R.id.religion_radio_btn_3_fragment);
        radioButton4 = view.findViewById(R.id.religion_radio_btn_4_fragment);
        radioButton5 = view.findViewById(R.id.religion_radio_btn_5_fragment);

        myRef = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();

        radioButton1.setChecked(true);
        selectedRadioButton = radioButton1;

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                selectedRadioButton = view.findViewById(selectedRadioButtonId);
            }
        });

        View.OnClickListener radioButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioButton1.setBackgroundResource(R.drawable.radio_button_unchecked);
                radioButton2.setBackgroundResource(R.drawable.radio_button_unchecked);
                radioButton3.setBackgroundResource(R.drawable.radio_button_unchecked);
                radioButton4.setBackgroundResource(R.drawable.radio_button_unchecked);
                radioButton5.setBackgroundResource(R.drawable.radio_button_unchecked);

                RadioButton clickedRadioButton = (RadioButton) v;
                clickedRadioButton.setBackgroundResource(R.drawable.radio_button_checked);
                selectedRadioButton = clickedRadioButton;
            }
        };
        radioButton1.setOnClickListener(radioButtonClickListener);
        radioButton2.setOnClickListener(radioButtonClickListener);
        radioButton3.setOnClickListener(radioButtonClickListener);
        radioButton4.setOnClickListener(radioButtonClickListener);
        radioButton5.setOnClickListener(radioButtonClickListener);

        return view;
    }

    public void updateDB() {
        DatabaseReference userRef = myRef.child("users").child(user.getUid());
        String selectedReligion = selectedRadioButton.getText().toString();
        userRef.child("religion").setValue(selectedReligion);
    }

    public String editDB() {
        String newReligion = selectedRadioButton.getText().toString();
        return newReligion;
    }
}
