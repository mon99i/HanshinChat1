package com.example.hanshinchat1;

import java.util.HashMap;
import java.util.Map;

public class Ideal {
    private Map<String, String> priority1;
    private Map<String, String> priority2;
    private Map<String, String> priority3;

    public Ideal(){
        priority1=new HashMap<>();
        priority2=new HashMap<>();
        priority3=new HashMap<>();
    };

    public Ideal(Map<String, String> priority1, Map<String, String> priority2, Map<String, String> priority3) {
        this.priority1 = priority1;
        this.priority2 = priority2;
        this.priority3 = priority3;
    }

    public Map<String, String> getPriority1() {
        return priority1;
    }

    public void setPriority1(Map<String, String> priority1) {
        this.priority1 = priority1;
    }

    public Map<String, String> getPriority2() {
        return priority2;
    }

    public void setPriority2(Map<String, String> priority2) {
        this.priority2 = priority2;
    }

    public Map<String, String> getPriority3() {
        return priority3;
    }

    public void setPriority3(Map<String, String> priority3) {
        this.priority3 = priority3;
    }
}
