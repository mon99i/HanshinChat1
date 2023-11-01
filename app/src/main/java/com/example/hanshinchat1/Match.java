package com.example.hanshinchat1;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Match implements Serializable {

    private Boolean request;
    private String request_date;
    private Boolean approved;

    public Match(){};
    public Match(Boolean request, String request_date, Boolean approved) {
        this.request = request;
        this.request_date = request_date;
        this.approved = approved;
    }

    public Boolean getRequest() {
        return request;
    }

    public void setRequest(Boolean request) {
        this.request = request;
    }

    public String getRequest_date() {
        return request_date;
    }

    public void setRequest_date(String request_date) {
        this.request_date = request_date;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }
}
