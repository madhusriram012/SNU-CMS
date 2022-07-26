package com.example.snucms.timetable;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.snucms.R;
import com.example.snucms.jsonHelper;
import com.google.android.material.switchmaterial.SwitchMaterial;

import org.json.JSONException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EventEditActivity extends AppCompatActivity
{
    private EditText eventNameET;
    private TextView eventDateTV, eventTimeTV;
    private SwitchMaterial repeatSwitch, notificationSwitch;
    private Button btnRemoveEvent;

    int mYear, mMonth, mDay, mHour, mMinute, pos;
    boolean repeat = false, notification = true;
    Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        //actionBar.setTitle((event == null)?"Add event":"Edit event");
        actionBar.setBackgroundDrawable(getDrawable(R.drawable.img_1));

        eventNameET = findViewById(R.id.eventName);
        eventDateTV = findViewById(R.id.eventDateTV);
        eventTimeTV = findViewById(R.id.eventTimeTV);
        btnRemoveEvent = findViewById(R.id.btnRemoveEvent);
        repeatSwitch = findViewById(R.id.repeatSwitch);
        notificationSwitch = findViewById(R.id.notificationSwitch);

        repeatSwitch.setOnCheckedChangeListener((compoundButton, b) -> repeat = b);
        notificationSwitch.setChecked(true);
        notificationSwitch.setOnCheckedChangeListener((compoundButton, b) -> {
            notification = b;
            repeatSwitch.setEnabled(b);
        });

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            pos = extras.getInt("pos");
            event = CalendarUtils.eventsList.get(pos);

            eventNameET.setText(event.getName());

            mYear = event.getDate().getYear();
            mMonth = event.getDate().getMonthValue();
            mDay = event.getDate().getDayOfMonth();
            mHour = event.getTime().getHour();
            mMinute = event.getTime().getMinute();

            repeatSwitch.setChecked(event.isRepeat());
        } else {
            mYear = CalendarUtils.selectedDate.getYear();
            mMonth = CalendarUtils.selectedDate.getMonthValue();
            mDay = CalendarUtils.selectedDate.getDayOfMonth();
            mHour = LocalTime.now().getHour();
            mMinute = LocalTime.now().getMinute();

            btnRemoveEvent.setVisibility(View.GONE);
        }

        eventDateTV.setText("Date: " + CalendarUtils.formattedDate(CalendarUtils.selectedDate));
        eventTimeTV.setText("Time: " + CalendarUtils.formattedTime(LocalTime.of(mHour, mMinute)));
    }

    public void saveEventAction(View view) throws IOException, JSONException {
        String eventName = eventNameET.getText().toString();
        Event newEvent = new Event(
                (event == null)?Event.lastId++:event.getId(),
                eventName,
                LocalDate.of(mYear, mMonth, mDay),
                LocalTime.of(mHour, mMinute),
                repeat
        );
        if(event == null) {
            CalendarUtils.eventsList.add(newEvent);
        } else {
            CalendarUtils.eventsList.set(pos, newEvent);
        }

        new jsonHelper(getApplicationContext()).writeJson();

        if(notification) {
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
            intent.putExtra("id", newEvent.getId());
            intent.putExtra("name", newEvent.getName());
            intent.putExtra("date", DateTimeFormatter.ofPattern("dd/LL/yyyy").format(newEvent.getDate()));
            intent.putExtra("time", newEvent.getTime().format(DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH)));
            PendingIntent alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), (int) newEvent.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);

            Calendar cal = Calendar.getInstance();
            cal.set(mYear, mMonth - 1, mDay, mHour, mMinute, 5);
            Calendar calendar = Calendar.getInstance();
            if (repeat) {
                if (cal.before(calendar)) {
                    int day = cal.get(Calendar.DAY_OF_WEEK);
                    cal = (Calendar) calendar.clone();
                    if (day - cal.get(Calendar.DAY_OF_WEEK) < 0) {
                        cal.add(Calendar.DATE, 7);
                    }
                }
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), (7 * 24 * 60 * 60 * 1000), alarmIntent);
            } else {
                if(!cal.before(calendar))
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), alarmIntent);
            }
        }

        finish();
    }

    public void removeEventAction(View view) throws IOException, JSONException {
        CalendarUtils.eventsList.remove(event);
        new jsonHelper(getApplicationContext()).writeJson();

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), (int) event.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(alarmIntent);

        finish();
    }

    public void showTimePickerDialog(View v) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        mHour = hourOfDay;
                        mMinute = minute;
                        eventTimeTV.setText("Time: " + CalendarUtils.formattedTime(LocalTime.of(mHour, mMinute)));
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
        timePickerDialog.getButton(TimePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
        timePickerDialog.getButton(TimePickerDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
        timePickerDialog.getButton(TimePickerDialog.BUTTON_NEUTRAL).setTextColor(Color.BLACK);
    }

    public void showDatePickerDialog(View v) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        mYear = year;
                        mMonth = monthOfYear;
                        mDay = dayOfMonth;
                        eventDateTV.setText("Date: " + CalendarUtils.formattedDate(LocalDate.of(mYear, mMonth, mDay)));
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
        datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
        datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
        datePickerDialog.getButton(DatePickerDialog.BUTTON_NEUTRAL).setTextColor(Color.BLACK);
    }

}