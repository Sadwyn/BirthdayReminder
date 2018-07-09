package com.sadwyn.institute.birthdayreminder.presentation.view;

public interface AddPersonView extends BaseView {
    void onPersonSaved();
    void onSavingError(String error);
}
