package com.sadwyn.institute.birthdayreminder.presentation.presenter;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.view.View;

import com.sadwyn.institute.birthdayreminder.contentprovider.MyContentProvider;
import com.sadwyn.institute.birthdayreminder.data.Person;
import com.sadwyn.institute.birthdayreminder.presentation.view.BaseView;
import com.sadwyn.institute.birthdayreminder.presentation.view.PeopleListView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class PeopleListPresenter extends BasePresenter<PeopleListView> {
    private PeopleListView view;
    private CompositeDisposable disposable = new CompositeDisposable();

    public void loadPersonsFromDB(ContentResolver contentResolver) {

        disposable.add(Single.fromCallable(() -> getPeople(contentResolver))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(people -> view.addPersonToList(people)));
    }

    @NonNull
    private List<Person> getPeople(ContentResolver contentResolver) {
        List<Person> people = new ArrayList<>();
        Cursor c = contentResolver.query(MyContentProvider.PEOPLE_CONTENT_URI, null, null,
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
    public void onViewCreated(PeopleListView view) {
        this.view = view;
        loadPersonsFromDB(view.getContentResolver());
    }

    @Override
    public void onViewDestroyed() {
        disposable.clear();
        view = null;
    }
}
