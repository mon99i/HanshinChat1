package com.example.hanshinchat1.recycler;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.hanshinchat1.Match;
import com.example.hanshinchat1.R;
import com.example.hanshinchat1.UserInfo;
import com.example.hanshinchat1.viewpager.RecommendViewPagerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator3;

public class RecyclerRecommendMatchAdapter extends RecyclerView.Adapter<RecyclerRecommendMatchAdapter.ViewHolder> {
    private static final String TAG = "RecyclerRecommendMatchAdapter";
    private Context context;
    private String recommendType;

    //일반 추천
    private ArrayList<UserInfo> recommendUsers;

    //이상형추천
    private ArrayList<UserInfo> firstIdealUsers;
    private ArrayList<UserInfo> secondIdealUsers;
    private ArrayList<UserInfo> thirdIdealUsers;

    private AlertDialog dialog;


    public RecyclerRecommendMatchAdapter(Context context, ArrayList<UserInfo> recommendUsers, String recommendType) {
        this.context = context;
        this.recommendUsers = recommendUsers;
        this.recommendType = recommendType;
        // checkMatchExists(recommendUsers);
    }


/*    //매칭 재점검
    private void checkMatchExists(ArrayList<UserInfo> recommendUsers) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase.getInstance().getReference().child("matches").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(UserInfo userInfo : recommendUsers) {
                    Match myMatch=snapshot.child(user.getUid()).child(userInfo.getUid()).getValue(Match.class);
                    Match opponentMatch=snapshot.child(userInfo.getUid()).child(user.getUid()).getValue(Match.class);

                    //이건 너희가 정하셈
                    if(myMatch!=null&&myMatch.getApproved()==true){
                        Log.d(TAG, "onDataChange: 내 UID 에 채팅방있음");
                        recommendUsers.remove(userInfo);
                    }
                    if (opponentMatch != null && opponentMatch.getApproved() == true) {
                        Log.d(TAG, "onDataChange: 상대 UID에 채팅방있음");
                        recommendUsers.remove(userInfo);
                    }

                }
                notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        FirebaseDatabase.getInstance().getReference().child("matches").child(user.getUid())
                .orderByChild("approved").equalTo(true).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            for(UserInfo userInfo: recommendUsers){
                                for(DataSnapshot item:snapshot.getChildren()){
                                    if(item.getKey().equals(userInfo.getUid())){
                                        recommendUsers.remove(userInfo);
                                    }
                                }
                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


}*/

    public RecyclerRecommendMatchAdapter(Context context, ArrayList<UserInfo> recommendUsers) {
        this.context = context;
        this.recommendUsers = recommendUsers;

    }

    public RecyclerRecommendMatchAdapter(Context context, String recommendType, ArrayList<UserInfo> firstIdealUsers,
                                         ArrayList<UserInfo> secondIdealUsers, ArrayList<UserInfo> thirdIdealUsers) {
        this.context = context;
        this.recommendType = recommendType;
        this.firstIdealUsers = firstIdealUsers;
        this.secondIdealUsers = secondIdealUsers;
        this.thirdIdealUsers = thirdIdealUsers;
        recommendUsers = new ArrayList<>();
        recommendUsers.addAll(firstIdealUsers);
        recommendUsers.addAll(secondIdealUsers);
        recommendUsers.addAll(thirdIdealUsers);
    }

    @NonNull
    @Override
    public RecyclerRecommendMatchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_recommend_match, parent, false);
        return new RecyclerRecommendMatchAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerRecommendMatchAdapter.ViewHolder holder, int position) {


        final int currentPosition = holder.getAbsoluteAdapterPosition();
        Uri imageUri = Uri.parse(recommendUsers.get(position).getPhotoUrl());
        Glide.with(context).load(imageUri).into(holder.recommendMatchImage);


        holder.recommendMatchName.setText(recommendUsers.get(position).getName());
        holder.recommendMatchAge.setText(recommendUsers.get(position).getAge() + "세");


        setRecommendTypeImage(holder, position);


        holder.recommendMatchImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showRecommendUserDialog(recommendUsers.get(currentPosition));
//                Intent intent = new Intent(context, MbtiMatchActivity2.class);
//                context.startActivity(intent);
            }
        });


    }

    private void showRecommendUserDialog(UserInfo userInfo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recommend_user_dialog, null);
        ImageView cancelBtn = view.findViewById(R.id.cancel_image_view);

        ViewPager2 recommendViewPager = view.findViewById(R.id.decisionViewPager);
        Button requestChatBtn = view.findViewById(R.id.acceptUserBtn);
        CheckBox recommendLikeBox = view.findViewById(R.id.recommendLikeBox);
        TextView recommendUserName = view.findViewById(R.id.decisionUserName);

        CircleIndicator3 indicator = view.findViewById(R.id.indicator);
        indicator.setViewPager(recommendViewPager);
        indicator.createIndicators(2, 0);
        recommendViewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);

        recommendUserName.setText(userInfo.getName());
        recommendViewPager.setAdapter(new RecommendViewPagerAdapter((FragmentActivity) context,userInfo, true));
        recommendViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            //                        @Override
//                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                            super.onPageScrolled(position, positionOffset, positionOffsetPixels);
//                            if (positionOffsetPixels == 0) {
//                                recommendViewPager.setCurrentItem(position);
//                            }
//                        }
//
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                indicator.animatePageSelected(position % 2);
                if (position == 0) {
                    // indicator.setBackgroundColor(R.drawable.indicator_selected); // 선택된 페이지의 이미지
                } else {
                    // indicator.setBackgroundColor(R.drawable.indicator_default); // 나머지 페이지의 이미지
                }
            }
        });

        builder.setView(view);
        dialog = builder.create();
        //dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));
        dialog.show();


        requestChatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestChat(userInfo);
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        DatabaseReference likeRef = FirebaseDatabase.getInstance().getReference().child("users").child(userInfo.getUid()).child("like");
        recommendLikeBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Integer like = userInfo.getLike();
                if (isChecked) {
                    if (like == null || like == 0) {
                        likeRef.setValue(1);
                    } else {
                        like++;
                        likeRef.setValue(like);
                    }
                } else {
                    if (like == null || like == 0) {
                        likeRef.setValue(0);
                    } else {
                        likeRef.setValue(like);
                    }
                }

            }
        });


    }

    private void requestChat(UserInfo userInfo) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    /*    DatabaseReference usersMatchRef = FirebaseDatabase.getInstance().getReference().child("matches")
                .child("users").child(userInfo.getUid()).child("request").child(user.getUid());
        usersMatchRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {                //match상태가 request true/false, approved false 인 상태
                    Toast.makeText(context, "이미 요청한 상대입니다", Toast.LENGTH_SHORT).show();
                } else {
                    usersMatchRef.setValue(true);
                    Toast.makeText(context, "요청 완료", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onDataChange: 요청완료");
                    dialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
*/

        DatabaseReference myMatchRef = FirebaseDatabase.getInstance().getReference().child("matches")
                .child("users").child(userInfo.getUid()).child(user.getUid());
        myMatchRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Toast.makeText(context, "이미 요청한 상대입니다", Toast.LENGTH_SHORT).show();
                }else{
                    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
                    LocalDateTime currentTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
                    String currentTimeString = currentTime.format(dateTimeFormatter);
                    Match match=new Match(true,currentTimeString,null);
                    myMatchRef.setValue(match);
                    Toast.makeText(context, "요청 완료", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setRecommendTypeImage(ViewHolder holder, int position) {

        switch (recommendType) {
            case "이상형 추천":
                if (position < firstIdealUsers.size()) {
                    holder.crownImage.setVisibility(View.VISIBLE);
                    holder.heartImage1.setVisibility(View.VISIBLE);
                } else if (position >= firstIdealUsers.size() && position < firstIdealUsers.size() + secondIdealUsers.size()) {
                    holder.heartImage1.setVisibility(View.VISIBLE);
                }
                break;
            case "MBTI 추천":
                holder.newImage.setVisibility(View.VISIBLE);
                break;
            case "내 주변 친구":
                holder.onLocationImage.setVisibility(View.VISIBLE);
                break;
            case "새로 가입한 친구":
                holder.newImage.setVisibility(View.VISIBLE);
                break;
            case "오늘 접속한 친구":
                holder.onLineImage.setVisibility(View.VISIBLE);
                break;
            case "인기 있는 친구":
                holder.top5Image.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return recommendUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView recommendMatchImage;
        private TextView recommendMatchName;
        private TextView recommendMatchAge;

        private ImageView crownImage;
        private ImageView heartImage1;
        private ImageView onLineImage;
        private ImageView onLocationImage;
        private ImageView top5Image;
        private ImageView newImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recommendMatchImage = itemView.findViewById(R.id.recommendUserImage);
            recommendMatchName = itemView.findViewById(R.id.recommendMatchName);
            recommendMatchAge = itemView.findViewById(R.id.recommendMatchAge);

            crownImage = itemView.findViewById(R.id.crownImage);
            heartImage1 = itemView.findViewById(R.id.heartImage1);

            onLineImage = itemView.findViewById(R.id.onLineImage);
            onLocationImage = itemView.findViewById(R.id.onLocationImage);
            //top5Image = itemView.findViewById(R.id.top5Image);
            newImage = itemView.findViewById(R.id.newImage);

            crownImage.setVisibility(View.GONE);
            heartImage1.setVisibility(View.GONE);
            onLineImage.setVisibility(View.GONE);
            onLocationImage.setVisibility(View.GONE);
            //top5Image.setVisibility(View.GONE);
            newImage.setVisibility(View.GONE);

        }
    }
}
