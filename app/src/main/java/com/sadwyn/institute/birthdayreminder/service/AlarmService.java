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

public class AlarmService extends IntentService {
    public AlarmService() {
        super("AlarmService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Cursor c = getContentResolver().query(MyContentProvider.PEOPLE_CONTENT_URI, null,null,null, null);
        List<Person> people = getPeople(c);




        for (Person person : people) {
            Date birthDate = parseDate(person.getBirthDate());
            Calendar birthDateCalendar = Calendar.getInstance();
            birthDateCalendar.setTime(birthDate);


            Calendar currentDate = Calendar.getInstance();
            currentDate.set(Calendar.DAY_OF_YEAR, birthDateCalendar.get(Calendar.DAY_OF_YEAR));
            currentDate.set(Calendar.MONTH, birthDateCalendar.get(Calendar.MONTH));
            currentDate.add(Calendar.DAY_OF_YEAR, -7);

            Calendar testCalendaar = Calendar.getInstance();
            testCalendaar.add(Calendar.SECOND, 10);

            ComponentName receiver = new ComponentName(getApplicationContext(), AlarmReceiver.class);
            PackageManager pm = getApplicationContext().getPackageManager();
            pm.setComponentEnabledSetting(receiver,
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                    PackageManager.DONT_KILL_APP);


            Intent receiverIntent = new Intent(this, NotificationReceiver.class);
            NotificationHelper notificationHelper = new NotificationHelper(getApplicationContext());
            Notification notification = notificationHelper.createNotification("7 дней до дня рождения", person.getLastName() + " " + person.getFirstName() +
                    " " + person.getPatronymic());
            receiverIntent.putExtra("notification", notification);
            receiverIntent.putExtra("id", person.getId());

            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, person.getId(), receiverIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            AlarmManager manager = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
            manager.set(AlarmManager.RTC_WAKEUP, currentDate.getTimeInMillis(), pendingIntent);
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
            do {
                Person person = new Person();
                person.setId(c.getInt(idColIndex));
                person.setFirstName(c.getString(firstNameColIndex));
                person.setLastName(c.getString(lastNameColIndex));
                person.setPatronymic(c.getString(patronymicIndex));
                person.setBirthDate(c.getString(birthDateIndex));
                people.add(person);
            }
            while (c.moveToNext());
            c.close();
            return people;
        }
        return Collections.emptyList();
    }

    private Date parseDate(String date){
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
