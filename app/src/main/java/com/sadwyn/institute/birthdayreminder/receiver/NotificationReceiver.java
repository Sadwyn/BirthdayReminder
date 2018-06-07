package com.sadwyn.institute.birthdayreminder.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.sadwyn.institute.birthdayreminder.util.NotificationHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();

        int id = extras.getInt("id");
        String fullName = extras.getString("fullName");
        String birthDate = extras.getString("birthDate");

        NotificationHelper notificationHelper = new NotificationHelper(context.getApplicationContext());
        Notification notification = notificationHelper.createNotification("7 дней до дня рождения", fullName + ", " + "Исполняется " +
                getDateDiff(formatDate(new Date()), birthDate) + " лет");

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.notify(id, notification);
    }

    private String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        return dateFormat.format(date);
    }

    private int getDateDiff(String date, String date2) {
        String[] date1Array = date.split("\\.");
        String[] date2Array = date2.split("\\.");
        return Math.abs(Integer.parseInt(date1Array[2]) - Integer.parseInt(date2Array[2]));
    }
}
