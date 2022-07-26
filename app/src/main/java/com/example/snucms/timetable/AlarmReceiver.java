package com.example.snucms.timetable;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.snucms.R;
import com.example.snucms.activities.LoginPage;
import com.example.snucms.activities.MainActivity;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        long id = intent.getExtras().getLong("id");
        String name = intent.getExtras().getString("name");
        String date = intent.getExtras().getString("date");
        String time = intent.getExtras().getString("time");

        Intent notificationIntent = new Intent(context, LoginPage.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntent(notificationIntent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent((int) id, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "channel")
                .setContentTitle(name)
                .setContentText(date + " " + time)
                .setSmallIcon(R.drawable.logo)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify((int) id, builder.build());
    }
}
