package com.example.hanshinchat1;

import java.io.Serializable;
import java.util.ArrayList;

public class UserInfo implements Serializable{

    public UserInfo(){}

    public String getLastSignInTime() {
        return lastSignInTime;
    }

    public void setLastSignInTime(String lastSignInTime) {
        this.lastSignInTime = lastSignInTime;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public UserInfo(String uid, String photoUrl, String name, String gender, Integer age, Integer grade,
                    Integer studentId, String department, Integer height, String form, String address,
                    String religion, String smoking, String drinking, ArrayList<String> interest,
                    ArrayList<String> personality, String mbti, String idealTypeFirst, String idealTypeSecond)
    {
        this.uid = uid;
        this.photoUrl = photoUrl;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.grade = grade;
        this.studentId = studentId;
        this.department = department;
        this.height = height;
        this.form = form;
        this.address = address;
        this.religion = religion;
        this.smoking = smoking;
        this.drinking = drinking;
        this.interest = interest;
        this.personality = personality;
        this.mbti = mbti;
        this.idealTypeFirst = idealTypeFirst;
        this.idealTypeSecond = idealTypeSecond;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSmoking() {
        return smoking;
    }

    public void setSmoking(String smoking) {
        this.smoking = smoking;
    }

    public String getDrinking() {
        return drinking;
    }

    public void setDrinking(String drinking) {
        this.drinking = drinking;
    }

    public ArrayList<String> getInterest() {
        return interest;
    }

    public void setInterest(ArrayList<String> interest) {
        this.interest = interest;
    }

    public ArrayList<String> getPersonality() {
        return personality;
    }

    public void setPersonality(ArrayList<String> personality) {
        this.personality = personality;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getMbti() {
        return mbti;
    }

    public void setMbti(String mbti) {
        this.mbti = mbti;
    }

    public String getIdealTypeFirst() {
        return idealTypeFirst;
    }

    public void setIdealTypeFirst(String idealTypeFirst) {
        this.idealTypeFirst = idealTypeFirst;
    }

    public String getIdealTypeSecond() {
        return idealTypeSecond;
    }

    public void setIdealTypeSecond(String idealTypeSecond) {
        this.idealTypeSecond = idealTypeSecond;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
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
    private ArrayList<String> interest;
    private ArrayList<String> personality;
    private String form;
    private Integer grade;
    private String mbti;
    private String idealTypeFirst;
    private String idealTypeSecond;
    private String photoUrl;

    private String lastSignInTime;
    private String creationTime;



}
