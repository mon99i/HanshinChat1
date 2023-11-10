package com.example.hanshinchat1;

import java.io.Serializable;

public class Message implements Serializable {
    public String senderUid;
    public String sended_date;
    public String content;
    public boolean confirmed = false;

    public Message(String senderUid, String sended_date, String content) {
        this.senderUid = senderUid;
        this.sended_date = sended_date;
        this.content = content;

    }

    public Message() {
        this.senderUid = "";
        this.sended_date = "";
        this.content = "";
        this.confirmed = false;
    }

    public String getSenderUid() {
        return senderUid;
    }

    public void setSenderUid(String senderUid) {
        this.senderUid = senderUid;
    }

    public String getSended_date() {
        return sended_date;
    }

    public void setSended_date(String sended_date) {
        this.sended_date = sended_date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }
}
