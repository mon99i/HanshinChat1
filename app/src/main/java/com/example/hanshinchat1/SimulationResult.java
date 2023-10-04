package com.example.hanshinchat1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SimulationResult extends AppCompatActivity {

    private int totalScore;
    private String comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simulation_result);

        Button exitBtn = findViewById(R.id.simulation_exit);
        TextView textViewResultMessage = findViewById(R.id.result_message);
        TextView textViewTotalScore = findViewById(R.id.simul_result);

        totalScore = SimulationScoreManager.getTotalScore();

        String strScore = String.valueOf(totalScore);
        textViewTotalScore.setText(strScore + "점");

        if (totalScore > 0 && totalScore < 30) {
            comment = "아직은 이성의 마음을 잘 헤아리지 못한 것 같네요...\n이성에 대해서 공부하면 좋을 것 같아요!";
        } else if (totalScore >= 30 && totalScore < 60) {
            comment = "어느 정도 이성의 마음을 알기는 하지만 아직은....\n조금 더 공부하면 좋은 만남을 할 수 있을거 같아요!";
        } else if (totalScore >= 60 && totalScore < 90) {
            comment = "이성의 마음을 꽤나 잘 아시네요!\n좋은 인연을 만나시길 바랍니다!";
        } else if (totalScore >= 90 && totalScore <= 100) {
            comment = "이성의 마음을 완벽히 헤아리고 있군요!!\n이성과 만날 기회가 생기면 좋은 관계로\n발전 될 가능성이 높을 것 같아요!";
        } else {
            comment = "시스템 오류 상태 입니다.";
        }

        textViewResultMessage.setText(comment);

        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
