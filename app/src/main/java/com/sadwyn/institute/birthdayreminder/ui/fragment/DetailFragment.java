package com.sadwyn.institute.birthdayreminder.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.sadwyn.institute.birthdayreminder.R;
import com.sadwyn.institute.birthdayreminder.data.Person;
import com.sadwyn.institute.birthdayreminder.ui.activity.MainActivity;

public class DetailFragment extends BaseFragment {
    public static final String PERSON = "person";
    private TextView firstName;
    private TextView lastName;
    private TextView patronymic;
    private TextView birthDate;
    private Button phone;
    private Person person;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        person = (Person) getArguments().getSerializable(PERSON);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        firstName = view.findViewById(R.id.firstName);
        lastName = view.findViewById(R.id.lastName);
        patronymic = view.findViewById(R.id.et_patronymic);
        birthDate = view.findViewById(R.id.birthDate);
        phone = view.findViewById(R.id.phoneNumber);

        if (person != null) {
            firstName.setText(String.format("Имя: %s", person.getFirstName()));
            lastName.setText(String.format("Фамилия: %s", person.getLastName()));
            patronymic.setText(String.format("Отчество: %s", person.getPatronymic()));
            birthDate.setText(String.format("Дата рождения: %s", person.getBirthDate()));
            phone.setText(String.format("Мобильный: %s", person.getPhone()));
            phone.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + person.getPhone()));
                startActivity(intent);
            });
        }
    }

    @Override
    boolean isNeedHomeAsUp() {
        return true;
    }

    public static DetailFragment newInstance(Person person) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(PERSON, person);
        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
}