package com.example.snucms.timetable;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.snucms.R;

import java.time.LocalDate;
import java.util.ArrayList;


public class CalendarMainActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener
{
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getDrawable(R.drawable.img_1));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_main);

        calendarRecyclerView =  findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);

        CalendarUtils.selectedDate = LocalDate.now();
        setMonthView();

    }

    @Override
    protected void onResume() {
        super.onResume();
        setMonthView();
    }

    private void setMonthView()
    {
        monthYearText.setText(CalendarUtils.monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> daysInMonth = CalendarUtils.daysInMonthArray(CalendarUtils.selectedDate);

        //Get events for each day and add the names to dailyEventMap
        CalendarUtils.dailyEventMap.clear();
        for(int i=0;i<daysInMonth.size();i++) {
            if(daysInMonth.get(i) == null)
                continue;
            ArrayList<Event> eventsInDay = Event.eventsForDate(daysInMonth.get(i));
            if(eventsInDay.size() > 0) {
                ArrayList<String> eventStrings = new ArrayList<>();
                for(int j=0;j<4 && j<eventsInDay.size();j++) {
                    eventStrings.add(eventsInDay.get(j).getName());
                }
                CalendarUtils.dailyEventMap.put(daysInMonth.get(i).getDayOfMonth(), eventStrings);
            }
        }

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }


    public void previousMonthAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusMonths(1);
        setMonthView();
    }

    public void nextMonthAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusMonths(1);
        setMonthView();
    }

    @Override
    public void onItemClick(int position, LocalDate date)
    {

        if(date != null)
        {
            //System.out.println("---------");
            if(CalendarUtils.selectedDate.equals(date))
                startActivity(new Intent(CalendarMainActivity.this, WeekViewActivity.class));
            else {
                CalendarUtils.selectedDate = date;
                setMonthView();
            }
        }
    }

    public void addEvent(View view) {
        startActivity(new Intent(this, EventEditActivity.class));
    }

}







