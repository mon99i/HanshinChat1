package com.example.hanshinchat1.report;

public class reportInfo {
    private String name;
    private String username;
    private String writeReport;
    private String time;
    private String uid;

    public reportInfo() {}

    public reportInfo(String name, String username, String writeReport, String time, String uid) {
        this.name = name;
        this.username = username;
        this.writeReport = writeReport;
        this.time = time;
        this.uid = uid;
    }

    public String getName() { return name;}

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getWriteReport() {
        return writeReport;
    }

    public void setWriteReport(String writeReport) {
        this.writeReport = writeReport;
    }

    public String getTime() {
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
}
