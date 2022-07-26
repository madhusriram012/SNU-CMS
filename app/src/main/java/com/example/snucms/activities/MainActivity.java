package com.example.snucms.activities;

import com.example.snucms.R;
import com.example.snucms.callbob.CallBob;
import com.example.snucms.gymslot.GymSlot;
import com.example.snucms.jsonHelper;
import com.example.snucms.timetable.CalendarMainActivity;
import com.example.snucms.timetable.CalendarUtils;
import com.example.snucms.tuckshop.Tuckshop;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

/*
Timetable with Google Calendar Integration and notifications for classes, assignments, ISC slots and other events
Call bob: Issue tracking, history and more accountability
ISC slot booking and digital sign in
Library Digital Sign in using QR Code
Tuckshop Food Reservation
*/

/*
Required components:
Firebase database
    Callbob
    ISC slots
    Library entries
    Tuckshop entries
Notifications
QR code scanner
 */

/*
Screens:
Login
Main
Timetable
    Today's events
    Add/remove events
Callbob
    Add issue
    Issue history (remove issues)
    Issue tracking
ISC slots
    List of slots
        Each item has name, time, total capacity
    //shows confirmation on click
    //only displays your slot on booking
Library
    QR scanner with token entry
Tuckshop
    Show menu
    Pending orders
    Completed orders
 */

/*
Class for event
    Name
    Time
    Repeat
    Reminder time
    function to cancel notification
 */

/*
Class for issue
    Id
    Title
    Desciption
    Location
    Tracking
        Acknowledged
        * estimated verification time
        * Verified - User
        estimated fix time
        Issue resolved - User and call
 */

/*
ISC slots:
Collections for each slot
Reset collections every week
Able to add only one slot
 */

/*
Library:
Simple database design for library
Only appends user data, with the token number
 */

/*
Tuckshop:
Two tables, one for pending and finished orders
Add new orders to pending
Finished orders are moved
Both are displayed seperately per user
 */

public class MainActivity extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    RelativeLayout btnTimetable, btnGymSLot, btnTuckshop, btnCallBob, btnLibrary, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getDrawable(R.drawable.img_1));

        btnTimetable = findViewById(R.id.btnTimetable);
        btnGymSLot = findViewById(R.id.btnGymSlot);
        btnTuckshop = findViewById(R.id.btnTuckshop);
        btnCallBob = findViewById(R.id.btnCallBob);
        btnLibrary = findViewById(R.id.btnLibrary);
        btnLogout = findViewById(R.id.btnLogout);

        btnTimetable.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MainActivity.this.startActivity(new Intent(MainActivity.this, CalendarMainActivity.class));
                        overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);


                    }
                }
        );

        btnGymSLot.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MainActivity.this.startActivity(new Intent(MainActivity.this, GymSlot.class));
                        overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);

                        //firebaseHelper.populateSlots();
                    }
                }
        );

        btnTuckshop.setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MainActivity.this.startActivity(new Intent(MainActivity.this, Tuckshop.class));
                        overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);


                    }
                }


        );

        btnCallBob.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MainActivity.this.startActivity(new Intent(MainActivity.this, CallBob.class));
                        overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);


                    }
                }
        );

        btnLibrary.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MainActivity.this.startActivity(new Intent(MainActivity.this, Library.class));
                        overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);


                    }
                }
        );

        btnLogout.setOnClickListener(view -> {
            SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.pref_file_key), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("netid", "");
            editor.putString("name", "");
            editor.putString("rollno", "");
            editor.clear();
            editor.apply();
            CalendarUtils.eventsList.clear();
            CalendarUtils.dailyEventMap.clear();
            (new jsonHelper(getApplicationContext())).clearJson();
            startActivity(new Intent(MainActivity.this, LoginPage.class));
        });

    }

}