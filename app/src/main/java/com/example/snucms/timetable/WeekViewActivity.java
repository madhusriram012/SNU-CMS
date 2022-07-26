package com.example.snucms.timetable;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.snucms.R;

import java.time.LocalDate;
import java.util.ArrayList;

public class WeekViewActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener
{
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private ListView eventListView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        ActionBar actionBar;
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getDrawable(R.drawable.img_1));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_view);
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
        eventListView = findViewById(R.id.eventListView);
        setWeekView();

    }

    private void setWeekView()
    {
        monthYearText.setText(CalendarUtils.monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> daysInWeek = CalendarUtils.daysInWeekArray(CalendarUtils.selectedDate);

        //Get events for each day and add the names to dailyEventMap
        CalendarUtils.dailyEventMap.clear();
        for(int i=0;i<daysInWeek.size();i++) {
            if(daysInWeek.get(i) == null)
                continue;
            ArrayList<Event> eventsInDay = Event.eventsForDate(daysInWeek.get(i));
            if(eventsInDay.size() > 0) {
                ArrayList<String> eventStrings = new ArrayList<>();
                for(int j=0;j<4 && j<eventsInDay.size();j++)
                    eventStrings.add(eventsInDay.get(j).getName());
                CalendarUtils.dailyEventMap.put(daysInWeek.get(i).getDayOfMonth(), eventStrings);
            }
        }

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInWeek, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
        setEventAdpater();
    }


    public void previousWeekAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusWeeks(1);
        setWeekView();
    }

    public void nextWeekAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusWeeks(1);
        setWeekView();
    }

    @Override
    public void onItemClick(int position, LocalDate date)
    {
        if(CalendarUtils.selectedDate.equals(date))
            startActivity(new Intent(this, DailyCalendarActivity.class));
        
        else {
            CalendarUtils.selectedDate = date;
            setWeekView();
        }
        CalendarUtils.selectedDate = date;
        setWeekView();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        setWeekView();
    }

    private void setEventAdpater()
    {
        EventAdapter eventAdapter = new EventAdapter(getApplicationContext(), CalendarUtils.eventsList);
        eventListView.setAdapter(eventAdapter);
    }


    public void newEventAction(View view)
    {
        startActivity(new Intent(this, EventEditActivity.class));
    }

}