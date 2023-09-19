package com.example.hanshinchat1;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SimulationResult extends AppCompatActivity {
    private int totalScore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simulation_result);

        showResult();
        showComment();

        Button exitBtn = (Button) findViewById(R.id.simulation_exit);

        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void showResult() {
        Intent intent = getIntent();
        totalScore = intent.getIntExtra("totalScore", totalScore);

        EditText editTextResult = findViewById(R.id.simul_result);
        editTextResult.setText(String.valueOf(totalScore));
        editTextResult.setGravity(Gravity.CENTER);
    }

    private void showComment() {
        String comment;
        if (totalScore > 0 && totalScore < 30) {
            comment = "아직은 이성의 마음을 잘 헤아리지 못한거같네요...조금만 이성에 대해서 공부하면 좋을거 같아요!";
        } else if (totalScore >= 30 && totalScore < 60) {
            comment = "어느정도 이성의 마음을 알기는 하지만 아직은....더 공부하면 더 좋은 만남을 할수 있을거 같아요!";
        } else if (totalScore >= 60 && totalScore < 90) {
            comment = "이성의 마음을 잘 알고 계시네요! 좋은 인연을 만나길 기도드립니다!";
        } else if (totalScore >= 90 && totalScore <= 100) {
            comment = "이성의 마음을 완벽히 헤아리고 있군요!! 이성과 만날 기회가 주어진다면 좋은 관계로 발전 될 가능성이 높을거 같아요!";
        } else{
            comment = "시스템 오류 상태 입니다.";
        }

        TextView textViewResultMessage = findViewById(R.id.result_message);
        textViewResultMessage.setText(comment);

    }
}