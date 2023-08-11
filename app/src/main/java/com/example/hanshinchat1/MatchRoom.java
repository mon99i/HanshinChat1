package com.example.hanshinchat1;

import java.util.HashMap;
import java.util.Map;

public class MatchRoom {

    protected String host;
    protected Map<String,Guest> guests;
    protected MatchInfo matchInfo;

    public MatchRoom(String host, Map<String, Guest> guests, MatchInfo matchInfo) {
        this.host = host;
        this.guests = guests;
        this.matchInfo = matchInfo;
    }
    public MatchRoom(){
        guests=new HashMap<>();
        matchInfo=new MatchInfo();
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Map<String, Guest> getGuests() {
        return guests;
    }

    public void setGuests(Map<String, Guest> guests) {
        this.guests = guests;
    }

    public MatchInfo getMatchInfo() {
        return matchInfo;
    }

    public void setMatchInfo(MatchInfo matchInfo) {
        this.matchInfo = matchInfo;
    }
}
