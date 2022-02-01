package com.example;

import java.util.ArrayList;

// the events of the CalendarClass will be stored in a mutable list of Events
// In the future, if I had to implement undoing feature, I would use a 
// immutable list and store each event as a history

public class CalendarClass {
    private String name;
    private ArrayList<Event> events;
    Boolean publicView;

    public CalendarClass(String n) {
        name = n;
        events = new ArrayList<Event>();
        publicView = true;
    }

    public void setName(String n) {
        name = n;
    }

    public String getName() {
        return name;
    }

    public void addEvent(Event e) {
        events.add(e);
    }

    public void removeEvent(String event) {
        for (Event e : events) {
            if (e.getName().equals(event)) {
                events.remove(e);
                break;
            }
        }
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void togglePublic() {
        publicView = !publicView;
    }

    public Boolean isPublic() {
        return publicView;
    }
}
