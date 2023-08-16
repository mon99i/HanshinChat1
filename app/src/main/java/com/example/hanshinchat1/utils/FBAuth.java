package com.example.hanshinchat1.utils;

import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FBAuth {
    private static FirebaseAuth auth;

    public static String getUid() {
        auth = FirebaseAuth.getInstance();
        return auth.getCurrentUser() != null ? auth.getCurrentUser().getUid() : "";
    }
    public static String getTime() {
        Date currentDateTime = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss ", Locale.KOREA);
        String formattedDate = dateFormat.format(currentDateTime);

        return formattedDate;

    }
}
