package com.sadwyn.institute.birthdayreminder.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.sadwyn.institute.birthdayreminder.R;
import com.sadwyn.institute.birthdayreminder.data.Person;

public class DetailActivity extends AppCompatActivity {
    private TextView firstName;
    private TextView lastName;
    private TextView patronymic;
    private TextView birthDate;
    private TextView phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        patronymic = findViewById(R.id.patronymic);
        birthDate = findViewById(R.id.birthDate);
        phone = findViewById(R.id.phoneNumber);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("Детали");

        Person person = (Person) getIntent().getSerializableExtra("person");
        if (person != null) {
            firstName.setText(String.format("Имя %s", person.getFirstName()));
            lastName.setText(String.format("Фамилия %s", person.getLastName()));
            patronymic.setText(String.format("Отчество %s", person.getPatronymic()));
            birthDate.setText(String.format("Дата рождения %s", person.getBirthDate()));
            phone.setText(String.format("Мобильный %s", person.getPhone()));
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
