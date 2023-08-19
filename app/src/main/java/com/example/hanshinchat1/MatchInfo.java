package com.example.hanshinchat1;

public class MatchInfo {

    private Boolean request;

    public MatchInfo(){
        request=false;
        approved=false;
    };


    public MatchInfo(Boolean request, Boolean approved) {
        this.request = request;
        this.approved = approved;
    }

    public Boolean getRequest() {
        return request;
    }

    public void setRequest(Boolean request) {
        this.request = request;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    private Boolean approved;
}
