package com.example.hanshinchat1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.hanshinchat1.fragment.IdealAddressFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SetIdeal2Activity extends AppCompatActivity {
   /* private final int Fragment_1 = 1;
    private final String address="지역";*/

    private static final String TAG = "SetIdeal2Activity";
    private int priority;
    private String selectedIdeal;
    private Object value;

    Intent intent;
    private Button idealSaveBtn;
    private ImageButton idealBackBtn;
    private Map<String,Object> priority1;
    private Map<String,Object> priority2;
    private Map<String,Object> priority3;
    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_ideal2);
        initializeView();
        FragmentView();
        initializeListener();
    }

    private void initializeView(){
        priority=getIntent().getIntExtra("priority",0);
        selectedIdeal= getIntent().getStringExtra("selectedIdeal");
        Log.d(TAG, "initializeView: priority"+priority);
        Log.d(TAG, "initializeView: ideal"+selectedIdeal);
        idealSaveBtn=findViewById(R.id.idealSaveBtn);
        idealBackBtn=findViewById(R.id.idealBackBtn);


        priority1=new HashMap<>();
        priority2=new HashMap<>();
        priority3=new HashMap<>();

    }

    private void FragmentView(){
        //FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch(selectedIdeal){
            case "거주지":
                selectedIdeal="address";
                IdealAddressFragment idealAddressFragment = new IdealAddressFragment();
                transaction.add(R.id.fragment_container_ideal,idealAddressFragment);
                transaction.commit();
                break;
            case "나이":
                selectedIdeal="age";

              /*  transaction.add(R.id.fragment_container_ideal,IdealAgeFragment.newInstance());
                transaction.commit();*/
                break;

            case "학과":
                selectedIdeal="department";
                Log.d(TAG, "학과");
              /*  transaction.add(R.id.fragment_container_ideal,IdealDepartmentFragment.newInstance());
                transaction.commit();*/
                break;

            case "음주":
                selectedIdeal="drinking";
                Log.d(TAG, "음주");
               /* transaction.add(R.id.fragment_container_ideal,IdealDrinkingFragment.newInstance());
                transaction.commit();*/

                break;
            /*case "체형":
                transaction.add(R.id.fragment_container_ideal,IdealFormFragment.newInstance());
                transaction.commit();
                break;

            case "키":
                transaction.add(R.id.fragment_container_ideal,IdealHeightFragment.newInstance());
                transaction.commit();
                break;

            case "관심사":
                transaction.add(R.id.fragment_container_ideal,IdealInterestFragment.newInstance());
                transaction.commit();
                break;

            case "성격":
                transaction.add(R.id.fragment_container_ideal,IdealPersonalityFragment.newInstance());
                transaction.commit();
                break;

            case "종교":
                transaction.add(R.id.fragment_container_ideal,IdealReligionFragment.newInstance());
                transaction.commit();
                break;

            case "흡연":
                transaction.add(R.id.fragment_container_ideal,IdealSmokingFragment.newInstance());
                transaction.commit();
                break;
*/


        }

    }

    private void initializeListener(){
        idealSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               updateDB();

            }
        });

        idealBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSetIdealActivity();
            }
        });
    }

    private void updateDB(){
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference idealRef=FirebaseDatabase.getInstance().getReference().child("ideals").child(user.getUid());
     /*   priority2.put(selectedIdeal,value);
        idealRef.child("priority2").setValue(priority2);
        switch(priority){
            case 1:
                priority1.put(selectedIdeal,value);
                ideal.setPriority1(priority1);
                updateDB(ideal);
                Log.d(TAG, "onClick: ideal생성완료");
                Log.d(TAG, "onClick: "+value);
                break;
            case 2:
                priority2.put(selectedIdeal,value);
                ideal.setPriority1(priority2);
                Log.d(TAG, "onClick: ideal생성완료");
                Log.d(TAG, "onClick: "+value);
                break;
            case 3:
                priority3.put(selectedIdeal,value);
                ideal.setPriority1(priority3);
                break;
        }
*/
        idealRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        Ideal ideal;
                        if(snapshot.exists()){
                            ideal=snapshot.getValue(Ideal.class);
                            Log.d(TAG, "onDataChange: 이상형이미있음");
                        }else{                    // 최초 이상형 설정 생성
                            ideal=new Ideal();
                            Log.d(TAG, "onDataChange: 이상형새로생성");
                        }

                        switch(priority){
                            case 1:
                                priority1.put(selectedIdeal,value);
                                ideal.setPriority1(priority1);
                                idealRef.setValue(ideal);
                                Log.d(TAG, "onClick: ideal생성완료");
                                goToSetIdealActivity();
                                break;
                            case 2:
                                priority2.put(selectedIdeal,value);
                                ideal.setPriority2(priority2);
                                idealRef.setValue(ideal);
                                Log.d(TAG, "onClick: ideal2생성완료");
                                goToSetIdealActivity();
                                break;
                            case 3:
                                priority3.put(selectedIdeal,value);
                                ideal.setPriority3(priority3);
                                idealRef.setValue(ideal);
                                goToSetIdealActivity();
                                break;
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }

    private void goToSetIdealActivity(){
        Intent intent=new Intent(getApplicationContext(),SetIdealActivity.class);
        startActivity(intent);
        finish();
    }


    public void onValueReceived(Object value) {
        this.value=value;
    }
}