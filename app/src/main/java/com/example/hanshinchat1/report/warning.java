package com.example.hanshinchat1.report;

public class warning {
    private String userUid;
    private int warningCount;

    public warning() {
    }

    public warning(String userUid, int warningCount) {
        this.userUid = userUid;
        this.warningCount = warningCount;
    }

    public String getUserUid() {
        return userUid;
    }

    public int getWarningCount() {
        return warningCount;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public void setWarningCount(int warningCount) {
        this.warningCount = warningCount;
    }

}
