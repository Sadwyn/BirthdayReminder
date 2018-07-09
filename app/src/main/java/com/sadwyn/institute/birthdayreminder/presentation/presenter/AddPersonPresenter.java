package com.sadwyn.institute.birthdayreminder.presentation.presenter;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.text.TextUtils;

import com.sadwyn.institute.birthdayreminder.contentprovider.MyContentProvider;
import com.sadwyn.institute.birthdayreminder.presentation.view.AddPersonView;
import com.sadwyn.institute.birthdayreminder.ui.fragment.AddPersonFragment;

import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class AddPersonPresenter extends BasePresenter<AddPersonView> {
    private AddPersonView view;
    private CompositeDisposable disposables = new CompositeDisposable();
    private static final String DATE_PATTERN =
            "(0?[1-9]|[12][0-9]|3[01]).(0?[1-9]|1[012]).((19|20)\\d\\d)";

    boolean validateSuccessful;

    @Override
    public void onViewCreated(AddPersonView view) {
        this.view = view;
    }

    @Override
    public void onViewDestroyed() {
        disposables.clear();
        view = null;
    }

    public void addPersonToDataBase(Map<String, String> data) {


        disposables.add(Observable.just(data.entrySet())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(Observable::fromIterable)
                .doOnNext(this::validate)
                .toMap(Map.Entry::getKey, Map.Entry::getValue)
                .flatMap(this::addPersonToTable)
                .subscribe(o -> view.onPersonSaved()));

    }

    private void validate(Map.Entry<String, String> field) {
        if (TextUtils.isEmpty(field.getValue())) {
            view.onSavingError(field.getKey() + " is Empty");
            disposables.clear();
        } else if (field.getKey().equals(AddPersonFragment.DATE)) {
            Pattern pattern = Pattern.compile(DATE_PATTERN);
            Matcher matcher = pattern.matcher(field.getValue());
            if (!matcher.matches()) {
                view.onSavingError("Date is not valid, use dd.mm.yyyy pattern");
                disposables.clear();
            }
        }

    }

    private Single addPersonToTable(Map<String, String> dataMap) {
        ContentValues cv = new ContentValues();
        cv.put("firstName", dataMap.get(AddPersonFragment.FIRST_NAME));
        cv.put("lastName", dataMap.get(AddPersonFragment.LAST_NAME));
        cv.put("patronymic", dataMap.get(AddPersonFragment.PATRONYMIC));
        cv.put("birthDate", dataMap.get(AddPersonFragment.DATE));
        cv.put("phone", dataMap.get(AddPersonFragment.PHONE));
        ContentResolver contentResolver = view.getContentResolver();
        contentResolver.insert(MyContentProvider.PEOPLE_CONTENT_URI, cv);
        return Single.just(true);
    }
}
