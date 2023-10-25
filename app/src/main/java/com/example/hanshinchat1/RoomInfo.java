package com.example.hanshinchat1;

import java.io.Serializable;

public class RoomInfo implements Serializable {
    private String title;
    private String category;
    private String num;
    private String department;
    private String host;
    private String gender;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public RoomInfo(String host, String title, String category, String num, String department, String gender) {
        this.title = title;
        this.category = category;
        this.num = num;
        this.department = department;
        this.gender = gender;
        this.host=host;
    }
    public RoomInfo(){};

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


}
