package com.sadwyn.institute.birthdayreminder.presentation.view;

import android.content.ContentResolver;

import com.sadwyn.institute.birthdayreminder.data.Person;

import java.util.List;

public interface PeopleListView extends BaseView{
    void addPersonToList(List<Person> people);
}
