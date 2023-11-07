package com.example.hanshinchat1;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Match implements Serializable {

    private String match_key;
    private String sender_uid;
    private Boolean request;
    private String request_date;
    private Boolean approved;

    public String getMatch_key() {
        return match_key;
    }

    public void setMatch_key(String match_key) {
        this.match_key = match_key;
    }

    public String getSender_uid() {
        return sender_uid;
    }

    public void setSender_uid(String sender_uid) {
        this.sender_uid = sender_uid;
    }

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
