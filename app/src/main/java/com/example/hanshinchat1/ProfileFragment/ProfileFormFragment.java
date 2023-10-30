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

public class ProfileFormFragment extends Fragment {

    private RadioGroup radioGroup;
    private RadioButton radioButton1, radioButton2, radioButton3, radioButton4;
    private RadioButton selectedRadioButton;

    DatabaseReference myRef;
    FirebaseUser user;
    public ProfileFormFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_form_fragment, container, false);

        radioGroup = view.findViewById(R.id.form_radio_group_fragment);

        radioButton1 = view.findViewById(R.id.form_radio_btn_1_fragment);
        radioButton2 = view.findViewById(R.id.form_radio_btn_2_fragment);
        radioButton3 = view.findViewById(R.id.form_radio_btn_3_fragment);
        radioButton4 = view.findViewById(R.id.form_radio_btn_4_fragment);
        myRef = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();

        radioButton1.setChecked(true);
        selectedRadioButton = radioButton1;

        View.OnClickListener radioButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioButton1.setBackgroundResource(R.drawable.radio_button_unchecked);
                radioButton2.setBackgroundResource(R.drawable.radio_button_unchecked);
                radioButton3.setBackgroundResource(R.drawable.radio_button_unchecked);
                radioButton4.setBackgroundResource(R.drawable.radio_button_unchecked);

                RadioButton clickedRadioButton = (RadioButton) v;
                clickedRadioButton.setBackgroundResource(R.drawable.radio_button_checked);
                selectedRadioButton = clickedRadioButton;
            }
        };
        radioButton1.setOnClickListener(radioButtonClickListener);
        radioButton2.setOnClickListener(radioButtonClickListener);
        radioButton3.setOnClickListener(radioButtonClickListener);
        radioButton4.setOnClickListener(radioButtonClickListener);
        return view;
    }
    public void updateDB() {
        DatabaseReference userRef = myRef.child("users").child(user.getUid());
        String selectedForm = selectedRadioButton.getText().toString();
        userRef.child("form").setValue(selectedForm);
    }

    public String editDB() {
        String selectedForm = selectedRadioButton.getText().toString();
        return selectedForm;
    }
}
