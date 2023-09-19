package com.example.hanshinchat1;

import java.util.HashMap;
import java.util.Map;

public class Ideal {

    private Map<String, Object> priority1;
    private Map<String, Object> priority2;
    private Map<String, Object> priority3;

    public Ideal(){
        priority1=new HashMap<>();
        priority2=new HashMap<>();
        priority3=new HashMap<>();
    };

    public Ideal(Map<String, Object> priority1, Map<String, Object> priority2, Map<String, Object> priority3) {
        this.priority1 = priority1;
        this.priority2 = priority2;
        this.priority3 = priority3;
    }

    public Map<String, Object> getPriority1() {
        return priority1;
    }

    public void setPriority1(Map<String, Object> priority1) {
        this.priority1 = priority1;
    }

    public Map<String, Object> getPriority2() {
        return priority2;
    }

    public void setPriority2(Map<String, Object> priority2) {
        this.priority2 = priority2;
    }

    public Map<String, Object> getPriority3() {
        return priority3;
    }

    public void setPriority3(Map<String, Object> priority3) {
        this.priority3 = priority3;
    }
}
