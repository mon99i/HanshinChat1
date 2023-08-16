package com.example.hanshinchat1;

import java.util.HashMap;

public class Guest {

    private Boolean request;

    public Guest(){
        request=false;
        approved=false;
    };

    public Guest(Boolean request, Boolean approved) {
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
