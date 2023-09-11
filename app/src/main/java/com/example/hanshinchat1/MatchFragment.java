package com.example.hanshinchat1;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.hanshinchat1.UserInfo;

import java.util.ArrayList;

public class MatchFragment extends Fragment {

    private UserInfo userInfo;

    public static MatchFragment newInstance(UserInfo userInfo) {
        MatchFragment fragment = new MatchFragment();
        Bundle args = new Bundle();
        args.putSerializable("userInfo", userInfo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userInfo = (UserInfo)getArguments().getSerializable("userInfo");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_match, container, false);

        // userInfo를 사용하여 화면에 데이터를 표시
       /* TextView nameTextView = view.findViewById(R.id.nameTextView);
        TextView ageTextView = view.findViewById(R.id.ageTextView);

        nameTextView.setText(userInfo.getName());
        ageTextView.setText(String.valueOf(userInfo.getAge()));

*/
        //receivedList = (ArrayList<UserInfo>) getIntent().getSerializableExtra("userInfos");

        TextView gender = view.findViewById(R.id.showMatch_txt_gender);
        TextView department = view.findViewById(R.id.showMatch_txt_department);
        TextView age = view.findViewById(R.id.showMatch_txt_age);
        TextView name = view.findViewById(R.id.showMatch_txt_name);
        ImageView image = view.findViewById(R.id.showMatch_image);



        Uri imageUri = Uri.parse( userInfo.getPhotoUrl());
        Glide.with(this).load(imageUri).into(image);
          //.apply(RequestOptions.circleCropTransform()) // 이미지 모양을 원형으로 지정 (선택 사항)

        name.setText( userInfo.getName());
        gender.setText( userInfo.getGender());
        department.setText( userInfo.getDepartment());
        age.setText( userInfo.getAge().toString());

        return view;
    }


}
