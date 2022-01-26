package com.example;

// import java.sql.Timestamp;
// import java.text.SimpleDateFormat;
// import java.util.ArrayList;
// import java.util.HashMap;
import java.util.*;
/*
    INSTRUCTIONS:
    - When adding event, enter date and time as "yyyy-MM-dd hh:mm:ss"

    >> How to use timestamp
    Timestamp ts = new Timestamp(System.currentTimeMillis());
    String s = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(ts);
    Event e = new Event("eventName", s, s);

    >> Hashmap over Hashtable
    - I don't plan on using threads so I don't need synchronization yet, hashmap is more efficient for this
    - I could use LinkedHashMap if I wanted to implement a map that holds the order of insertions
*/

public class CalendarApp {
    private HashMap<User, ArrayList<Calendar>> userDict;
    private User currentUser;
    private Calendar currentCalendar;
    private String cmd;
    private static final String CMD_PROMPT = String.join("\n",
            "\n----------------------------------",
            "COMMANDS:",
            "Log In = 1 *name*",
            "Add Calendar = 2 *calendar name*",
            "Add Event = 3 *event name*",
            "View All Calendars = 4",
            "View Current Calendar = 5",
            "Remove Calendar = 6",
            "Remove Event = 7",
            "----------------------------------\n",
            ">>>> ");

    public CalendarApp() {
        userDict = new HashMap<User, ArrayList<Calendar>>();
        currentUser = null;
        currentCalendar = null;
    }

    public void run() {
        Boolean loop = true;
        while (loop) {
            String input = getInput();
            if (input.length() != 0) {
                cmd = input.substring(0, 1);
                switch (cmd) {
                    case "1":
                        logIn(input);
                        break;

                    case "2":
                        addCalendar(input);
                        break;

                    case "3":
                        addEvent(input);
                        break;

                    case "4":
                        viewAllCalendars();
                        break;

                    case "5":
                        viewCurrentCalendar();
                        break;

                    case "6":
                        removeCalendar(input);
                        break;

                    case "7":
                        removeEvent(input);
                        break;

                    case "q":
                        System.out.println("\nQUITTING\n");
                        loop = false;
                        break;

                    default:
                        System.out.println("\nNVALID INPUT\n");
                        break;
                }
            }
        }
    }

    private static String getInput() {
        System.out.print(CMD_PROMPT);
        String input = System.console().readLine();
        System.out.println();
        return input;
    }

    private void logIn(String input) {
        if (input.length() < 2) {
            return;
        }
        String userName = input.substring(2);
        Boolean userFound = false;
        for (User u : userDict.keySet()) {
            System.out.println("userName; " + userName);
            System.out.println("name; " + u.getName());
            if (u.getName().equals(userName)) {
                currentUser = u;
                userFound = true;
            }
        }
        if (!userFound) {
            User user = new User(userName);
            ArrayList<Calendar> calendar = new ArrayList<Calendar>();
            userDict.put(user, calendar);
            currentUser = user;
        }
    }

    private void addCalendar(String input) {
        if (input.length() < 2) {
            return;
        }
        if (currentUser == null) {
            System.out.println("Log in first");
            return;
        }
        String calendarName = input.substring(2);
        Calendar calendar = new Calendar(calendarName);
        currentCalendar = calendar;
        userDict.get(currentUser).add(calendar);
    }

    private void addEvent(String input) {
        if (currentCalendar == null) {
            System.out.println("Add calendar first");
            return;
        }
        if (input.length() < 2) {
            return;
        }
        String eventName = input.substring(2);
        System.out.print("ENTER START DATE\n>>>>");
        String startDate = System.console().readLine();
        System.out.print("ENTER END DATE\n>>>>");
        String endDate = System.console().readLine();
        Event event = new Event(eventName, startDate, endDate);
        currentCalendar.addEvent(event);
    }

    private void removeCalendar(String input) {

    }

    private void removeEvent(String input) {
        if (currentCalendar == null) {
            System.out.println("Add calendar first");
            return;
        }
        if (input.length() < 2) {
            return;
        }
        String eventName = input.substring(2);
        for (Calendar c : userDict.get(currentUser)) {
            for (Event e : c.getEvents()) {
                if (e.getName().equals(eventName)) {
                    c.removeEvent(eventName);
                }
            }
        }
    }

    private void viewAllCalendars() {
        for (User s : userDict.keySet()) {
            System.out.println("\nUSER: " + s.getName());
            for (Calendar c : userDict.get(s)) {
                System.out.println("CALENDAR: " + c.getName());
                for (Event e : c.getEvents()) {
                    System.out.println("EVENT: " + e.getName());
                }
            }
        }
    }

    private void viewCurrentCalendar() {
        for (Calendar c : userDict.get(currentUser)) {
            if (c == currentCalendar) {
                System.out.println("CALENDAR: " + currentCalendar.getName());
                for (Event e : c.getEvents()) {
                    System.out.println("EVENT: " + e.getName());
                    System.out.println("START DATE: " + e.getStartDate());
                    System.out.println("EVENT DATE: " + e.getEndDate());

                }
            }
        }
    }
}
