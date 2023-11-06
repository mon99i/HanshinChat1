package com.example.hanshinchat1.board;

import android.net.wifi.aware.PublishConfig;

import com.example.hanshinchat1.utils.FBAuth;

public class    ListViewItem {
    private String contentArea;
    private String titleArea;
    private String time;
    private String uid;
    private String name;

    public ListViewItem() {}

    public ListViewItem(String titleArea, String contentArea, String time, String uid) {
        this.titleArea = titleArea;
        this.contentArea = contentArea;
        this.time = time;
        this.uid = uid;
    }

    public ListViewItem(String titleArea, String contentArea, String time, String name, String uid) {
        this.titleArea = titleArea;
        this.contentArea = contentArea;
        this.time = time;
        this.name = name;
        this.uid = uid;
    }

    public String getTitle() {
        return titleArea;
    }

    public void setTitle(String titleArea) {
        this.titleArea = titleArea;
    }

    public String getContent() {
        return contentArea;
    }

    public void setContent(String contentArea) {
        this.contentArea = contentArea;
    }
    public String getTime() {
//        return FBAuth.getTime();
        return time;
    }
    public void setTime(String time) {
        this.time = time;
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
}
