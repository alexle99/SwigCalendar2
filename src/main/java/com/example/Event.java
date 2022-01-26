package com.example;

import java.util.ArrayList;

/*
    Time stored as "yyyy-mm-dd hh:mm:ss"
*/

class Event {
    private String name;
    private String startDate;
    private String endDate;
    private ArrayList<String> guests;
    private Boolean repeat;

    public Event(String n, String startT, String endT) {
        name = n;
        startDate = startT;
        endDate = endT;
        guests = new ArrayList<String>();
        repeat = false;
    }

    public void setName(String n) {
        name = n;
    }

    public String getName() {
        return name;
    }

    public void setStartDate(String d) {
        startDate = d;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setEndDate(String d) {
        endDate = d;
    }

    public String getEndDate() {
        return endDate;
    }

    public void addGuest(String guest) {
        guests.add(guest);
    }

    public ArrayList<String> getGuests() {
        return guests;
    }

    public void repeatWeekly(Boolean b) {
        repeat = b;
    }

    public Boolean getRepeatWeekly() {
        return repeat;
    }

}