package com.example.hanshinchat1;

import static com.example.hanshinchat1.HomeActivity.NOTIFICATION_ID;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationManagerCompat;

public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action=intent.getAction();
        if("ACTION_ACCEPT".equals(action)){
            updateMatchingStatus(true);

        } else if ("ACTION_DECLINE".equals(action)) {
            // "거절" 버튼을 클릭한 경우 처리할 작업
            updateMatchingStatus(false); // 매칭 상태를 거절로 변경
        }

        // 알림을 클릭한 후 알림을 제거하려면 다음 코드를 추가할 수 있습니다.
     /*   NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.cancel(NOTIFICATION_ID);*/
    }

    // 매칭 상태를 변경하는 메서드
    private void updateMatchingStatus(boolean accepted) {
        // 여기서 매칭 상태를 변경하거나 특정 작업 수행
        // 예를 들어, 데이터베이스 업데이트 또는 특정 서비스 호출 등을 수행할 수 있습니다.
    }


}
