package com.example.hanshinchat1.fragment;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hanshinchat1.MatchRoom;
import com.example.hanshinchat1.R;
import com.example.hanshinchat1.UserInfo;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShowUserFragment1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShowUserFragment1 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private ScrollView scroll1;
    public UserInfo userInfo;
    public MatchRoom matchRoom;
    public ShowUserFragment1(UserInfo userInfo) {
        // Required empty public constructor
        this.userInfo=userInfo;
    }

    public ShowUserFragment1(MatchRoom matchRoom) {
        this.matchRoom=matchRoom;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment recommendUserFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShowUserFragment1 newInstance(String param1, String param2, UserInfo userInfo) {
        ShowUserFragment1 fragment = new ShowUserFragment1(userInfo);
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
        View view=inflater.inflate(R.layout.fragment_show_user1, container, false);

        ImageView recommendUserImage=view.findViewById(R.id.recommendUserImage);
        TextView recommendAgeAddress=view.findViewById(R.id.recommendAgeAddress);
        TextView recommendUserHeight=view.findViewById(R.id.recommendUserHeight);
        TextView recommendUserForm=view.findViewById(R.id.recommendUserForm);
        TextView recommendUserDepartment=view.findViewById(R.id.recommendUserDepartment);
        TextView recommendUserDrinking=view.findViewById(R.id.recommendUserDrinking);
        TextView recommendUserSmoking=view.findViewById(R.id.recommendUserSmoking);
        TextView recommendUserReligion=view.findViewById(R.id.recommendUserReligion);
        scroll1 = view.findViewById(R.id.recommendUserScroll1);

        String imageUrl = userInfo.getPhotoUrl();
        Uri imageUri = Uri.parse(imageUrl);
        Glide.with(getActivity()).load(imageUri).into(recommendUserImage);
        recommendAgeAddress.setText(userInfo.getAge()+"세,  "+userInfo.getAddress());
        recommendUserHeight.setText(userInfo.getHeight()+"cm");
        recommendUserForm.setText(userInfo.getForm());
        recommendUserDepartment.setText(userInfo.getDepartment());
        recommendUserDrinking.setText(userInfo.getDrinking());
        recommendUserSmoking.setText(userInfo.getSmoking());
        recommendUserReligion.setText(userInfo.getReligion());

        return view;
    }

    public void resetScrollView() {
        // 스크롤뷰를 초기화하는 코드
        scroll1.scrollTo(0, 0); // 스크롤 위치를 초기화
    }
}