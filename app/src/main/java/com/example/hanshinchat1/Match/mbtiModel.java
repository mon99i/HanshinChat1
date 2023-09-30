package com.example.hanshinchat1.Match;

public class mbtiModel {

    private int age;
    private String department;
    private String mbti;

    public mbtiModel() {}

    public mbtiModel(int age, String department, String mbti) {
        this.age = age;
        this.department = department;
        this.mbti = mbti;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getMbti() {
        return mbti;
    }

    public void setMbti(String mbti) {
        this.mbti = mbti;
    }
}
