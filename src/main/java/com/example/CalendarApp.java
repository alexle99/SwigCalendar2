package com.example;

// import java.sql.Timestamp;
// import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
// import java.util.*;
import java.util.Calendar;

/*
    INSTRUCTIONS:
    - When adding calendars or events, do not includes spaces
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
    private String ONES = "1111 11 11 11 11";
    private String ZEROS = "0000 00 00 00 00";
    private String TWOS = "2222 22 22 22 22";
    private String THREES = "3333 33 33 33 33";

    private HashMap<User, ArrayList<CalendarClass>> userDict;
    private User currentUser;
    private CalendarClass currentCalendar;
    private String cmd;
    private static final String CMD_PROMPT = String.join("\n",
            "\n==================================================",
            "COMMANDS:",
            "Log In = 1 *name*",
            "Add Calendar = 2 *calendar name*",
            "Add Event = 3 *event name*",
            "View All Calendars = 4",
            "View Current Calendar = 5",
            "Remove Calendar = 6 *calendar name*",
            "Remove Event = 7 *event name*",
            "Rename Calendar = 8 *calendar name*",
            "Rename Event = 9 *old event name* *new event name*",
            "Update Event = A *event name*",
            "Filter Calendars = B *key word*",
            "Filter Events = C *key word*",
            "Toggle Calendar Privacy = D",
            "Curent User = E",
            "All Users = F",
            "Current Calendar = G",
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
                    viewAllUsers();
                    break;

                case "G":
                    pl("Current Calendar: " + currentCalendar.getName());
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
        if (currentUser == null) {
            System.out.println("Log in first");
            return;
        }
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

    private void addEvent(String eventName) {
        if (currentCalendar == null) {
            System.out.println("Add calendar first");
            return;
        }

        Event event = new Event(eventName);

        p("ENTER START DATE 'yyyy MM dd hh mm'\n>>>> ");
        String startDate = System.console().readLine();
        // String startDate = TODAY;

        p("ENTER END DATE 'yyyy MM dd hh mm'\n>>>> ");
        // String endDate = System.console().readLine();
        String endDate = TOMORROW;
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
        pl("INPUT: " + input);
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

    // show all the users, their calendars and each calendar's events
    // doesn't take into account private calendars yet
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

    private void viewAllUsers() {
        pl("Users: ");
        for (User u : userDict.keySet()) {
            pl(u.getName());
        }
    }

    private static boolean argExists(String input) {

        String[] inputArray = input.split(" ");

        if (input.length() < 2) {
            pl("empty arg");
            return false;
        }

        String result = "";
        for (String s : inputArray) {
            result += s + " ";
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
