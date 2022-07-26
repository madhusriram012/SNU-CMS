package com.example.snucms.timetable;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.example.snucms.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class EventAdapter extends ArrayAdapter<com.example.snucms.timetable.Event> {

    Context context;
    boolean blue = true;

    public EventAdapter(@NonNull Context context, ArrayList<Event> events)
    {
        super(context, 0, events);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View itemView, @NonNull ViewGroup parent)
    {
        Event event = getItem(position);

        if(event.getDate().equals(CalendarUtils.selectedDate)
                || (event.isRepeat()
                && event.getDate().getDayOfWeek() == CalendarUtils.selectedDate.getDayOfWeek()))
        {
            if (itemView == null)
                itemView = LayoutInflater.from(getContext()).inflate(R.layout.event_cell, parent, false);

            TextView eventCellTV = itemView.findViewById(R.id.eventCellTV);
            TextView eventCellTime = itemView.findViewById(R.id.eventCellTime);
            eventCellTV.setText(event.getName().toUpperCase(Locale.ROOT));
            eventCellTime.setText(CalendarUtils.formattedTime(event.getTime()));

            /*ConstraintLayout eventLayoutInner = itemView.findViewById(R.id.eventLayoutInner);
            if(!blue)
                eventLayoutInner.setBackground(ContextCompat.getDrawable(context, R.drawable.mybutton));
            blue = !blue;*/

            itemView.findViewById(R.id.eventCellLL).setOnClickListener(view -> {
                context.startActivity(
                        new Intent(context, EventEditActivity.class)
                                .putExtra("pos", position)
                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                );
            });
            return itemView;
        } else
            return new Space(context);
    }

}
