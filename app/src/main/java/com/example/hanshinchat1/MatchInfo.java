package com.example.hanshinchat1;

public class MatchInfo {


    protected String title;
    protected String category;
    protected String num;
    protected String department;

    public MatchInfo(String title, String category, String num, String department, String gender) {
        this.title = title;
        this.category = category;
        this.num = num;
        this.department = department;
        this.gender = gender;
    }
    public MatchInfo(){};

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    protected String gender;
}
