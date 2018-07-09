package com.sadwyn.institute.birthdayreminder.ui.fragment;


import android.content.ContentResolver;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.JobIntentService;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.sadwyn.institute.birthdayreminder.R;
import com.sadwyn.institute.birthdayreminder.presentation.presenter.AddPersonPresenter;
import com.sadwyn.institute.birthdayreminder.presentation.view.AddPersonView;
import com.sadwyn.institute.birthdayreminder.service.AlarmService;
import com.sadwyn.institute.birthdayreminder.ui.activity.MainActivity;

import java.util.LinkedHashMap;
import java.util.Map;

public class AddPersonFragment extends BaseFragment implements AddPersonView {
    public static final String FIRST_NAME = "First Name";
    public static final String LAST_NAME = "Last Name";
    public static final String PATRONYMIC = "Patronymic";
    public static final String DATE = "Date";
    public static final String PHONE = "Phone";
    private AddPersonPresenter presenter = new AddPersonPresenter();

    private TextInputEditText etFirstName;
    private TextInputEditText etLastName;
    private TextInputEditText etPatronymic;
    private TextInputEditText etDate;
    private TextInputEditText etPhone;
    private Button btnSave;

    public AddPersonFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_person, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Add Person");
        etFirstName = view.findViewById(R.id.et_first_name);
        etLastName = view.findViewById(R.id.et_last_name);
        etPatronymic = view.findViewById(R.id.et_patronymic);
        etDate = view.findViewById(R.id.et_date);
        btnSave = view.findViewById(R.id.btn_save);
        etPhone = view.findViewById(R.id.et_phone);
        btnSave.setOnClickListener(v -> {
                    btnSave.setEnabled(false);
                    Map<String, String> map = new LinkedHashMap<>();
                    map.put(FIRST_NAME, etFirstName.getText().toString());
                    map.put(LAST_NAME, etLastName.getText().toString());
                    map.put(PATRONYMIC, etPatronymic.getText().toString());
                    map.put(DATE, etDate.getText().toString());
                    map.put(PHONE, etPhone.getText().toString());
                    presenter.addPersonToDataBase(map);
                }
        );
        presenter.onViewCreated(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onViewDestroyed();
    }

    public static AddPersonFragment newInstance() {
        return new AddPersonFragment();
    }

    @Override
    public void onPersonSaved() {
        btnSave.setEnabled(true);
        Toast.makeText(getContext(), "Person was successfully added to database", Toast.LENGTH_SHORT).show();
        JobIntentService.enqueueWork(getContext().getApplicationContext(), AlarmService.class, 666, new Intent());
    }

    @Override
    public void onSavingError(String error) {
        btnSave.setEnabled(true);
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public ContentResolver getContentResolver() {
        return getActivity().getContentResolver();
    }
}
