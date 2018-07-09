package com.sadwyn.institute.birthdayreminder.presentation.presenter;

import android.view.View;

import com.sadwyn.institute.birthdayreminder.presentation.view.BaseView;

public abstract class BasePresenter<T extends BaseView> {
    public abstract void onViewCreated(T view);
    public abstract void onViewDestroyed();
}
