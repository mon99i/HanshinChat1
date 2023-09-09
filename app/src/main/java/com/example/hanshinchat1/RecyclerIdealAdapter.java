package com.example.hanshinchat1;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

public class RecyclerIdealAdapter extends RecyclerView.Adapter<RecyclerIdealAdapter.ViewHolder> {

   Context context;
   SetIdealActivity activity;
    ArrayList<String> idealList =new ArrayList<>(
            Arrays.asList("나이","거주지","학과","키","체형","흡연","음주","성격","관심사","종교")
            );
    AlertDialog alertDialog;
 /*   public RecyclerIdealAdapter(ArrayList<String> idealList){
        this.idealList=idealList;

    };*/



    public RecyclerIdealAdapter(Context context,AlertDialog alertDialog){
        this.context=context;
        this.alertDialog=alertDialog;

    }

    @NonNull
    @Override
    public RecyclerIdealAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //context
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.row_ideal_priority,parent,false);
        return new ViewHolder(view);    //RecyclerIdealAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerIdealAdapter.ViewHolder holder, int position) {


        final int currentPosition = holder.getAdapterPosition();
        holder.textView.setText(idealList.get(position));
        holder.radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //버튼 모션
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (alertDialog != null && alertDialog.isShowing()) {
                            alertDialog.dismiss();
                        }
                    }
                }, 300);

             /*   activity.
                Intent intent=new Intent(context, SetIdealActivity.class);
                intent.putExtra("idealPriority",holder.textView.getText());
                context.startActivity(intent);

*/


               /* ((AppCompatActivity) context).finish();

                if (alertDialog != null && alertDialog.isShowing()) {
                    alertDialog.dismiss(); // AlertDialog 닫기
                }*/
            }
        });


    }

    @Override
    public int getItemCount() {
        return idealList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        RadioButton radioButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.rowPriorityTxt);
            radioButton=itemView.findViewById(R.id.rowPriority_rdoBtn);
        }
        // ViewHolder 클래스 정의 및 뷰 객체 관리
    }
}
