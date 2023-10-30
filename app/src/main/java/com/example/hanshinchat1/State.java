package com.example.hanshinchat1;

import java.util.Map;

public class State {

    private Map<String,Boolean> request;
    private Map<String,Boolean> approved;
    private Map<String,String> request_date;

    public State(Map<String, Boolean> request, Map<String, Boolean> approved, Map<String, String> request_date) {
        this.request = request;
        this.approved = approved;
        this.request_date = request_date;
    }

    public State(){};
    public Map<String, String> getRequest_date() {
        return request_date;
    }

    public void setRequest_date(Map<String, String> request_date) {
        this.request_date = request_date;
    }


    public Map<String, Boolean> getRequest() {
        return request;
    }

    public void setRequest(Map<String, Boolean> request) {
        this.request = request;
    }

    public Map<String, Boolean> getApproved() {
        return approved;
    }

    public void setApproved(Map<String, Boolean> approved) {
        this.approved = approved;
    }


}
