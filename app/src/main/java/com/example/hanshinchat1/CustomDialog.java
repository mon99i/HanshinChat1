package com.example.hanshinchat1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hanshinchat1.recycler.VerticalDecoration;

import java.util.Map;

public class CustomDialog extends Dialog {
    private Context context;

   // public static CustomDialog customDialog;
    int priority;
    private AlertDialog.Builder builder;
    private LayoutInflater inflater;
    public static AlertDialog dialog;

    public CustomDialog(@NonNull Context context) {
        super(context);
        this.context=context;
        builder=new AlertDialog.Builder(context);
        inflater = LayoutInflater.from(context);
    }



    /* public CustomDialog(@NonNull Context context,int priority) {
         super(context);
         this.context = context;
         this.priority=priority;
     }

     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.ideal_prioity_dialog);  // 커스텀 다이얼로그 레이아웃 파일을 설정

         RecyclerView recyclerView = findViewById(R.id.recycler_idealPriority);
         recyclerView.setAdapter(new RecyclerIdealAdapter(context,priority));
         recyclerView.setLayoutManager(new LinearLayoutManager(context));

         // 리사이클러뷰에 어댑터 설정



     }
 */
  /*  public static CustomDialog getInstance(Context context){
        customDialog=new CustomDialog(context);
        LayoutInflater inflater = LayoutInflater.from(context);

        return customDialog;
    }*/


    public Dialog priorityDialog(int priority, Map<String,String>idealMap){
        View view=inflater.inflate(R.layout.ideal_prioity_dialog, null);
        RecyclerView recyclerView=view.findViewById(R.id.recycler_idealPriority);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new VerticalDecoration(20));
        recyclerView.setAdapter(new RecyclerIdealAdapter(context,priority,idealMap));


        builder.setView(view);
        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));
        return dialog;



       /* alertDialog.getWindow().setGravity(Gravity.TOP); //상단에 위치
        alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);  //밖에 배경 어둡지않게
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));  // 배경 투명하게
        //alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.show(); */


    }


}
