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

public class RecyclerRecommendMatchAdapter extends RecyclerView.Adapter<RecyclerRecommendMatchAdapter.ViewHolder>{
    Context context;
    ArrayList<UserInfo> recommendUsers;
    public RecyclerRecommendMatchAdapter(Context context, ArrayList<UserInfo> recommendUsers){
        this.context=context;
        this.recommendUsers=recommendUsers;
    }
    @NonNull
    @Override
    public RecyclerRecommendMatchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_recommend_match, parent, false);
        return new RecyclerRecommendMatchAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerRecommendMatchAdapter.ViewHolder holder, int position) {


        Uri imageUri = Uri.parse( recommendUsers.get(position).getPhotoUrl());
        Glide.with(context).load(imageUri).into(holder.recommendMatchImage);

        holder.recommendMatchImage.setImageURI(imageUri);
        holder.recommendMatchName.setText(recommendUsers.get(position).getName());

        holder.recommendMatchImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return recommendUsers.size();
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
