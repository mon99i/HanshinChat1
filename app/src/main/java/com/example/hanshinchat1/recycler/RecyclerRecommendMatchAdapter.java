package com.example.hanshinchat1.recycler;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.hanshinchat1.Match.MbtiMatchActivity2;
import com.example.hanshinchat1.R;
import com.example.hanshinchat1.UserInfo;
import com.example.hanshinchat1.viewpager.RecommendViewPagerAdapter;


import java.lang.reflect.Array;
import java.util.ArrayList;

public class RecyclerRecommendMatchAdapter extends RecyclerView.Adapter<RecyclerRecommendMatchAdapter.ViewHolder> {
    private Context context;
    private String recommendType;

    //일반 추천
    private ArrayList<UserInfo> recommendUsers;

    //이상형추천
    private ArrayList<UserInfo> firstIdealUsers;
    private ArrayList<UserInfo> secondIdealUsers;
    private ArrayList<UserInfo> thirdIdealUsers;


    public RecyclerRecommendMatchAdapter(Context context, ArrayList<UserInfo> recommendUsers, String recommendType) {
        this.context = context;
        this.recommendUsers = recommendUsers;
        this.recommendType = recommendType;
    }

    public RecyclerRecommendMatchAdapter(Context context, String recommendType,ArrayList<UserInfo> firstIdealUsers,
                                         ArrayList<UserInfo> secondIdealUsers, ArrayList<UserInfo> thirdIdealUsers) {
        this.context = context;
        this.recommendType=recommendType;
        this.firstIdealUsers=firstIdealUsers;
        this.secondIdealUsers=secondIdealUsers;
        this.thirdIdealUsers=thirdIdealUsers;
        recommendUsers=new ArrayList<>();
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


        final int currentPosition= holder.getAbsoluteAdapterPosition();
        Uri imageUri = Uri.parse(recommendUsers.get(position).getPhotoUrl());
        Glide.with(context).load(imageUri).into(holder.recommendMatchImage);

        holder.recommendMatchImage.setImageURI(imageUri);
        holder.recommendMatchName.setText(recommendUsers.get(position).getName());
        holder.recommendMatchAge.setText(recommendUsers.get(position).getAge() + "세");


        setRecommendTypeImage(holder,position);

//        final int currentPosition = position;

        holder.recommendMatchImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showRecommendUserDialog(recommendUsers.get(currentPosition));
//                Intent intent = new Intent(context, MbtiMatchActivity2.class);
//                context.startActivity(intent);
            }
        });


    }

    private void showRecommendUserDialog(UserInfo userInfo){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.recommend_user_dialog, null);
        ViewPager2 recommendViewPager=view.findViewById(R.id.recommendViewPager);
        recommendViewPager.setAdapter(new RecommendViewPagerAdapter((FragmentActivity) context,userInfo));

        builder.setView(view);
        AlertDialog dialog = builder.create();
        //dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));
        dialog.show();

    }

    private void setRecommendTypeImage(ViewHolder holder, int position) {

        switch (recommendType) {
            case "이상형 추천":
                if(position<firstIdealUsers.size()){
                    holder.crownImage.setVisibility(View.VISIBLE);
                    holder.heartImage1.setVisibility(View.VISIBLE);
                }else if(position>=firstIdealUsers.size()&&position<firstIdealUsers.size()+secondIdealUsers.size()){
                    holder.heartImage1.setVisibility(View.VISIBLE);
                }
                break;
            case "MBTI 추천":
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
            recommendMatchImage = itemView.findViewById(R.id.recommendMatchImage);
            recommendMatchName = itemView.findViewById(R.id.recommendMatchName);
            recommendMatchAge = itemView.findViewById(R.id.recommendMatchAge);

            crownImage=itemView.findViewById(R.id.crownImage );
            heartImage1 = itemView.findViewById(R.id.heartImage1);

            onLineImage = itemView.findViewById(R.id.onLineImage);
            onLocationImage = itemView.findViewById(R.id.onLocationImage);
            //top5Image = itemView.findViewById(R.id.top5Image);
            newImage = itemView.findViewById(R.id.newImage);

            crownImage.setVisibility(View.GONE);
            heartImage1.setVisibility(View.GONE);
            onLineImage .setVisibility(View.GONE);
            onLocationImage .setVisibility(View.GONE);
            //top5Image.setVisibility(View.GONE);
            newImage.setVisibility(View.GONE);

        }
    }
}
