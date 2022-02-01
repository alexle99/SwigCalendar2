package com.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Calendar;

/*
    INSTRUCTIONS:
    - When adding calendars or events, do not includes spaces in names
    - When adding event, enter date and time as "yyyy MM dd hh mm"

    >> Hashmap over Hashtable
    - I don't plan on using threads so I don't need synchronization yet, hashmap is more efficient for this
    - I could use LinkedHashMap if I wanted to implement a map that holds the order of insertions
*/

public class CalendarApp {

    // private String TODAY = "2022 01 25 11 13";
    // private String TOMORROW = "2022 01 26 12 01";
    // private String ONES = "1111 11 11 11 11";
    // private String ZEROS = "0000 00 00 00 00";
    // private String TWOS = "2222 22 22 22 22";
    // private String THREES = "3333 33 33 33 33";

    private static CalendarApp calendarApp;
    // the main data structure
    private HashMap<User, ArrayList<CalendarClass>> userDict;
    // keeps track of current user using the app
    private User currentUser;
    // keeps track of current Calendar to display
    private CalendarClass currentCalendar;
    private String cmd;

    // CalendarApp is a singleton
    private CalendarApp() {
        userDict = new HashMap<User, ArrayList<CalendarClass>>();
        currentUser = null;
        currentCalendar = null;
    }

    // called from Main.java
    public static CalendarApp getInstance() {
        if (calendarApp == null) {
            calendarApp = new CalendarApp();
        }
        return calendarApp;
    }

    // main loop for user input and handling input
    public void run() {
        Boolean loop = true;
        while (loop) {
            String input = getInput();
            loop = handleInput(input);
        }
    }

    private static String getInput() {
        pl("==================================================");
        p(">>>> ");
        String input = System.console().readLine();
        pl("");
        return input;
    }

    private Boolean handleInput(String input) {
        if (input.length() != 0) {
            cmd = input.substring(0, 1);
            switch (cmd) {
                case "1":
                    if (argExists(input)) {
                        logIn(input.substring(2));
                    }
                    break;

                case "2":
                    if (argExists(input)) {
                        addCalendar(input.substring(2));
                    }
                    break;

                case "3":
                    if (argExists(input)) {
                        addEvent(input.substring(2));
                    }
                    break;

                case "4":
                    viewAllCalendars();
                    break;

                case "5":
                    viewCurrentCalendar();
                    break;

                case "6":
                    if (argExists(input)) {
                        removeCalendar(input.substring(2));
                    }
                    break;

                case "7":
                    if (argExists(input)) {
                        removeEvent(input.substring(2));
                    }
                    break;

                case "8":
                    if (argExists(input)) {
                        renameCalendar(input.substring(2));
                    }
                    break;

                case "9":
                    if (argExists(input)) {
                        renameEvent(input.substring(2));
                    }
                    break;

                case "A":
                    if (argExists(input)) {
                        updateEvent(input.substring(2));
                    }
                    break;

                case "B":
                    if (argExists(input)) {
                        filterCalendars(input.substring(2));
                    }
                    break;

                case "C":
                    if (argExists(input)) {
                        filterEvents(input.substring(2));
                    }
                    break;

                case "D":
                    toggleCurrentCalendarPrivacy();
                    break;

                case "E":
                    pl("Current User: " + currentUser.getName());
                    break;

                case "F":
                    pl("Current Calendar: " + currentCalendar.getName());
                    break;

                case "G":
                    shareCalendar(input.substring(2));
                    break;

                case "Q":
                    pl("\nQUITTING\n");
                    return false;

                default:
                    pl("\nINVALID INPUT\n");
                    break;
            }
        }
        return true;
    }

    private void logIn(String userName) {
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

    private void addCalendar(String calendarName) {
        // a user must be logged into add a calendar
        if (currentUser == null) {
            System.out.println("Log in first");
            return;
        }
        for (CalendarClass c : userDict.get(currentUser)) {
            // doesn't add calendar with existing name
            if (c.getName().equals(calendarName)) {
                currentCalendar = c;
                return;
            }
        }
        CalendarClass calendar = new CalendarClass(calendarName);
        currentCalendar = calendar;
        userDict.get(currentUser).add(calendar);
    }

    private void addEvent(String eventName) {
        // current calendar must exist
        if (currentCalendar == null) {
            System.out.println("Add calendar first");
            return;
        }

        Event event = new Event(eventName);

        p("ENTER START DATE 'yyyy MM dd hh mm'\n>>>> ");
        String startDate = System.console().readLine();
        // String startDate = TODAY;

        p("ENTER END DATE 'yyyy MM dd hh mm'\n>>>> ");
        String endDate = System.console().readLine();
        // String endDate = TOMORROW;
        pl("");
        String[] startDateArray = startDate.split(" ");
        String[] endDateArray = endDate.split(" ");

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

    private void removeCalendar(String calendarName) {
        for (CalendarClass c : userDict.get(currentUser)) {
            if (c.getName().equals(calendarName)) {
                userDict.get(currentUser).remove(c);
                return;
            }
        }
    }

    private void removeEvent(String eventName) {
        if (currentCalendar == null) {
            System.out.println("Add calendar first");
            return;
        }
        currentCalendar.removeEvent(eventName);
    }

    private void renameCalendar(String input) {
        currentCalendar.setName(input);
    }

    private void renameEvent(String input) {
        String[] inputArray = input.split(" ");
        String oldName = inputArray[0];
        String newName = inputArray[1];
        for (Event e : currentCalendar.getEvents()) {
            if (oldName.equals(e.getName())) {
                e.setName(newName);
            }
        }
    }

    // brute force, remove event with name and make new event with same name
    // user has to input all the fields again
    private void updateEvent(String input) {
        currentCalendar.removeEvent(input);
        addEvent(input);
    }

    // filters calendars of the current user that have substring in name
    private void filterCalendars(String substring) {
        for (CalendarClass c : userDict.get(currentUser)) {
            if (c.getName().contains(substring)) {
                viewCalendar(c);
            }
        }
    }

    // filters events that contain substring in the current calendar
    private void filterEvents(String substring) {
        for (Event e : currentCalendar.getEvents()) {
            if (e.getName().contains(substring)) {
                viewEvent(e);
            }
        }
    }

    // calendar privacy toggling
    // only the owner of the calendar can see it when it is private
    private void toggleCurrentCalendarPrivacy() {
        currentCalendar.togglePublic();
        p(currentCalendar.getName() + " IS NOW ");
        if (currentCalendar.isPublic()) {
            pl("PUBLIC");
        } else {
            pl("PRIVATE");
        }
    }

    private User getUser(String name) {
        for (User u : userDict.keySet()) {
            if (u.getName().equals(name)) {
                return u;
            }
        }
        return null;
    }

    // add calendar with same public toggle to specified user
    private void shareCalendar(String user) {
        User u = getUser(user);
        userDict.get(u).add(currentCalendar);
    }

    // show all the users, their calendars and each calendar's events
    // private calendars aren't shown unless calendar's user is logged in
    private void viewAllCalendars() {
        for (User s : userDict.keySet()) {
            pl("--------------------");
            pl("USER: " + s.getName());
            for (CalendarClass c : userDict.get(s)) {
                if (c.isPublic()) {
                    viewCalendar(c);
                } else {
                    // if the calendar belongs to the user
                    if (userDict.get(currentUser).contains(c)) {
                        viewCalendar(c);
                    }
                }
            }
        }
    }

    private void viewCurrentCalendar() {
        viewCalendar(currentCalendar);
    }

    private void viewCalendar(CalendarClass c) {
        pl("\nCALENDAR: " + c.getName());
        for (Event e : c.getEvents()) {
            viewEvent(e);
        }
    }

    private void viewEvent(Event e) {
        pl("\nEVENT       : " + e.getName());
        pl("-Start YEAR  : " + e.getStartDate().get(Calendar.YEAR));
        pl("-Start MONTH : " + e.getStartDate().get(Calendar.MONTH));
        pl("-Start DATE  : " + e.getStartDate().get(Calendar.DATE));
        pl("-Start HOUR  : " + e.getStartDate().get(Calendar.HOUR));
        pl("-Start MINUTE: " + e.getStartDate().get(Calendar.MINUTE));

        pl("-END YEAR    : " + e.getEndDate().get(Calendar.YEAR));
        pl("-END MONTH   : " + e.getEndDate().get(Calendar.MONTH));
        pl("-END DATE    : " + e.getEndDate().get(Calendar.DATE));
        pl("-END HOUR    : " + e.getEndDate().get(Calendar.HOUR));
        pl("-END MINUTE  : " + e.getEndDate().get(Calendar.MINUTE));
    }

    // // used for testing
    // private void viewAllUsers() {
    // pl("Users: ");
    // for (User u : userDict.keySet()) {
    // pl(u.getName());
    // }
    // }

    private static boolean argExists(String input) {
        if (input.length() < 2) {
            pl("empty arg");
            return false;
        }
        return true;
    }

    private static void p(String s) {
        System.out.print(s);
    }

    private static void pl(String s) {
        System.out.println(s);
    }
}
