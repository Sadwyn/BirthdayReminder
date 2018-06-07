package com.sadwyn.institute.birthdayreminder.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.JobIntentService;

import com.sadwyn.institute.birthdayreminder.service.AlarmService;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        JobIntentService.enqueueWork(context, AlarmService.class, 666, new Intent());
    }
}
