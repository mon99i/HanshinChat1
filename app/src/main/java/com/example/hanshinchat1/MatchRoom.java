package com.example.hanshinchat1;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class MatchRoom implements Serializable {

    private RoomInfo roomInfo;

    private Map<String,MatchInfo> matchInfo;


    public MatchRoom(){};

    public RoomInfo getRoomInfo() {
        return roomInfo;
    }

    public void setRoomInfo(RoomInfo roomInfo) {
        this.roomInfo = roomInfo;
    }

    public Map<String, MatchInfo> getMatchInfo() {
        return matchInfo;
    }

    public void setMatchInfo(Map<String, MatchInfo> matchInfo) {
        this.matchInfo = matchInfo;
    }

    public MatchRoom(RoomInfo roomInfo, Map<String, MatchInfo> matchInfo) {
        this.roomInfo = roomInfo;
        this.matchInfo = matchInfo;
    }
}
