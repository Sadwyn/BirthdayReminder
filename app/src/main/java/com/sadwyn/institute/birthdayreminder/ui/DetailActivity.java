package com.sadwyn.institute.birthdayreminder.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sadwyn.institute.birthdayreminder.R;
import com.sadwyn.institute.birthdayreminder.data.Person;

public class DetailActivity extends AppCompatActivity {
    private TextView firstName;
    private TextView lastName;
    private TextView patronymic;
    private TextView birthDate;
    private Button phone;
    private Person person;

    public static final int REQUEST_CODE_CALL = 100;

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

        person = (Person) getIntent().getSerializableExtra("person");
        if (person != null) {
            firstName.setText(String.format("Имя: %s", person.getFirstName()));
            lastName.setText(String.format("Фамилия: %s", person.getLastName()));
            patronymic.setText(String.format("Отчество: %s", person.getPatronymic()));
            birthDate.setText(String.format("Дата рождения: %s", person.getBirthDate()));
            phone.setText(String.format("Мобильный: %s", person.getPhone()));
            phone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + person.getPhone()));
                    startActivity(intent);
                }
            });

        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}