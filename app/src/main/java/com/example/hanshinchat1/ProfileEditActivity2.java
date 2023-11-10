package com.example.hanshinchat1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.hanshinchat1.ProfileFragment.ProfileAddressFragment;
import com.example.hanshinchat1.ProfileFragment.ProfileAgeFragment;
import com.example.hanshinchat1.ProfileFragment.ProfileDepartmentFragment;
import com.example.hanshinchat1.ProfileFragment.ProfileDrinkingFragment;
import com.example.hanshinchat1.ProfileFragment.ProfileFashionFemaleFragment;
import com.example.hanshinchat1.ProfileFragment.ProfileFashionMaleFragment;
import com.example.hanshinchat1.ProfileFragment.ProfileFormFragment;
import com.example.hanshinchat1.ProfileFragment.ProfileGenderFragment;
import com.example.hanshinchat1.ProfileFragment.ProfileGradeFragment;
import com.example.hanshinchat1.ProfileFragment.ProfileHeightFragment;
import com.example.hanshinchat1.ProfileFragment.ProfileInterestFragment;
import com.example.hanshinchat1.ProfileFragment.ProfileMbtiFragment;
import com.example.hanshinchat1.ProfileFragment.ProfileNameFragment;
import com.example.hanshinchat1.ProfileFragment.ProfilePersonalityFragment;
import com.example.hanshinchat1.ProfileFragment.ProfileReligionFragment;
import com.example.hanshinchat1.ProfileFragment.ProfileSmokingFragment;
import com.example.hanshinchat1.ProfileFragment.ProfileStudentIdFragment;

public class ProfileEditActivity2 extends MainActivity {

    private TextView editedName, editedAge, editedStudentId, editedGrade;
    private RadioGroup editedGender, editedReligion, editedSmoking, editedDrinking, editedForm, firstEditedMbti, SecondEditedMbti, ThirdEditedMbti, ForthEditedMbti;
    private View editedInterest, editedPersonality, editedDepartment, editedHeight, firstEditedAddress, secondEditedAddress;
    private Button editBtn, cancelBtn;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_fragment);

        editBtn = findViewById(R.id.edit_profile_edit_btn);
        cancelBtn = findViewById(R.id.edit_profile_cancel_btn);
        fragmentManager = getSupportFragmentManager();

        int requestCode = getIntent().getIntExtra("requestCode", -1);
        switch (requestCode) {
            case 1:
                ProfileNameFragment nameFragment = new ProfileNameFragment();
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.edit_profile_frame, nameFragment);
                transaction.commit();

                editBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editedName = nameFragment.getView().findViewById(R.id.name_fragment);
                        String editedNameStr = nameFragment.editDB();
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("editedName", editedNameStr);
                        setResult(RESULT_OK, resultIntent);
                        finish();
                    }
                });
                break;
            case 2:
                ProfileGenderFragment genderFragment = new ProfileGenderFragment();
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.edit_profile_frame, genderFragment);
                transaction.commit();
                editBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editedGender = genderFragment.getView().findViewById(R.id.gender_radio_group_fragment);
                        String editedGenderStr = genderFragment.editDB();
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("editedGender", editedGenderStr);
                        setResult(RESULT_OK, resultIntent);
                        finish();
                    }
                });
                break;
            case 3:
                ProfileInterestFragment interestFragment = new ProfileInterestFragment();
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.edit_profile_frame, interestFragment);
                transaction.commit();
                editBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editedInterest = interestFragment.getView().findViewById(R.id.interest_checkbox_layout_fragment);
                        String editedInterestStr = interestFragment.editDB();
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("editedInterest", editedInterestStr);
                        setResult(RESULT_OK, resultIntent);
                        finish();
                    }
                });
                break;
            case 4:
                ProfilePersonalityFragment personalityFragment = new ProfilePersonalityFragment();
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.edit_profile_frame, personalityFragment);
                transaction.commit();
                editBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editedPersonality = personalityFragment.getView().findViewById(R.id.personality_checkbox_layout_fragment);
                        String editedPersonalityStr = personalityFragment.editDB();
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("editedPersonality", editedPersonalityStr);
                        setResult(RESULT_OK, resultIntent);
                        finish();
                    }
                });
                break;
            case 5:
                ProfileAgeFragment ageFragment = new ProfileAgeFragment();
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.edit_profile_frame, ageFragment);
                transaction.commit();

                editBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editedAge = ageFragment.getView().findViewById(R.id.age_fragment);
                        String editedAgeStr = ageFragment.editDB();
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("editedAge", editedAgeStr);
                        setResult(RESULT_OK, resultIntent);
                        finish();
                    }
                });
                break;
            case 6:
                ProfileStudentIdFragment studentIdFragment = new ProfileStudentIdFragment();
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.edit_profile_frame, studentIdFragment);
                transaction.commit();

                editBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editedStudentId = studentIdFragment.getView().findViewById(R.id.student_id_fragment);
                        String editedStudentIdStr = studentIdFragment.editDB();
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("editedStudentId", editedStudentIdStr);
                        setResult(RESULT_OK, resultIntent);
                        finish();
                    }
                });
                break;
            case 7:
                ProfileDepartmentFragment departmentFragment = new ProfileDepartmentFragment();
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.edit_profile_frame, departmentFragment);
                transaction.commit();

                editBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editedDepartment = departmentFragment.getView().findViewById(R.id.department_spinner_fragment);
                        String editedDepartmentStr = departmentFragment.editDB();
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("editedDepartment", editedDepartmentStr);
                        setResult(RESULT_OK, resultIntent);
                        finish();
                    }
                });
                break;
            case 8:
                ProfileHeightFragment heightFragment = new ProfileHeightFragment();
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.edit_profile_frame, heightFragment);
                transaction.commit();

                editBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editedHeight = heightFragment.getView().findViewById(R.id.height_number_picker_fragment);
                        String editedHeightStr = heightFragment.editDB();
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("editedHeight", editedHeightStr);
                        setResult(RESULT_OK, resultIntent);
                        finish();
                    }
                });
                break;

            case 9:
                ProfileReligionFragment religionFragment = new ProfileReligionFragment();
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.edit_profile_frame, religionFragment);
                transaction.commit();

                editBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editedReligion = religionFragment.getView().findViewById(R.id.religion_radio_group_fragment);
                        String editedReligionStr = religionFragment.editDB();
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("editedReligion", editedReligionStr);
                        setResult(RESULT_OK, resultIntent);
                        finish();
                    }
                });
                break;
            case 10:
                ProfileAddressFragment addressFragment = new ProfileAddressFragment();
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.edit_profile_frame, addressFragment);
                transaction.commit();

                editBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        firstEditedAddress = addressFragment.getView().findViewById(R.id.first_address_number_picker_fragment);
                        secondEditedAddress = addressFragment.getView().findViewById(R.id.second_address_number_picker_fragment);
                        String editedAddressStr = addressFragment.editDB();
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("editedAddress", editedAddressStr);
                        setResult(RESULT_OK, resultIntent);
                        finish();
                    }
                });
                break;

            case 11:
                ProfileSmokingFragment smokingFragment = new ProfileSmokingFragment();
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.edit_profile_frame, smokingFragment);
                transaction.commit();

                editBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editedSmoking = smokingFragment.getView().findViewById(R.id.smoking_radio_group_fragment);
                        String editedSmokingStr = smokingFragment.editDB();
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("editedSmoking", editedSmokingStr);
                        setResult(RESULT_OK, resultIntent);
                        finish();
                    }
                });
                break;
            case 12:
                ProfileDrinkingFragment drinkingFragment = new ProfileDrinkingFragment();
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.edit_profile_frame, drinkingFragment);
                transaction.commit();

                editBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editedDrinking = drinkingFragment.getView().findViewById(R.id.drinking_radio_group_fragment);
                        String editedDrinkingStr = drinkingFragment.editDB();
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("editedDrinking", editedDrinkingStr);
                        setResult(RESULT_OK, resultIntent);
                        finish();
                    }
                });
                break;
            case 13:
                ProfileFormFragment formFragment = new ProfileFormFragment();
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.edit_profile_frame, formFragment);
                transaction.commit();

                editBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editedForm = formFragment.getView().findViewById(R.id.form_radio_group_fragment);
                        String editedFormStr = formFragment.editDB();
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("editedForm", editedFormStr);
                        setResult(RESULT_OK, resultIntent);
                        finish();
                    }
                });
                break;
            case 14:
                ProfileGradeFragment gradeFragment = new ProfileGradeFragment();
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.edit_profile_frame, gradeFragment);
                transaction.commit();

                editBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editedGrade = gradeFragment.getView().findViewById(R.id.grade_fragment);
                        String editedGradeStr = gradeFragment.editDB();
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("editedGrade", editedGradeStr);
                        setResult(RESULT_OK, resultIntent);
                        finish();
                    }
                });
                break;
            case 15:
                ProfileMbtiFragment mbtiFragment = new ProfileMbtiFragment();
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.edit_profile_frame, mbtiFragment);
                transaction.commit();

                editBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        firstEditedMbti = mbtiFragment.getView().findViewById(R.id.mbti_first_radio_group_fragment);
                        SecondEditedMbti = mbtiFragment.getView().findViewById(R.id.mbti_second_radio_group_fragment);
                        ThirdEditedMbti = mbtiFragment.getView().findViewById(R.id.mbti_third_radio_group_fragment);
                        ForthEditedMbti = mbtiFragment.getView().findViewById(R.id.mbti_forth_radio_group_fragment);
                        String editedMbtiStr = mbtiFragment.editDB();
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("editedMbti", editedMbtiStr);
                        setResult(RESULT_OK, resultIntent);
                        finish();
                    }
                });
                break;
            case 16:
                ProfileFashionMaleFragment fashionMaleFragment = new ProfileFashionMaleFragment();
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.edit_profile_frame, fashionMaleFragment);
                transaction.commit();

                editBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String editedFashionStr = fashionMaleFragment.editDB();
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("editedFashion", editedFashionStr);
                        setResult(RESULT_OK, resultIntent);
                        finish();
                    }
                });
                break;
            case 17:
                ProfileFashionFemaleFragment fashionFemaleFragment = new ProfileFashionFemaleFragment();
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.edit_profile_frame, fashionFemaleFragment);
                transaction.commit();

                editBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String editedFashionStr = fashionFemaleFragment.editDB();
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("editedFashion", editedFashionStr);
                        setResult(RESULT_OK, resultIntent);
                        finish();
                    }
                });
                break;

            default:
                break;
        }
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfileEditActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
