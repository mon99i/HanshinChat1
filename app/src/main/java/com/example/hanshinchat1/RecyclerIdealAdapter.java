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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class RecyclerIdealAdapter extends RecyclerView.Adapter<RecyclerIdealAdapter.ViewHolder> {

    private final static String TAG = "idealAdpater";
    private Context context;
    private int priority;
    private AlertDialog dialog;

    public static ArrayList<String> idealList = new ArrayList<>(
            Arrays.asList("나이", "거주지", "학과", "키", "체형", "흡연", "음주", "성격", "관심사", "종교")
    );
    private Map<String, String> idealMap;
    private ArrayList<String>   idealKeys = new ArrayList<>();
    private ArrayList<String>   idealValues= new ArrayList<>();


    public RecyclerIdealAdapter(Context context, int priority, Map<String, String> idealMap, AlertDialog dialog) {
        this.context = context;
        this.dialog=dialog;
        this.priority = priority;
        this.idealMap = idealMap;
        setupIdealList();

    }

    private void setupIdealList() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference priorityRef = FirebaseDatabase.getInstance().getReference().child("ideals")
                .child(user.getUid());
        priorityRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                idealKeys.clear();
                idealValues.clear();
                for (String key : idealMap.keySet()) {
                    idealKeys.add(key);
                    idealValues.add(idealMap.get(key));
                }  //일단 맵에있는 모든 address, 거주지를 list에 저장

                if (snapshot.exists()) {
                    for (DataSnapshot item : snapshot.getChildren()) {
                        for (DataSnapshot subItem : item.getChildren()) {
                            String key = subItem.getKey();
                            idealKeys.remove(key);
                            idealValues.remove(idealMap.get(key));

                        }
                    }
                }

                notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @NonNull
    @Override
    public RecyclerIdealAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //context
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_ideal_priority, parent, false);
        return new ViewHolder(view);    //RecyclerIdealAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerIdealAdapter.ViewHolder holder, int position) {

        final int currentPosition = holder.getAdapterPosition();
        holder.textView.setText(idealValues.get(currentPosition));
        holder.radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //버튼 모션
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                    dialog.dismiss();
                    }
                }, 300);


                Intent intent = new Intent(context, SetIdeal2Activity.class);
                intent.putExtra("priority", priority);
                intent.putExtra("selectedIdeal", idealKeys.get(currentPosition));
                context.startActivity(intent);
                ((AppCompatActivity) context).finish();

            }
        });


    }

    @Override
    public int getItemCount() {
        return idealValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        RadioButton radioButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.rowPriorityTxt);
            radioButton = itemView.findViewById(R.id.rowPriority_rdoBtn);
        }

    }
}
