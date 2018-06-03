package com.sadwyn.institute.birthdayreminder.ui;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.sadwyn.institute.birthdayreminder.contentprovider.MyContentProvider;
import com.sadwyn.institute.birthdayreminder.data.PersonEnum;
import com.sadwyn.institute.birthdayreminder.R;
import com.sadwyn.institute.birthdayreminder.data.DBHelper;
import com.sadwyn.institute.birthdayreminder.data.Person;
import com.sadwyn.institute.birthdayreminder.service.AlarmService;
import com.sadwyn.institute.birthdayreminder.util.NotificationHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView peopleListRecycler;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DBHelper(getApplicationContext());
        if (!dbHelper.doesDatabaseExist(getApplicationContext(), DBHelper.PEOPLE_DB)) {
            fillDefaultDataBase();
        }

        peopleListRecycler = findViewById(R.id.personListRecycler);
        PeopleAdapter adapter = new PeopleAdapter();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        peopleListRecycler.setLayoutManager(manager);
        peopleListRecycler.setAdapter(adapter);
        adapter.setPeople(getPersonsFromDB());
        adapter.notifyDataSetChanged();
        startService(new Intent(this, AlarmService.class));

    }

    public void fillDefaultDataBase() {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        for (PersonEnum person : PersonEnum.values()) {
            ContentValues cv = new ContentValues();
            cv.put("firstName", person.getFirstName());
            cv.put("lastName", person.getLastName());
            cv.put("patronymic", person.getPatronymic());
            cv.put("birthDate", person.getBirthDate());
            long rowID = database.insert(DBHelper.PEOPLE_TABLE, null, cv);
            Log.d(this.getClass().getSimpleName(), "row inserted, ID = " + rowID);
        }
        database.close();
    }

    public List<Person> getPersonsFromDB() {
        List<Person> people = new ArrayList<>();
        Cursor c = getContentResolver().query(MyContentProvider.PEOPLE_CONTENT_URI, null, null,
                null, null);

        if (c != null && c.moveToFirst()) {
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
        }
        return people;
    }
}
