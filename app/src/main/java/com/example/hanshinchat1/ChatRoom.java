package com.example.hanshinchat1;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ChatRoom implements Serializable {
    public Map<String, Boolean> users;
    public Map<String, Message> messages;

    public ChatRoom() {
        users = new HashMap<>();
        messages = new HashMap<>();
    }

    public ChatRoom(Map<String, Boolean> users, Map<String, Message> messages) {
        this.users = users;
        this.messages = messages;
    }

    public Map<String, Boolean> getUsers() {
        return users;
    }

    public void setUsers(Map<String, Boolean> users) {
        this.users = users;
    }

    public Map<String, Message> getMessages() {
        return messages;
    }

    public void setMessages(Map<String, Message> messages) {
        this.messages = messages;
    }
}
