package com.example.hanshinchat1.ProfileFragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.hanshinchat1.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ProfilePersonalityFragment extends Fragment {
    DatabaseReference myRef;
    FirebaseUser user;
    private ArrayList<String> selectedPersonality = new ArrayList<>();
    private static final int MAX_PERSONALITY = 3;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_personality_fragment, container, false);

        myRef = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
        String[] personalityArray = getResources().getStringArray(R.array.성격);

        LinearLayout checkBoxLayout = view.findViewById(R.id.personality_checkbox_layout_fragment);
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
                currentLinearLayout = new LinearLayout(getContext());
                currentLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                checkBoxLayout.addView(currentLinearLayout);
            }
            CheckBox checkBox = new CheckBox(getContext());
            checkBox.setText(currentPersonality);
            checkBox.setLayoutParams(checkBoxParams);
            checkBox.setBackgroundResource(R.drawable.button_border);
            checkBox.setButtonDrawable(null);
            checkBox.setGravity(Gravity.CENTER);

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        if (selectedPersonality.size() < MAX_PERSONALITY) {
                            selectedPersonality.add(currentPersonality);
                        } else {
                            checkBox.setChecked(false);
                            Toast.makeText(getContext(), "최대 " + MAX_PERSONALITY + "개까지 선택 가능합니다.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        selectedPersonality.remove(currentPersonality);
                    }
                }
            });
            currentLinearLayout.addView(checkBox);
        }
        return view;
    }
    public void updateDB() {
        DatabaseReference userRef = myRef.child("users").child(user.getUid());
        userRef.child("personality").setValue(selectedPersonality);
    }

    public String editDB() {
        if (selectedPersonality.isEmpty()) {
            Toast.makeText(getContext(), "성격을 선택해주세요", Toast.LENGTH_SHORT).show();
            return null;
        } else {
            String newPersonality = TextUtils.join(", ", selectedPersonality);
            return newPersonality;
        }
    }
}
