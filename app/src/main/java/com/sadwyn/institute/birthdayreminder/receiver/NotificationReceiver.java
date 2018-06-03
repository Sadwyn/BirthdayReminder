package com.sadwyn.institute.birthdayreminder.receiver;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.sadwyn.institute.birthdayreminder.R;
import com.sadwyn.institute.birthdayreminder.util.NotificationHelper;

public class NotificationReceiver extends BroadcastReceiver {

    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String PATRONYMIC = "patronymic";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        Notification notification = extras.getParcelable("notification");
        int id = extras.getInt("id");

       NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
       manager.notify(id, notification);

    }
}
