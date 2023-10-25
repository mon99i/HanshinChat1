package com.example.hanshinchat1.Room;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;

import androidx.fragment.app.Fragment;

import com.example.hanshinchat1.databinding.BoardBinding;

import com.example.hanshinchat1.R;

public class MatchInfo2 extends Fragment {

    private TextView gender;
    private TextView age;
    private TextView grade;
    private TextView department;
    private TextView height;
    private TextView form;
    private TextView address;
    private TextView religion;
    private TextView smoking;
    private TextView drinking;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.matchinfo2, container, false);

        gender = rootView.findViewById(R.id.match_gender);
        age = rootView.findViewById(R.id.match_age);
        grade = rootView.findViewById(R.id.match_grade);
        department = rootView.findViewById(R.id.match_department);
        height = rootView.findViewById(R.id.match_height);
        form = rootView.findViewById(R.id.match_form);
        address = rootView.findViewById(R.id.match_address);
        religion = rootView.findViewById(R.id.match_religion);
        smoking = rootView.findViewById(R.id.match_smoking);
        drinking = rootView.findViewById(R.id.match_drinking);

        return rootView;
    }
}
