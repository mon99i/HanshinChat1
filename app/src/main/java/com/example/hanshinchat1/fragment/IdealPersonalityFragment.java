package com.example.hanshinchat1.fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.hanshinchat1.R;
import com.example.hanshinchat1.SetIdeal2Activity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IdealPersonalityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IdealPersonalityFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String[] personalityArray;
    private ArrayList<String> selectedValues;
    private static final int MAX_PERSONALITY = 3;

    public IdealPersonalityFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IdealPersonalityFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IdealPersonalityFragment newInstance(String param1, String param2) {
        IdealPersonalityFragment fragment = new IdealPersonalityFragment();
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
        View view = inflater.inflate(R.layout.fragment_ideal_personality, container, false);
        personalityArray = getResources().getStringArray(R.array.성격);
        selectedValues = new ArrayList<>();

        LinearLayout checkBoxLayout = view.findViewById(R.id.fragment_personality_checkbox_layout);

        // 수정된 부분
        LinearLayout currentLinearLayout = null;
        LinearLayout.LayoutParams checkBoxParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        checkBoxParams.weight = 1;
        checkBoxParams.setMargins(2, 2, 2, 2);

        for (int i = 0; i < personalityArray.length; i++) {
            final String currentPersonality = personalityArray[i];

            if (i % 4 == 0) {
                currentLinearLayout = new LinearLayout(getActivity());
                currentLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                checkBoxLayout.addView(currentLinearLayout);
            }

            CheckBox checkBox = new CheckBox(getActivity());
            checkBox.setText(currentPersonality);
            checkBox.setLayoutParams(checkBoxParams);
            checkBox.setBackgroundResource(R.drawable.button_border);
            checkBox.setButtonDrawable(null);
            checkBox.setGravity(Gravity.CENTER);

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        if (selectedValues.size() < MAX_PERSONALITY) {
                            selectedValues.add(currentPersonality);
                        } else {
                            checkBox.setChecked(false);
                            Toast.makeText(getActivity(), "최대 " + MAX_PERSONALITY + "개까지 선택 가능합니다.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        selectedValues.remove(currentPersonality);
                    }
                    sendValueToActivity(selectedValues);
                }
            });
            currentLinearLayout.addView(checkBox);
        }


        return view;
    }


    private void sendValueToActivity(Object value) {
        if (getActivity() != null && getActivity() instanceof SetIdeal2Activity) {
            SetIdeal2Activity activity = (SetIdeal2Activity) getActivity();
            activity.onValueReceived(value); // 액티비티의 메서드를 호출하여 값을 전달
        }
    }
}