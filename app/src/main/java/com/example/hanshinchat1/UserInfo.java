package com.example.hanshinchat1;

import android.net.Uri;

public class UserInfo {

    public UserInfo(){}

    public UserInfo(String nickName, String hobby, String mbti, String displayName, int age, String major, String photoUrl) {
        this.nickName = nickName;
        this.hobby = hobby;
        this.mbti = mbti;
        this.displayName = displayName;
        this.age = age;
        this.major = major;
        this.photoUrl = photoUrl;
    }

    private String nickName;
    private String hobby;
    private String mbti;

    private String displayName;

    private int age;

    private String major;

    private static String photoUrl;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getMbti() {
        return mbti;
    }

    public void setMbti(String mbti) {
        this.mbti = mbti;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

}
