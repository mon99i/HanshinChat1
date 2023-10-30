package com.example.hanshinchat1;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.hanshinchat1.ProfileFragment.ProfileAddressFragment;
import com.example.hanshinchat1.ProfileFragment.ProfileAgeFragment;
import com.example.hanshinchat1.ProfileFragment.ProfileCompleteFragment;
import com.example.hanshinchat1.ProfileFragment.ProfileDepartmentFragment;
import com.example.hanshinchat1.ProfileFragment.ProfileDrinkingFragment;
import com.example.hanshinchat1.ProfileFragment.ProfileFashionFemaleFragment;
import com.example.hanshinchat1.ProfileFragment.ProfileFashionMaleFragment;
import com.example.hanshinchat1.ProfileFragment.ProfileFormFragment;
import com.example.hanshinchat1.ProfileFragment.ProfileGenderFragment;
import com.example.hanshinchat1.ProfileFragment.ProfileGradeFragment;
import com.example.hanshinchat1.ProfileFragment.ProfileHeightFragment;
import com.example.hanshinchat1.ProfileFragment.ProfileIdealTypeFragment;
import com.example.hanshinchat1.ProfileFragment.ProfileInterestFragment;
import com.example.hanshinchat1.ProfileFragment.ProfileMbtiFragment;
import com.example.hanshinchat1.ProfileFragment.ProfileNameFragment;
import com.example.hanshinchat1.ProfileFragment.ProfilePersonalityFragment;
import com.example.hanshinchat1.ProfileFragment.ProfileReligionFragment;
import com.example.hanshinchat1.ProfileFragment.ProfileSmokingFragment;
import com.example.hanshinchat1.ProfileFragment.ProfileStudentIdFragment;
import com.example.hanshinchat1.fragment.IdealAddressFragment;
import com.example.hanshinchat1.fragment.IdealAgeFragment;
import com.example.hanshinchat1.fragment.IdealDrinkingFragment;
import com.example.hanshinchat1.fragment.IdealFormFragment;
import com.example.hanshinchat1.fragment.IdealHeightFragment;
import com.example.hanshinchat1.fragment.IdealInterestFragment;
import com.example.hanshinchat1.fragment.IdealPersonalityFragment;
import com.example.hanshinchat1.fragment.IdealReligionFragment;
import com.example.hanshinchat1.fragment.IdealSmokingFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.example.hanshinchat1.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SetProfileActivity extends AppCompatActivity {

    private Button setProfileNextBtn;
    private ProgressBar setProfileProgressBar;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private Fragment nextFragment;

    private ProfileNameFragment nameFragment;
    private ProfileGenderFragment genderFragment;
    private ProfileAgeFragment ageFragment;
    private ProfileGradeFragment gradeFragment;
    private ProfileStudentIdFragment studentIdFragment;
    private ProfileDepartmentFragment departmentFragment;
    private ProfileHeightFragment heightFragment;
    private ProfileFormFragment formFragment;
    private ProfileAddressFragment addressFragment;
    private ProfileReligionFragment religionFragment;
    private ProfileSmokingFragment smokingFragment;
    private ProfileDrinkingFragment drinkingFragment;
    private ProfileInterestFragment interestFragment;
    private ProfilePersonalityFragment personalityFragment;
    private ProfileMbtiFragment mbtiFragment;
    private ProfileIdealTypeFragment idealTypeFragment;
    private ProfileCompleteFragment completeFragment;

    private ProfileFashionMaleFragment fashionMaleFragment;

    private ProfileFashionFemaleFragment fashionFemaleFragment;

    private MainActivity mainActivity;

    private UserInfo userInfo;
    private TextView progressTextView;
    private int currentStep = 1;
    private int totalSteps = 19;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_profile_fragment);

        initializeView();
        checkProfile();
        initializeListener();
    }

    private void initializeView() {
        nameFragment = new ProfileNameFragment();
        genderFragment = new ProfileGenderFragment();
        ageFragment = new ProfileAgeFragment();
        gradeFragment = new ProfileGradeFragment();
        studentIdFragment = new ProfileStudentIdFragment();
        departmentFragment = new ProfileDepartmentFragment();
        heightFragment = new ProfileHeightFragment();
        formFragment = new ProfileFormFragment();
        addressFragment = new ProfileAddressFragment();
        religionFragment = new ProfileReligionFragment();
        smokingFragment = new ProfileSmokingFragment();
        drinkingFragment = new ProfileDrinkingFragment();
        interestFragment = new ProfileInterestFragment();
        personalityFragment = new ProfilePersonalityFragment();
        mbtiFragment = new ProfileMbtiFragment();
        idealTypeFragment = new ProfileIdealTypeFragment();
        completeFragment = new ProfileCompleteFragment();
        fashionMaleFragment = new ProfileFashionMaleFragment();
        fashionFemaleFragment = new ProfileFashionFemaleFragment();

        setProfileNextBtn = findViewById(R.id.set_profile_next_btn);
        setProfileProgressBar = findViewById(R.id.set_profile_progress);

        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();

        setProfileProgressBar = findViewById(R.id.set_profile_progress);
        setProfileProgressBar.setProgress(0);
        setProfileProgressBar.setMax(19);
        progressTextView = findViewById(R.id.progress_text_view);
        progressTextView.setText(currentStep + "/" + totalSteps);
    }

    private void initializeListener() {
        setProfileNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = fragmentManager.findFragmentById(R.id.set_profile_frame);
                if (fragment instanceof ProfileNameFragment) {
                    ((ProfileNameFragment) fragment).updateDB();
                } else if (fragment instanceof ProfileGenderFragment) {
                    ((ProfileGenderFragment) fragment).updateDB();
                } else if (fragment instanceof ProfileAgeFragment) {
                    ((ProfileAgeFragment) fragment).updateDB();
                } else if (fragment instanceof ProfileGradeFragment) {
                    ((ProfileGradeFragment) fragment).updateDB();
                } else if (fragment instanceof ProfileStudentIdFragment) {
                    ((ProfileStudentIdFragment) fragment).updateDB();
                } else if (fragment instanceof ProfileDepartmentFragment) {
                    ((ProfileDepartmentFragment) fragment).updateDB();
                } else if (fragment instanceof ProfileHeightFragment) {
                    ((ProfileHeightFragment) fragment).updateDB();
                } else if (fragment instanceof ProfileFormFragment) {
                    ((ProfileFormFragment) fragment).updateDB();
                } else if (fragment instanceof ProfileAddressFragment) {
                    ((ProfileAddressFragment) fragment).updateDB();
                } else if (fragment instanceof ProfileReligionFragment) {
                    ((ProfileReligionFragment) fragment).updateDB();
                } else if (fragment instanceof ProfileSmokingFragment) {
                    ((ProfileSmokingFragment) fragment).updateDB();
                } else if (fragment instanceof ProfileDrinkingFragment) {
                    ((ProfileDrinkingFragment) fragment).updateDB();
                } else if (fragment instanceof ProfileInterestFragment) {
                    ((ProfileInterestFragment) fragment).updateDB();
                } else if (fragment instanceof ProfilePersonalityFragment) {
                    ((ProfilePersonalityFragment) fragment).updateDB();
                } else if (fragment instanceof ProfileMbtiFragment) {
                    ((ProfileMbtiFragment) fragment).updateDB();
                } else if (fragment instanceof ProfileIdealTypeFragment) {
                    ((ProfileIdealTypeFragment) fragment).updateDB();
                } else if (fragment instanceof ProfileFashionMaleFragment) {
                    ((ProfileFashionMaleFragment) fragment).updateDB();
                } else if (fragment instanceof ProfileFashionFemaleFragment) {
                    ((ProfileFashionFemaleFragment) fragment).updateDB();
                } else if (fragment instanceof ProfileCompleteFragment){
                    ((ProfileCompleteFragment) fragment).updateDB();
                } else {
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
                checkProfile();
            }
        });


    }

    private void checkProfile() {
        FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        UserInfo userInfo = snapshot.getValue(UserInfo.class);
                        Class<?> nextFragmentClass = getNextProfileFragment(userInfo);
                        if (nextFragmentClass != null) {
                            try {
                                Fragment nextFragment = (Fragment) nextFragmentClass.newInstance();
                                transaction = fragmentManager.beginTransaction();
                                transaction.replace(R.id.set_profile_frame, nextFragment).commitAllowingStateLoss();
                                updateProgressBar(nextFragment);
                            } catch (InstantiationException | IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }


    private void updateProgressBar(Fragment fragment) {
        int progress = 0;
        currentStep = 1;
        totalSteps = 19;
        if (fragment instanceof ProfileNameFragment) {
            progress = 2; currentStep = 2;
        } else if (fragment instanceof ProfileGenderFragment) {
            progress = 3; currentStep = 3;
        } else if (fragment instanceof ProfileAgeFragment) {
            progress = 4; currentStep = 4;
        } else if (fragment instanceof ProfileGradeFragment) {
            progress = 5; currentStep = 5;
        } else if (fragment instanceof ProfileStudentIdFragment) {
            progress = 6; currentStep = 6;
        } else if (fragment instanceof ProfileDepartmentFragment) {
            progress = 7; currentStep = 7;
        } else if (fragment instanceof ProfileHeightFragment) {
            progress = 8; currentStep = 8;
        } else if (fragment instanceof ProfileFormFragment) {
            progress = 9; currentStep = 9;
        } else if (fragment instanceof ProfileAddressFragment) {
            progress = 10; currentStep = 10;
        } else if (fragment instanceof ProfileReligionFragment) {
            progress = 11; currentStep = 11;
        } else if (fragment instanceof ProfileSmokingFragment) {
            progress = 12; currentStep = 12;
        } else if (fragment instanceof ProfileDrinkingFragment) {
            progress = 13; currentStep = 13;
        } else if (fragment instanceof ProfileInterestFragment) {
            progress = 14; currentStep = 14;
        } else if (fragment instanceof ProfilePersonalityFragment) {
            progress = 15; currentStep = 15;
        } else if (fragment instanceof ProfileMbtiFragment) {
            progress = 16; currentStep = 16;
        } else if (fragment instanceof ProfileIdealTypeFragment) {
            progress = 17; currentStep = 17;
        } else if (fragment instanceof ProfileFashionMaleFragment) {
            progress = 18; currentStep = 18;
        } else if (fragment instanceof ProfileFashionFemaleFragment) {
            progress = 18; currentStep = 18;
        } else if (fragment instanceof ProfileCompleteFragment) {
            progress = 19; currentStep = 19;
        }
        setProfileProgressBar.setProgress(progress);
        progressTextView.setText(currentStep + "/" + totalSteps);
    }


    private Class<?> getNextProfileFragment(UserInfo userInfo) {
        if (userInfo.getName() == null) {
            return ProfileNameFragment.class;
        } else if (userInfo.getGender() == null) {
            return ProfileGenderFragment.class;
        } else if (userInfo.getAge() == null) {
            return ProfileAgeFragment.class;
        } else if (userInfo.getGrade() == null) {
            return ProfileGradeFragment.class;
        } else if (userInfo.getStudentId() == null) {
            return ProfileStudentIdFragment.class;
        } else if (userInfo.getDepartment() == null) {
            return ProfileDepartmentFragment.class;
        } else if (userInfo.getHeight() == null) {
            return ProfileHeightFragment.class;
        } else if (userInfo.getForm() == null) {
            return ProfileFormFragment.class;
        } else if (userInfo.getAddress() == null) {
            return ProfileAddressFragment.class;
        } else if (userInfo.getReligion() == null) {
            return ProfileReligionFragment.class;
        } else if (userInfo.getSmoking() == null) {
            return ProfileSmokingFragment.class;
        } else if (userInfo.getDrinking() == null) {
            return ProfileDrinkingFragment.class;
        } else if (userInfo.getInterest() == null) {
            return ProfileInterestFragment.class;
        } else if (userInfo.getPersonality() == null) {
            return ProfilePersonalityFragment.class;
        } else if (userInfo.getMbti() == null) {
            return ProfileMbtiFragment.class;
        } else if (userInfo.getFashion() == null) {
            if(userInfo.getGender().equals("남자")){
                return ProfileFashionMaleFragment.class;
            } else if(userInfo.getGender().equals("여자")){
                return ProfileFashionFemaleFragment.class;
            }
        } else if (userInfo.getCreationTime() == null) {
            return ProfileCompleteFragment.class;
        }
        // 모든 프로필 정보가 입력되었을 때 null 반환
        return null;
    }
}