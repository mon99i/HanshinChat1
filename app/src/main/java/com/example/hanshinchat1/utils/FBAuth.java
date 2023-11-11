package com.example.hanshinchat1.utils;

import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FBAuth {
    private static FirebaseAuth auth;

    public static String getUid() {
        auth = FirebaseAuth.getInstance();
        return auth.getCurrentUser() != null ? auth.getCurrentUser().getUid() : "";
    }

    public static String getName() {
        auth = FirebaseAuth.getInstance();
        return auth.getCurrentUser() != null ? auth.getCurrentUser().getDisplayName() : "";
    }

    public static String getTime() {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd HH:mm");
        LocalDateTime currentTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        String currentTimeString = currentTime.format(dateTimeFormatter);

        return currentTimeString;
    }
}
