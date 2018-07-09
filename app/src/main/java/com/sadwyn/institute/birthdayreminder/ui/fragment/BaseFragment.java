package com.sadwyn.institute.birthdayreminder.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.widget.Toolbar;

import com.sadwyn.institute.birthdayreminder.R;
import com.sadwyn.institute.birthdayreminder.ui.activity.MainActivity;

public abstract class BaseFragment extends Fragment {
    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawerLayout;
    private android.support.v7.widget.Toolbar toolbar;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        drawerLayout = ((MainActivity) getActivity()).getDrawerLayout();
        toggle = ((MainActivity) getActivity()).getToggle();
        toolbar = ((MainActivity) getActivity()).getToolbar();
        initializeNavigation(isNeedHomeAsUp());
    }

    private void initializeNavigation(boolean isNeedHomeAsUp) {
        if (isNeedHomeAsUp) {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, GravityCompat.START);
            toggle.setHomeAsUpIndicator(R.drawable.ic_left_arrow);
            toolbar.setNavigationOnClickListener(v -> getActivity().getSupportFragmentManager().popBackStack());
            toggle.syncState();
        } else {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, GravityCompat.START);
            toolbar.setNavigationOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));
            toggle.setHomeAsUpIndicator(R.drawable.ic_hamburger);
            toggle.syncState();
        }
    }

    boolean isNeedHomeAsUp() {
        return false;
    }
}
