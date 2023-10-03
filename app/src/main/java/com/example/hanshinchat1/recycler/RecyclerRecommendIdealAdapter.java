package com.example.hanshinchat1.recycler;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hanshinchat1.R;
import com.example.hanshinchat1.RecyclerChatRoomsAdapter;
import com.example.hanshinchat1.UserInfo;


import java.util.ArrayList;
import java.util.HashMap;

public class RecyclerRecommendIdealAdapter extends RecyclerView.Adapter<RecyclerRecommendIdealAdapter.ViewHolder>{
    Context context;
    ArrayList<UserInfo> firstIdealUsers;
    ArrayList<UserInfo> secondIdealUsers;
    ArrayList<UserInfo> thirdIdealUsers;
    private static final String TAG = "recommendIdealAdapter";
    public RecyclerRecommendIdealAdapter(Context context, ArrayList<UserInfo> firstIdealUsers,ArrayList<UserInfo> secondIdealUsers,ArrayList<UserInfo> thirdIdealUsers){
        this.context=context;
        this.firstIdealUsers=firstIdealUsers;
        this.secondIdealUsers=secondIdealUsers;
        this.thirdIdealUsers=thirdIdealUsers;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecyclerRecommendIdealAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_recommend_match, parent, false);
        return new RecyclerRecommendIdealAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerRecommendIdealAdapter.ViewHolder holder, int position) {


        if(!firstIdealUsers.isEmpty()){
            Uri imageUri = Uri.parse( firstIdealUsers.get(position).getPhotoUrl());
            Glide.with(context).load(imageUri).into(holder.recommendMatchImage);

            holder.recommendMatchImage.setImageURI(imageUri);
            holder.recommendMatchName.setText(firstIdealUsers.get(position).getName());
        }else if(!secondIdealUsers.isEmpty()){
            Uri imageUri = Uri.parse( secondIdealUsers.get(position).getPhotoUrl());
            Glide.with(context).load(imageUri).into(holder.recommendMatchImage);

            holder.recommendMatchImage.setImageURI(imageUri);
            holder.recommendMatchName.setText(secondIdealUsers.get(position).getName());
        }else{
            Uri imageUri = Uri.parse( thirdIdealUsers.get(position).getPhotoUrl());
            Glide.with(context).load(imageUri).into(holder.recommendMatchImage);

            holder.recommendMatchImage.setImageURI(imageUri);
            holder.recommendMatchName.setText(thirdIdealUsers.get(position).getName());
        }

    }

    @Override
    public int getItemCount() {
        return firstIdealUsers.size()+secondIdealUsers.size()+thirdIdealUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView recommendMatchImage;
        private TextView recommendMatchName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recommendMatchImage=itemView.findViewById(R.id.recommendMatchImage);
            recommendMatchName=itemView.findViewById(R.id.recommendMatchName);
        }
    }
}
