package com.example.hanshinchat1;

import android.net.Uri;
import java.io.Serializable;

public class UserInfo implements Serializable{

    public UserInfo(){}

    public UserInfo(String name, String gender, int age, int studentId, String department, int height, String religion, String address, String smoking, String drinking, String hobby, String mbti, String displayName, String major, String photoUrl) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.studentId = studentId;
        this.department = department;
        this.height = height;
        this.religion = religion;
        this.address = address;
        this.smoking = smoking;
        this.drinking = drinking;

        this.hobby = hobby;
        this.mbti = mbti;
        this.displayName = displayName;
        this.major = major;
        this.photoUrl = photoUrl;
    }

    private String uid;
    private String name;
    private String gender;
    private Integer age;
    private Integer studentId;
    private String department;
    private Integer height;
    private String religion;
    private String address;
    private String smoking;
    private String drinking;

    private String hobby;
    private String mbti;

    private String displayName;

    private String major;

    private static String photoUrl;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
    public String getName() { return name; }
    public void setName(String name) {
        this.name = name;
    }

    public String getGender() { return gender; }
    public void setGender(String gender){
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getDepartment() { return department; }

    public void setDepartment(String department){
        this.department = department;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(int height) { this.height = height; }

    public String getReligion() { return religion; }

    public void setReligion(String religion){
        this.religion = religion;
    }

    public String getAddress() { return address; }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSmoking() { return smoking; }
    public void setSmoking(String smoking){ this.smoking = smoking; }

    public String getDrinking() { return drinking; }
    public void setDrinking(String drinking) { this.drinking = drinking; }


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
