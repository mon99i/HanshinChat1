package com.example.hanshinchat1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.hanshinchat1.fragment.IdealAddressFragment;
import com.example.hanshinchat1.fragment.IdealAgeFragment;
import com.example.hanshinchat1.fragment.IdealDrinkingFragment;
import com.example.hanshinchat1.fragment.IdealFormFragment;
import com.example.hanshinchat1.fragment.IdealHeightFragment;
import com.example.hanshinchat1.fragment.IdealInterestFragment;
import com.example.hanshinchat1.fragment.IdealPersonalityFragment;
import com.example.hanshinchat1.fragment.IdealReligionFragment;
import com.example.hanshinchat1.fragment.IdealSmokingFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class SetIdeal2Activity extends AppCompatActivity {

    private static final String TAG = "SetIdeal2Activity";
    private int priority;
    private String selectedIdeal;
    private Object idealValue;
    private Button idealSaveBtn;
    private ImageButton idealBackBtn;
    private Map<String, Object> priority1;
    private Map<String, Object> priority2;
    private Map<String, Object> priority3;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_ideal2);

        initializeView();
        initializeListener();
    }

    private void initializeView() {
        priority = getIntent().getIntExtra("priority", 0);
        selectedIdeal = getIntent().getStringExtra("selectedIdeal");
        Log.d(TAG, "initializeView: priority" + priority);
        Log.d(TAG, "initializeView: ideal" + selectedIdeal);

        transaction = getSupportFragmentManager().beginTransaction();
        Class<?> idealFragment= getIdealFragment(selectedIdeal);
        if (idealFragment != null) {
            try {
                transaction.add(R.id.fragment_container_ideal, (Fragment)idealFragment.newInstance());
                transaction.commit();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }else Log.d(TAG, "받은 fragment가 없음");

        idealSaveBtn = findViewById(R.id.chatRequestBtn);
        idealBackBtn = findViewById(R.id.idealBackBtn);
        priority1 = new HashMap<>();
        priority2 = new HashMap<>();
        priority3 = new HashMap<>();

    }

    private Class<?> getIdealFragment(String selectedIdeal) {

        switch (selectedIdeal) {
            case "address":
                return IdealAddressFragment.class;
            case "age":
                return IdealAgeFragment.class;
            case "drinking":
                return IdealDrinkingFragment.class;
            case "form":
                return IdealFormFragment.class;
            case "height":
                return IdealHeightFragment.class;
            case "interest":
                return IdealInterestFragment.class;
            case "personality":
                return IdealPersonalityFragment.class;
            case "religion":
                return IdealReligionFragment.class;
            case "smoking":
                return IdealSmokingFragment.class;
            default:
               return null;

        }
    }

    private void initializeListener() {
        idealSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(idealValue!=null){
                    updateDB();
                }
            }
        });

        idealBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSetIdealActivity();
            }
        });
    }

    private void updateDB() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference idealRef = FirebaseDatabase.getInstance().getReference().child("ideals").child(user.getUid());
        idealRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Ideal ideal;
                if (snapshot.exists()) {
                    ideal = snapshot.getValue(Ideal.class);
                    Log.d(TAG, "onDataChange: 이상형이미있음");
                } else {                    // 최초 이상형 설정 생성
                    ideal = new Ideal();
                    Log.d(TAG, "onDataChange: 이상형새로생성");
                }

                switch (priority) {
                    case 1:
                        priority1.put(selectedIdeal, idealValue);
                        ideal.setPriority1(priority1);
                        idealRef.setValue(ideal);
                        Log.d(TAG, "onClick: ideal생성완료");
                        goToSetIdealActivity();
                        break;
                    case 2:
                        priority2.put(selectedIdeal, idealValue);
                        ideal.setPriority2(priority2);
                        idealRef.setValue(ideal);
                        Log.d(TAG, "onClick: ideal2생성완료");
                        goToSetIdealActivity();
                        break;
                    case 3:
                        priority3.put(selectedIdeal, idealValue);
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

    private void goToSetIdealActivity() {
        Intent intent = new Intent(getApplicationContext(), SetIdealActivity.class);
        startActivity(intent);
        finish();
    }

    public void onValueReceived(Object value) {
        this.idealValue = value;
    }
}