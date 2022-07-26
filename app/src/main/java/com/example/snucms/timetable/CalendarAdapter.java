package com.example.snucms.timetable;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.snucms.R;

import java.time.LocalDate;
import java.util.ArrayList;

class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>
{
    private final ArrayList<LocalDate> days;
    private final OnItemListener onItemListener;

    public CalendarAdapter(ArrayList<LocalDate> days, OnItemListener onItemListener)
    {
        this.days = days;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if(days.size() > 15) //month view
            layoutParams.height = (int) (parent.getHeight() * 0.166666666);
        else // week view
            layoutParams.height = (int) parent.getHeight();

        return new CalendarViewHolder(view, onItemListener, days);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position)
    {
        if(days.get(position) == null) {
            holder.dayOfMonth.setText("");
        } else {
            holder.bind();
        }
    }

    @Override
    public int getItemCount()
    {
        return days.size();
    }

    public interface OnItemListener
    {
        void onItemClick(int position, LocalDate date);

    }

    static class CalendarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private ArrayList<LocalDate> days;
        public View parentView;
        public TextView dayOfMonth;
        public TextView[] eventView;
        private CalendarAdapter.OnItemListener onItemListener;

        public CalendarViewHolder(@NonNull View itemView, CalendarAdapter.OnItemListener onItemListener, ArrayList<LocalDate> days)
        {
            super(itemView);
            parentView = itemView.findViewById(R.id.parentView);
            dayOfMonth = itemView.findViewById(R.id.cellDayText);

            eventView = new TextView[4];
            eventView[0] = itemView.findViewById(R.id.eventView1);
            eventView[1] = itemView.findViewById(R.id.eventView2);
            eventView[2] = itemView.findViewById(R.id.eventView3);
            eventView[3] = itemView.findViewById(R.id.eventView4);

            this.onItemListener = onItemListener;
            this.days = days;
            itemView.setOnClickListener(this);
        }

        public void bind() {
            //System.out.println(CalendarUtils.dailyEventMap.keySet().toArray()[0] + "----------");
            /*if(getAdapterPosition() < days.size()
                    && getAdapterPosition() > -1)
            System.out.println(days.get(getAdapterPosition()).getDayOfMonth() + "-----");*/

            if(days.get(getAdapterPosition()) != null) {

                final LocalDate date = days.get(getAdapterPosition());
                dayOfMonth.setText(String.valueOf(date.getDayOfMonth()));
                if(date.equals(CalendarUtils.selectedDate)) {
                    parentView.setBackgroundColor(Color.parseColor("#d4f1f4"));
                    dayOfMonth.setTextColor(Color.BLACK);
                }

                if(CalendarUtils.dailyEventMap
                        .containsKey(days.get(getAdapterPosition()).getDayOfMonth())){
                    ArrayList<String> dailyEvents = CalendarUtils.dailyEventMap
                            .get(days.get(getAdapterPosition()).getDayOfMonth());
                    if (dailyEvents != null) {
                        for (int i = 0; i < dailyEvents.size(); i++) {
                            eventView[i].setText(dailyEvents.get(i));
                            eventView[i].setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
        }

        @Override
        public void onClick(View view)
        {
            onItemListener.onItemClick(getAdapterPosition(), days.get(getAdapterPosition()));
        }
    }
}
