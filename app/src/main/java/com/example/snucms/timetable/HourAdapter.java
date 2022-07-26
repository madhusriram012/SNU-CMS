package com.example.snucms.timetable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.snucms.R;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class HourAdapter extends ArrayAdapter<HourEvent>
{
    public HourAdapter(@NonNull Context context, List<HourEvent> hourEvents)
    {
        super(context, 0, hourEvents);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View itemView, @NonNull ViewGroup parent)
    {
        HourEvent event = getItem(position);

        if (itemView == null)
            itemView = LayoutInflater.from(getContext()).inflate(R.layout.hour_cell, parent, false);

        TextView timeTV = itemView.findViewById(R.id.timeTV);
        timeTV.setText(CalendarUtils.formattedShortTime(event.time));

        setEvents(itemView, event.events);

        return itemView;
    }

    private void setEvents(View convertView, ArrayList<Event> events)
    {
        TextView event1 = convertView.findViewById(R.id.event1);
        TextView event2 = convertView.findViewById(R.id.event2);
        TextView event3 = convertView.findViewById(R.id.event3);

        /*int i;
        for(i=0;i<3;i++) {
            if(i < events.size()) {
                if(events.size() > 3 && i == 2) {
                    event3.setVisibility(View.VISIBLE);
                    String eventsNotShown = String.valueOf(events.size() - 2);
                    eventsNotShown += " More Events";
                    event3.setText(eventsNotShown);
                } else {
                    setEvent(event1, events.get(0));
                }
            } else {

            }
        }*/
        if(events.size() == 0)
        {
            hideEvent(event1);
            hideEvent(event2);
            hideEvent(event3);
        }
        else if(events.size() == 1)
        {
            setEvent(event1, events.get(0));
            hideEvent(event2);
            hideEvent(event3);
        }
        else if(events.size() == 2)
        {
            setEvent(event1, events.get(0));
            setEvent(event2, events.get(1));
            hideEvent(event3);
        }
        else if(events.size() == 3)
        {
            setEvent(event1, events.get(0));
            setEvent(event2, events.get(1));
            setEvent(event3, events.get(2));
        }
        else
        {
            setEvent(event1, events.get(0));
            setEvent(event2, events.get(1));
            event3.setVisibility(View.VISIBLE);
            String eventsNotShown = String.valueOf(events.size() - 2);
            eventsNotShown += " More Events";
            event3.setText(eventsNotShown);

        }
    }

    private void setEvent(TextView textView, Event event)
    {
        textView.setText(event.getName());
        textView.setVisibility(View.VISIBLE);
    }

    private void hideEvent(TextView tv)
    {
        tv.setVisibility(View.INVISIBLE);
    }

}













