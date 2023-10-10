package com.example.hanshinchat1.Match;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.hanshinchat1.R;
import com.google.firebase.auth.FirebaseAuth;

public class matchFragment1 extends Fragment {

    private ImageView photo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.matchinfo1, container, false);

//        이거 아님. 사진 받으려면 url
        photo = rootView.findViewById(R.id.match_image);


        return rootView;
    }
}
