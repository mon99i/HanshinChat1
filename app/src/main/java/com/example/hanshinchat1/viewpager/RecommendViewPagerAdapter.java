package com.example.hanshinchat1.viewpager;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.hanshinchat1.ChattingActivity;
import com.example.hanshinchat1.HomeActivity;
import com.example.hanshinchat1.Match;
import com.example.hanshinchat1.MatchRoom;
import com.example.hanshinchat1.R;
import com.example.hanshinchat1.RoomInfo;
import com.example.hanshinchat1.UserInfo;

import com.example.hanshinchat1.fragment.ShowUserFragment1;
import com.example.hanshinchat1.fragment.ShowUserFragment2;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RecommendViewPagerAdapter extends FragmentStateAdapter {
    private UserInfo userInfo;
    public ArrayList<UserInfo> userInfos;
    private MatchRoom matchRoom;
    public ArrayList<Fragment> fragments=new ArrayList<>();
    private static final String TAG = "RecommendViewPagerAdapter ";

/*
    public RecommendViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, ArrayList<UserInfo> userInfos) {
        super(fragmentActivity);
        this.userInfos=userInfos;
        fragments.clear();
        for(UserInfo user:userInfos){
            fragments.add(new ShowUserFragment1(user));
            fragments.add(new ShowUserFragment2(user));
        }
        notifyDataSetChanged();
    }
*/

    public RecommendViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, UserInfo userInfo, boolean isRoom) {
        super(fragmentActivity);
//        this.userInfo=userInfo;
        fragments.add(new ShowUserFragment1(userInfo,isRoom));
        fragments.add(new ShowUserFragment2(userInfo));

    }



  /*  public RecommendViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);

        //userInfos=new ArrayList<>();

        setUpAllUsers();

    }

    private void setUpAllUsers() {
*//*        FirebaseDatabase.getInstance().getReference().child("users")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //userInfos.clear();
                        fragments.clear();
                        for (String uid:uids){
                            for(DataSnapshot item:snapshot.getChildren()){
                                if(uid.equals(item.getKey())){
                                    fragments.add(new ShowUserFragment1(item.getValue(UserInfo.class)));
                                    fragments.add(new ShowUserFragment2(item.getValue(UserInfo.class)));
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });*//*

        ArrayList<String> chatRequestUids=new ArrayList<>();
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase.getInstance().getReference().child("matches").child(user.getUid())
                .orderByChild("approved").equalTo(null).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        chatRequestUids.clear();
                        for(DataSnapshot item:snapshot.getChildren()){
                            chatRequestUids.add(item.getKey());
                        }
                        getAllUsers(chatRequestUids);
                        Log.d(TAG, "onDataChange: "+chatRequestUids.size());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void getAllUsers(ArrayList<String> chatRequestUids) {

        userInfos=new ArrayList<>();
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase.getInstance().getReference().child("users").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        fragments.clear();
                        userInfos.clear();
                        for(String uid:chatRequestUids){
                            for(DataSnapshot item:snapshot.getChildren()){
                                if(uid.equals(item.getKey())){
                                    UserInfo userInfo=item.getValue(UserInfo.class);
                                    userInfos.add(userInfo);
                                    fragments.add(new ShowUserFragment1(userInfo));
                                    fragments.add(new ShowUserFragment2(userInfo));
                                    Log.d(TAG, "onDataChange: "+userInfo.getName());
                                }
                            }
                        }
                        notifyDataSetChanged();


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }*/



    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }
}
