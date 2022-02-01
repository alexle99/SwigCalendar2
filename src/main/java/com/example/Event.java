package com.example;

import java.util.Calendar;

// Event class utilizes the built in Calendar class so it is easier to 
// implement the repeating feature. 

class Event {
    private String name;
    private Calendar startDate;
    private Calendar endDate;

    public Event(String n) {
        name = n;
        startDate = Calendar.getInstance();
        endDate = Calendar.getInstance();
    }

    public void setName(String n) {
        name = n;
    }

    public String getName() {
        return name;
    }

    public void setStartDate(String year, String month, String day, String hour, String minute) {
        startDate.set(integer(year), integer(month), integer(day), integer(hour), integer(minute));
    }

    public void setEndDate(String year, String month, String day, String hour, String minute) {
        endDate.set(integer(year), integer(month), integer(day), integer(hour), integer(minute));
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public void updateStartDate(int field, int value) {
        startDate.set(field, value);
    }

    private int integer(String s) {
        return Integer.parseInt(s);
    }

}