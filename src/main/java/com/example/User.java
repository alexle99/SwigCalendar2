package com.example;

public class User {
    private String name;
    private int timeZone;

    public User(String n) {
        name = n;
        timeZone = 0;
    }

    public void setName(String n) {
        name = n;
    }

    public String getName() {
        return name;
    }

    public void setTimeZone(int i) {
        timeZone = i;
    }

    public int getTimeZone() {
        return timeZone;
    }

}
