package com.example;

import java.util.ArrayList;
import java.util.Calendar;

class Event {
    private String name;
    private Calendar startDate;
    private Calendar endDate;
    private ArrayList<String> guests;
    private Boolean repeat;

    public Event(String n) {
        name = n;
        startDate = Calendar.getInstance();
        endDate = Calendar.getInstance();
        guests = new ArrayList<String>();
        repeat = false;
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

    private int integer(String s) {
        return Integer.parseInt(s);
    }

}