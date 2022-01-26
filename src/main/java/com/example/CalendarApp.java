package com.example;

// import java.sql.Timestamp;
// import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
// import java.util.*;
import java.util.Calendar;

/*
    INSTRUCTIONS:
    - When adding event, enter date and time as "yyyy MM dd hh mm"

    >> How to use timestamp
    Timestamp ts = new Timestamp(System.currentTimeMillis());
    String s = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(ts);
    Event e = new Event("eventName", s, s);

    >> Hashmap over Hashtable
    - I don't plan on using threads so I don't need synchronization yet, hashmap is more efficient for this
    - I could use LinkedHashMap if I wanted to implement a map that holds the order of insertions
*/

public class CalendarApp {

    private String TODAY = "2022 01 25 11 13";
    private String TOMORROW = "2022 01 26 12 01";

    private HashMap<User, ArrayList<CalendarClass>> userDict;
    private User currentUser;
    private CalendarClass currentCalendar;
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
            "QUIT = q",
            "");

    public CalendarApp() {
        userDict = new HashMap<User, ArrayList<CalendarClass>>();
        currentUser = null;
        currentCalendar = null;
    }

    public void run() {
        Boolean loop = true;
        p(CMD_PROMPT);
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
                        pl("\nQUITTING\n");
                        loop = false;
                        break;

                    default:
                        pl("\nNVALID INPUT\n");
                        break;
                }
            }
        }
    }

    private static String getInput() {
        pl("----------------------------------");
        p(">>>> ");
        String input = System.console().readLine();
        pl("");
        return input;
    }

    private void logIn(String input) {
        if (input.length() < 2) {
            return;
        }
        String userName = input.substring(2);
        for (User u : userDict.keySet()) {
            if (u.getName().equals(userName)) {
                currentUser = u;
                return;
            }
        }
        User user = new User(userName);
        ArrayList<CalendarClass> calendar = new ArrayList<CalendarClass>();
        userDict.put(user, calendar);
        currentUser = user;
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
        for (CalendarClass c : userDict.get(currentUser)) {
            if (c.getName().equals(calendarName)) {
                currentCalendar = c;
                return;
            }
        }
        CalendarClass calendar = new CalendarClass(calendarName);
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
        p("ENTER START DATE 'yyyy MM dd hh mm'\n>>>> ");
        // String startDate = System.console().readLine();
        String startDate = TODAY;
        p("ENTER END DATE 'yyyy MM dd hh mm'\n>>>> ");
        // String endDate = System.console().readLine();
        pl("");
        String endDate = TOMORROW;
        String[] startDateArray = startDate.split(" ");
        String[] endDateArray = endDate.split(" ");

        Event event = new Event(eventName);

        event.setStartDate(
                startDateArray[0],
                startDateArray[1],
                startDateArray[2],
                startDateArray[3],
                startDateArray[4]);

        event.setEndDate(
                endDateArray[0],
                endDateArray[1],
                endDateArray[2],
                endDateArray[3],
                endDateArray[4]);

        currentCalendar.addEvent(event);
    }

    private void removeCalendar(String input) {
        if (input.length() < 2) {
            return;
        }
        String calendarName = input.substring(2);
        for (CalendarClass c : userDict.get(currentUser)) {
            if (c.getName().equals(calendarName)) {
                userDict.get(currentUser).remove(c);
                return;
            }
        }
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
        currentCalendar.removeEvent(eventName);
    }

    private void viewAllCalendars() {
        for (User s : userDict.keySet()) {
            pl("--------------------");
            pl("USER: " + s.getName());
            for (CalendarClass c : userDict.get(s)) {
                pl("\nCALENDAR: " + c.getName());
                for (Event e : c.getEvents()) {
                    viewEvent(e);
                }
            }
        }
    }

    private void viewCurrentCalendar() {
        for (CalendarClass c : userDict.get(currentUser)) {
            if (c == currentCalendar) {
                pl("CALENDAR: " + currentCalendar.getName());
                for (Event e : c.getEvents()) {
                    viewEvent(e);
                }
            }
        }
    }

    private void viewEvent(Event e) {
        pl("\nEVENT       : " + e.getName());
        pl("Start YEAR  : " + e.getStartDate().get(Calendar.YEAR));
        pl("Start MONTH : " + e.getStartDate().get(Calendar.MONTH));
        pl("Start DATE  : " + e.getStartDate().get(Calendar.DATE));
        pl("Start HOUR  : " + e.getStartDate().get(Calendar.HOUR));
        pl("Start MINUTE: " + e.getStartDate().get(Calendar.MINUTE));

        pl("END YEAR    : " + e.getEndDate().get(Calendar.YEAR));
        pl("END MONTH   : " + e.getEndDate().get(Calendar.MONTH));
        pl("END DATE    : " + e.getEndDate().get(Calendar.DATE));
        pl("END HOUR    : " + e.getEndDate().get(Calendar.HOUR));
        pl("END MINUTE  : " + e.getEndDate().get(Calendar.MINUTE));
    }

    private static void p(String s) {
        System.out.print(s);
    }

    private static void pl(String s) {
        System.out.println(s);
    }
}
