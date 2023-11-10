package com.example.hanshinchat1.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;

import com.example.hanshinchat1.R;
import com.example.hanshinchat1.SetIdeal2Activity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IdealReligionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IdealReligionFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RadioGroup radioGroup;
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private RadioButton radioButton3;
    private RadioButton radioButton4;
    private RadioButton radioButton5;

    private String selectedValue;
    private RadioButton selectedRadioButton;

    public IdealReligionFragment() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IdealReligionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IdealReligionFragment newInstance(String param1, String param2) {
        IdealReligionFragment fragment = new IdealReligionFragment();
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
        View view = inflater.inflate(R.layout.fragment_ideal_religion, container, false);
        radioGroup = view.findViewById(R.id.fragment_religion_radio_group);
        radioButton1 = view.findViewById(R.id.fragment_religion_radio_btn_1);
        radioButton2 = view.findViewById(R.id.fragment_religion_radio_btn_2);
        radioButton3 = view.findViewById(R.id.fragment_religion_radio_btn_3);
        radioButton4 = view.findViewById(R.id.fragment_religion_radio_btn_4);
        radioButton5 = view.findViewById(R.id.fragment_religion_radio_btn_5);

        initializeListener();
        return view;
    }


    private void initializeListener() {
        radioButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedRadioButton = radioButton1;
                radioButton1.setBackgroundResource(R.drawable.radio_button_checked);
                radioButton2.setBackgroundResource(R.drawable.radio_button_unchecked);
                radioButton3.setBackgroundResource(R.drawable.radio_button_unchecked);
                radioButton4.setBackgroundResource(R.drawable.radio_button_unchecked);
                selectedValue = radioButton1.getText().toString();
                sendValueToActivity(selectedValue);
            }
        });

        radioButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedRadioButton = radioButton2;
                radioButton1.setBackgroundResource(R.drawable.radio_button_unchecked);
                radioButton2.setBackgroundResource(R.drawable.radio_button_checked);
                radioButton3.setBackgroundResource(R.drawable.radio_button_unchecked);
                radioButton4.setBackgroundResource(R.drawable.radio_button_unchecked);
                selectedValue = radioButton2.getText().toString();
                sendValueToActivity(selectedValue);
            }
        });

        radioButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedRadioButton = radioButton3;
                radioButton1.setBackgroundResource(R.drawable.radio_button_unchecked);
                radioButton2.setBackgroundResource(R.drawable.radio_button_unchecked);
                radioButton3.setBackgroundResource(R.drawable.radio_button_checked);
                radioButton4.setBackgroundResource(R.drawable.radio_button_unchecked);
                selectedValue = radioButton3.getText().toString();
                sendValueToActivity(selectedValue);
            }
        });

        radioButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedRadioButton = radioButton4;
                radioButton1.setBackgroundResource(R.drawable.radio_button_unchecked);
                radioButton2.setBackgroundResource(R.drawable.radio_button_unchecked);
                radioButton3.setBackgroundResource(R.drawable.radio_button_unchecked);
                radioButton4.setBackgroundResource(R.drawable.radio_button_checked);
                selectedValue = radioButton4.getText().toString();
                sendValueToActivity(selectedValue);
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