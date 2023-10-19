package com.example.hanshinchat1;

public class Match {

    private Boolean request;
    private Boolean approved;
    private Boolean confirmed;

    public Match(){};
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

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

    public Match(Boolean request, Boolean approved, Boolean confirmed) {
        this.request = request;
        this.approved = approved;
        this.confirmed = confirmed;
    }
}
