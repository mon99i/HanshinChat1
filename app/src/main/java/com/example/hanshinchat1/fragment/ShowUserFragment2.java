package com.example.hanshinchat1.fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.hanshinchat1.MatchRoom;
import com.example.hanshinchat1.R;
import com.example.hanshinchat1.UserInfo;
import com.google.android.flexbox.FlexboxLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShowUserFragment2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShowUserFragment2 extends Fragment {
    private static final String TAG = "RecommendUserFragment2";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private UserInfo userInfo;


    private TextView recommendIdealTxt1;
    private TextView recommendIdealTxt2;
    private TextView recommendIdealTxt3;
    private FlexboxLayout recommendIdeal1;
    private FlexboxLayout recommendIdeal2;
    private FlexboxLayout recommendIdeal3;



    private ScrollView scroll2;
    public ShowUserFragment2(UserInfo userInfo) {
        // Required empty public constructor
        this.userInfo = userInfo;
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment recommendUserFragment2.
     */
    // TODO: Rename and change types and number of parameters
    public static ShowUserFragment2 newInstance(String param1, String param2, UserInfo userInfo) {
        ShowUserFragment2 fragment = new ShowUserFragment2(userInfo);
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
        View view = inflater.inflate(R.layout.fragment_show_user2, container, false);
        TextView recommendUserFashion= view.findViewById(R.id.recommendUserFashion);
        TextView recommendUserMBTI = view.findViewById(R.id.recommendUserMBTI);
        FlexboxLayout recommendPersonality = view.findViewById(R.id.recommendPersonality);
        FlexboxLayout recommendInterest = view.findViewById(R.id.recommendInterest);
        ConstraintLayout noneRecommendIdeal=view.findViewById(R.id.noneRecommendIdeal);
        scroll2 = view.findViewById(R.id.recommendUserScroll2);

        recommendIdealTxt1=view.findViewById(R.id.recommendIdealTxt1);
        recommendIdealTxt2=view.findViewById(R.id.recommendIdealTxt2);
        recommendIdealTxt3=view.findViewById(R.id.recommendIdealTxt3);
        recommendIdeal1 = view.findViewById(R.id.recommendIdeal1);
        recommendIdeal2 = view.findViewById(R.id.recommendIdeal2);
        recommendIdeal3 = view.findViewById(R.id.recommendIdeal3);


        recommendUserFashion.setText(userInfo.getFashion()+" 룩 즐겨입어요");
        recommendUserMBTI.setText(userInfo.getMbti() + " 에요");

        ArrayList<String> personalities = userInfo.getPersonality();
        ArrayList<String> interests = userInfo.getInterest();
        for (String personality : personalities) {
            TextView textView = new TextView(getActivity());
            textView.setText(personality);
            textView.setBackgroundResource(R.drawable.background_ideal_list_layout2);
            textView.setTextColor(Color.parseColor("#434959"));
            textView.setPadding(15, 15, 15, 15);
            recommendPersonality.addView(textView);
        }
        for (String interest : interests) {
            TextView textView = new TextView(getActivity());
            textView.setText(interest);
            textView.setBackgroundResource(R.drawable.background_ideal_list_layout2);
            textView.setTextColor(Color.parseColor("#434959"));
            textView.setPadding(15, 15, 15, 15);
            recommendInterest.addView(textView);

        }


        FirebaseDatabase.getInstance().getReference().child("ideals").child(userInfo.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            int i=1;
                            for(DataSnapshot item:snapshot.getChildren()){
                                for(DataSnapshot subItem:item.getChildren()){
                                    Log.d(TAG, "setRecommendIdeal: i2"+i);
                                    setRecommendIdeal(subItem.getKey(),subItem.getValue(),i);
                                }
                                i++;

                            }
                        }else noneRecommendIdeal.setVisibility(View.VISIBLE);


                    /*    setRecommendIdeal(ideal);
                        TextView textView = new TextView(getActivity());
                        textView.setText(i);
                        textView.setBackgroundResource(R.drawable.background_ideal_list_layout2);
                        textView.setTextColor(Color.parseColor("#434959"));
                        textView.setPadding(15, 15, 15, 15);
                        recommendInterest.addView(textView);*/
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        // Inflate the layout for this fragment
        return view;
    }

    public TextView getRecommendIdealTxt(int i){
        if(i==1){
            return recommendIdealTxt1;
        }else if(i==2){
            return recommendIdealTxt2;
        }else return recommendIdealTxt3;

    }
    public FlexboxLayout getRecommendIdeal(int i){
        if(i==1){
            return recommendIdeal1;
        }else if(i==2){
            return recommendIdeal2;
        }else return recommendIdeal3;

    }

    private void setRecommendIdeal(String idealKey, Object idealValue, int i) {
        ArrayList<Object> idealValues=new ArrayList<>();
        if(idealValue instanceof ArrayList){
             idealValues=(ArrayList<Object>)idealValue;
        }
        String strIdealValue=idealValue.toString();
        Log.d(TAG, "setRecommendIdeal: i3"+i);
        getRecommendIdealTxt(i).setVisibility(View.VISIBLE);
        getRecommendIdeal(i).setVisibility(View.VISIBLE);
        TextView textView = new TextView(getActivity());
        textView.setBackgroundResource(R.drawable.background_ideal_list_layout2);
        textView.setTextColor(Color.parseColor("#434959"));
        textView.setPadding(15, 15, 15, 15);

        Log.d(TAG, "setRecommendIdeal: "+getRecommendIdealTxt(i));
        Log.d(TAG, "setRecommendIdeal: "+getRecommendIdeal(i));

        switch(idealKey){
            case "address" :
                getRecommendIdealTxt(i).setText("거주지는");
                textView.setText(strIdealValue);
                getRecommendIdeal(i).addView(textView);
                break;
            case "age" :
                String age1=idealValues.get(0).toString();
                String age2=idealValues.get(idealValues.size()-1).toString();
                getRecommendIdealTxt(i).setText("나이는");
                textView.setText(age1+"세부터 "+age2+"세");
                getRecommendIdeal(i).addView(textView);
                break;
            case "drinking" :
                getRecommendIdealTxt(i).setText("음주는");
                textView.setText(strIdealValue);
                getRecommendIdeal(i).addView(textView);
                break;
            case "form" :
                getRecommendIdealTxt(i).setText("체형은");
                textView.setText(strIdealValue);
                getRecommendIdeal(i).addView(textView);
                break;
            case "height" :
                String height1=idealValues.get(0).toString();
                String height=idealValues.get(idealValues.size()-1).toString();
                textView.setText(height1+"cm부터 "+height+"cm");
                getRecommendIdealTxt(i).setText("키는");
                getRecommendIdeal(i).addView(textView);
                break;
            case "interest" :
                for(Object object:idealValues){
                    TextView textView2 = new TextView(getActivity());
                    textView2.setBackgroundResource(R.drawable.background_ideal_list_layout2);
                    textView2.setTextColor(Color.parseColor("#434959"));
                    textView2.setPadding(15, 15, 15, 15);
                    textView2.setText(object.toString());
                    getRecommendIdealTxt(i).setText("관심사는");
                    getRecommendIdeal(i).addView(textView2);
                }
                break;
            case "personality" :
                for(Object object:idealValues){
                    TextView textView3 = new TextView(getActivity());
                    textView3.setBackgroundResource(R.drawable.background_ideal_list_layout2);
                    textView3.setTextColor(Color.parseColor("#434959"));
                    textView3.setPadding(15, 15, 15, 15);
                    textView3.setText(object.toString());
                    getRecommendIdealTxt(i).setText("성격은");
                    getRecommendIdeal(i).addView(textView3);
                }
                break;
            case "religion" :
                getRecommendIdealTxt(i).setText("종교는");
                textView.setText(strIdealValue);
                getRecommendIdeal(i).addView(textView);
                break;
            case "smoking" :
                getRecommendIdealTxt(i).setText("흡연은");
                textView.setText(strIdealValue);
                getRecommendIdeal(i).addView(textView);
                break;
        }
    }

    public void resetScrollView() {
        // 스크롤뷰를 초기화하는 코드
        scroll2.scrollTo(0, 0); // 스크롤 위치를 초기화
    }

}