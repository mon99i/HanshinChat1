package com.example.hanshinchat1.Room;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.hanshinchat1.R;

public class MatchInfo1 extends Fragment {

    private ImageView photo;
    private TextView gender;
    private TextView age;
    private TextView department;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.matchinfo1, container, false);

        //        이거 아님. 사진 받으려면 url
        photo = rootView.findViewById(R.id.match_image);
        gender = rootView.findViewById(R.id.match_gender);
        age = rootView.findViewById(R.id.match_age);
        department = rootView.findViewById(R.id.match_department);

        return rootView;
    }
}
