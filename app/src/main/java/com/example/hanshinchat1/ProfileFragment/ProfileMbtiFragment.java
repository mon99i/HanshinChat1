package com.example.hanshinchat1.ProfileFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.hanshinchat1.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileMbtiFragment extends Fragment {
    DatabaseReference myRef;
    FirebaseUser user;

    private String selectedFirst = "", selectedSecond = "", selectedThird = "", selectedForth = "";

    private RadioButton btnE, btnI, btnN, btnS, btnF, btnT, btnP, btnJ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_mbti_fragment, container, false);

        myRef = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
        btnE = view.findViewById(R.id.mbti_e_fragment);
        btnI = view.findViewById(R.id.mbti_i_fragment);
        btnN = view.findViewById(R.id.mbti_n_fragment);
        btnS = view.findViewById(R.id.mbti_s_fragment);
        btnF = view.findViewById(R.id.mbti_f_fragment);
        btnT = view.findViewById(R.id.mbti_t_fragment);
        btnP = view.findViewById(R.id.mbti_p_fragment);
        btnJ = view.findViewById(R.id.mbti_j_fragment);

        btnE.setChecked(true);
        btnN.setChecked(true);
        btnF.setChecked(true);
        btnP.setChecked(true);
        selectedFirst = "E";
        selectedSecond = "N";
        selectedThird = "F";
        selectedForth = "P";

        btnE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFirst = "E";
                btnE.setBackgroundResource(R.drawable.radio_button_checked);
                btnI.setBackgroundResource(R.drawable.radio_button_unchecked);
            }
        });
        btnI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFirst = "I";
                btnE.setBackgroundResource(R.drawable.radio_button_unchecked);
                btnI.setBackgroundResource(R.drawable.radio_button_checked);
            }
        });
        btnN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedSecond = "N";
                btnN.setBackgroundResource(R.drawable.radio_button_checked);
                btnS.setBackgroundResource(R.drawable.radio_button_unchecked);
            }
        });
        btnS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedSecond = "S";
                btnS.setBackgroundResource(R.drawable.radio_button_checked);
                btnN.setBackgroundResource(R.drawable.radio_button_unchecked);
            }
        });
        btnF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedThird = "F";
                btnF.setBackgroundResource(R.drawable.radio_button_checked);
                btnT.setBackgroundResource(R.drawable.radio_button_unchecked);
            }
        });
        btnT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedThird = "T";
                btnT.setBackgroundResource(R.drawable.radio_button_checked);
                btnF.setBackgroundResource(R.drawable.radio_button_unchecked);
            }
        });
        btnP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedForth = "P";
                btnP.setBackgroundResource(R.drawable.radio_button_checked);
                btnJ.setBackgroundResource(R.drawable.radio_button_unchecked);
            }
        });
        btnJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedForth = "J";
                btnJ.setBackgroundResource(R.drawable.radio_button_checked);
                btnP.setBackgroundResource(R.drawable.radio_button_unchecked);
            }
        });
        return view;
    }

    public void updateDB() {
        DatabaseReference userRef = myRef.child("users").child(user.getUid());
        String mbtiValue = selectedFirst + selectedSecond + selectedThird + selectedForth;
        userRef.child("mbti").setValue(mbtiValue);
    }

    public String editDB() {
        String newMbtiValue = selectedFirst + selectedSecond + selectedThird + selectedForth;
        return newMbtiValue;
    }
}
