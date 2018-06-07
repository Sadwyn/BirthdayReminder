package com.sadwyn.institute.birthdayreminder.ui;

import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.JobIntentService;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.sadwyn.institute.birthdayreminder.R;
import com.sadwyn.institute.birthdayreminder.contentprovider.MyContentProvider;
import com.sadwyn.institute.birthdayreminder.data.DBHelper;
import com.sadwyn.institute.birthdayreminder.data.Person;
import com.sadwyn.institute.birthdayreminder.data.PersonEnum;
import com.sadwyn.institute.birthdayreminder.receiver.AlarmReceiver;
import com.sadwyn.institute.birthdayreminder.service.AlarmService;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PeopleAdapter.OnPersonClickListener {
    private RecyclerView peopleListRecycler;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Главная");
        dbHelper = new DBHelper(getApplicationContext());
        if (!dbHelper.doesDatabaseExist(getApplicationContext(), DBHelper.PEOPLE_DB)) {
            fillDefaultDataBase();
        }

        peopleListRecycler = findViewById(R.id.personListRecycler);
        PeopleAdapter adapter = new PeopleAdapter(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        peopleListRecycler.setLayoutManager(manager);
        peopleListRecycler.setAdapter(adapter);
        adapter.setPeople(getPersonsFromDB());
        adapter.notifyDataSetChanged();
        JobIntentService.enqueueWork(getApplicationContext(), AlarmService.class, 666, new Intent());

        ComponentName receiver = new ComponentName(getApplicationContext(), AlarmReceiver.class);
        PackageManager pm = getApplicationContext().getPackageManager();
        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);

    }

    public void fillDefaultDataBase() {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        for (PersonEnum person : PersonEnum.values()) {
            ContentValues cv = new ContentValues();
            cv.put("firstName", person.getFirstName());
            cv.put("lastName", person.getLastName());
            cv.put("patronymic", person.getPatronymic());
            cv.put("birthDate", person.getBirthDate());
            cv.put("phone", person.getPhoneNumber());
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
        }
        return people;
    }

    @Override
    public void onPersonClick(Person person) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("person", person);
        startActivity(intent);
    }
}
