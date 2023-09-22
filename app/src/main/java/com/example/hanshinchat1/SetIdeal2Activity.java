package com.example.hanshinchat1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SetIdeal2Activity extends AppCompatActivity {
   /* private final int Fragment_1 = 1;
    private final String address="지역";*/

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

    Intent intent;

    Ideal getIdeal;

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

        idealSaveBtn = findViewById(R.id.idealSaveBtn);
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
      /*      case "department":
                return IdealAgeFragment.class;*/
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


      /*  FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        ArrayList<Object> interest = new ArrayList<>();
        interest.add("축구");
        interest.add("농구");
        interest.add("배구");

        Object address = idealValue;

        ArrayList<Object> personality = new ArrayList<>();
        personality.add("유쾌");
        personality.add("호탕");


        Map<String, Object> priority1 = new HashMap<>();
        priority1.put("interest", interest);

        Map<String, Object> priority2 = new HashMap<>();
        priority2.put("address", idealValue);

        Map<String, Object> priority3 = new HashMap<>();
        priority3.put("personality", personality);

        Ideal ideal = new Ideal();
        ideal.setPriority1(priority1);
        ideal.setPriority2(priority2);
        ideal.setPriority3(priority3);


        ArrayList<String> interests = new ArrayList<>();
        interests.add("배드민턴");
        interests.add("축구");
        interests.add("둘");
        interests.add("셋");
        interests.add("넷");
        interests.add("다섯");

        FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid())
                .child("interest").setValue(interests);


        FirebaseDatabase.getInstance().getReference().child("exIdeals").child(user.getUid())
                .setValue(ideal);

        //Map<String, Object> priorityMap = new HashMap<>();
        //매칭 클릭한다면
        FirebaseDatabase.getInstance().getReference().child("exIdeals").child(user.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        *//*for (DataSnapshot item : snapshot.getChildren()) {

                            for (DataSnapshot subItem : item.getChildren()) {

                                priorityMap.put(subItem.getKey(), subItem.getValue());
                                Log.d(TAG, "onDataChange: " + priorityMap);

                            }

                        }*//*


                        getIdeal = snapshot.getValue(Ideal.class);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        FirebaseDatabase.getInstance().getReference().child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot item : snapshot.getChildren()) {

                    for (DataSnapshot subItem : item.getChildren()) {
                        Log.d(TAG, "user별 정보 키 안뜸");


                        if (getIdeal.getPriority1().containsKey(subItem.getKey()) && subItem.getChildrenCount() > 1) {
                            Log.d(TAG, "user별 자식키가 하나인데 흥미키로들어감 에러!!");
                            ArrayList<Object> idealValues = (ArrayList<Object>) getIdeal.getPriority1().get(subItem.getKey());
                            ArrayList<Object> userValues = (ArrayList<Object>) subItem.getValue();
                            Log.d(TAG, "onDataChange: " + idealValues);
                            Log.d(TAG, "onDataChange: " + userValues);
                            for (Object object : idealValues) {
                                if (userValues.contains(object)) {
                                    Log.d(TAG, "내 이상형과 맞는 if문으로 들어옴 ");
                                    UserInfo userInfo = item.getValue(UserInfo.class);
                                    Log.d(TAG, "onDataChange: " + userInfo);
                                    Log.d(TAG, "onDataChange: " + object);
                                }
                            }


                        } else if (getIdeal.getPriority1().containsKey(subItem.getKey())) {
                            Log.d(TAG, "user별 흥미키안으로들어감");
                            if (subItem.getValue().equals(getIdeal.getPriority1().get(subItem.getKey()))) {
                                Log.d(TAG, "user별 흥미키안If문들어감");
                                UserInfo userInfo = item.getValue(UserInfo.class);
                                Log.d(TAG, "onDataChange: " + userInfo);
                            }
                        }


                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/













      /*  Object exObject=objectPersonality;

        Log.d(TAG, "goToSetIdealActivity: "+exObject);
        Log.d(TAG, "goToSetIdealActivity: "+exMap);


        ArrayList<Object> arrayList=(ArrayList<Object>)exMap.get("a");


        Log.d(TAG, "goToSetIdealActivity: "+arrayList);

        if(arrayList.contains(exObject)){
            Log.d(TAG, "goToSetIdealActivity: 포함");
        }else Log.d(TAG, "goToSetIdealActivity: 불포함");
*/

       /* ArrayList<Object> list1 = mapPersonality;
        ArrayList<Object> list2 = objectPersonality;

        boolean containsValue = false;

        for (Object item : list1) {
            if (list2.contains(item)) {
                containsValue = true;
                break; // 포함된 값이 하나라도 있으면 루프 종료
            }
        }

        if (containsValue) {
            System.out.println("두 리스트에서 공통 값이 포함되어 있습니다.");
        } else {
            System.out.println("두 리스트에서 공통 값이 포함되어 있지 않습니다.");
        }*/


    }


    public void onValueReceived(Object value) {
        this.idealValue = value;
    }
}