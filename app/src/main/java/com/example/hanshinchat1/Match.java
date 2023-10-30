package com.example.hanshinchat1;

import java.util.HashMap;
import java.util.Map;

public class Match {

    private Map<String, State> users;
    private Map<String, State> rooms;


    public Match(Map<String, State> users, Map<String, State> rooms) {
        this.users = new HashMap<>();
        this.rooms = new HashMap<>();
    }

    public Map<String, State> getUsers() {
        return users;
    }

    public void setUsers(Map<String, State> users) {
        this.users = users;
    }

    public Map<String, State> getRooms() {
        return rooms;
    }

    public void setRooms(Map<String, State> rooms) {
        this.rooms = rooms;
    }

    public Match(){};

}
