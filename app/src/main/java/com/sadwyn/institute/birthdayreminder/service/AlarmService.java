package com.sadwyn.institute.birthdayreminder.service;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;

import com.sadwyn.institute.birthdayreminder.contentprovider.MyContentProvider;
import com.sadwyn.institute.birthdayreminder.data.Person;
import com.sadwyn.institute.birthdayreminder.receiver.AlarmReceiver;
import com.sadwyn.institute.birthdayreminder.receiver.NotificationReceiver;
import com.sadwyn.institute.birthdayreminder.util.NotificationHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class AlarmService extends JobIntentService {


    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        Cursor c = getContentResolver().query(MyContentProvider.PEOPLE_CONTENT_URI, null, null, null, null);
        List<Person> people = getPeople(c);


        for (Person person : people) {
            Date birthDate = parseDate(person.getBirthDate());

            Calendar currentDate = Calendar.getInstance();
            Calendar birthDateCalendar = Calendar.getInstance();
            birthDateCalendar.setTime(birthDate);
            birthDateCalendar.set(Calendar.YEAR, currentDate.get(Calendar.YEAR));
            birthDateCalendar.add(Calendar.DAY_OF_YEAR, -7);

            Intent receiverIntent = new Intent(this, NotificationReceiver.class);

            receiverIntent.putExtra("fullName", person.getLastName() + " " + person.getFirstName() + " " + person.getPatronymic());
            receiverIntent.putExtra("birthDate", person.getBirthDate());
            receiverIntent.putExtra("id", person.getId());

            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, person.getId(), receiverIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            long millisOfYear = (long) 1000 * 60 * 60 * 24 * 365;

            AlarmManager manager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);

            assert manager != null;
            if (currentDate.after(birthDateCalendar)) {
                birthDateCalendar.add(Calendar.YEAR, 1);
            }
            manager.setRepeating(AlarmManager.RTC_WAKEUP, birthDateCalendar.getTimeInMillis(), millisOfYear, pendingIntent);
        }
    }

    private List<Person> getPeople(Cursor c) {
        if (c != null && c.moveToFirst()) {
            List<Person> people = new ArrayList<>();
            int idColIndex = c.getColumnIndex("id");
            int firstNameColIndex = c.getColumnIndex("firstName");
            int lastNameColIndex = c.getColumnIndex("lastName");
            int patronymicIndex = c.getColumnIndex("patronymic");
            int birthDateIndex = c.getColumnIndex("birthDate");
            int phoneIndex = c.getColumnIndex("phone");
            do {
                Person person = new Person();
                person.setId(c.getInt(idColIndex));
                person.setFirstName(c.getString(firstNameColIndex));
                person.setLastName(c.getString(lastNameColIndex));
                person.setPatronymic(c.getString(patronymicIndex));
                person.setBirthDate(c.getString(birthDateIndex));
                person.setPhone(c.getString(phoneIndex));
                people.add(person);
            }
            while (c.moveToNext());
            c.close();
            return people;
        }
        return Collections.emptyList();
    }

    private Date parseDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

        Date birthDate = null;
        try {
            birthDate = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return birthDate;
    }
}
