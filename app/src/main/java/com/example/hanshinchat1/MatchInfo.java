package com.example.hanshinchat1;

import java.io.Serializable;

public class MatchInfo implements Serializable {

    private Boolean confirmed;

    private Boolean request;

    public MatchInfo(){
        request=false;
        approved=false;
        confirmed=false;
    };

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

    public MatchInfo(Boolean request, Boolean approved, Boolean confirmed) {
        this.request = request;
        this.approved = approved;
        this.confirmed= confirmed;
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
