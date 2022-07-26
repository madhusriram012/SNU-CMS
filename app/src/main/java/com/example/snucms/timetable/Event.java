package com.example.snucms.timetable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

/*
App specific files - Json
Last option - Shared preferences
 */

public class Event
{
    private long id;
    private String name;
    private LocalDate date;
    private LocalTime time;
    private boolean repeat = false;
    public static long lastId = 0;

    /*public Event(String name, LocalDate date, LocalTime time, boolean repeat) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.repeat = repeat;
    }*/

    public Event(long id, String name, LocalDate date, LocalTime time, boolean repeat) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
        this.repeat = repeat;
    }

    /*public static ArrayList<Event> eventsForDate(LocalDate date)
    {
        ArrayList<Event> events = new ArrayList<>();

        for(Event event : CalendarUtils.eventsList)
        {
            //System.out.println(event.getDate() + "---" + date + "----" + event.getDate().getDayOfWeek() + "---" + date.getDayOfWeek() + "----");
            if(event.getDate().equals(date) || (event.repeat && event.getDate().getDayOfWeek() == date.getDayOfWeek()))
                events.add(event);
        }

        return events;
    }*/

    public static ArrayList<Event> eventsForDate(LocalDate date) {
        ArrayList<Event> events = new ArrayList<>();

        for(Event event : CalendarUtils.eventsList) {
            if(event.getDate().equals(date) || (event.repeat && event.getDate().getDayOfWeek() == date.getDayOfWeek()))
                events.add(event);
        }

        return events;
    }

    public static ArrayList<Event> eventsForDateAndTime(LocalDate date, LocalTime time)
    {
        ArrayList<Event> events = new ArrayList<>();

        for(Event event : CalendarUtils.eventsList)
        {
            int eventHour = event.time.getHour();
            int cellHour = time.getHour();
            if((event.getDate().equals(date) || (event.repeat && event.getDate().getDayOfWeek() == date.getDayOfWeek())) && eventHour == cellHour)
                events.add(event);
        }

        return events;
    }

    public long getId() {
        return id;
    }

    /*public void setId(long id) {
        this.id = id;
    }*/

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public LocalDate getDate()
    {
        return date;
    }

    public void setDate(LocalDate date)
    {
        this.date = date;
    }

    public LocalTime getTime()
    {
        return time;
    }

    public void setTime(LocalTime time)
    {
        this.time = time;
    }

    public boolean isRepeat() { return repeat; }

}
