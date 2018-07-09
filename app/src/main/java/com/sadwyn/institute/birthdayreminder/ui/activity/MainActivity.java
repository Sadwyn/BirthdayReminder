package com.sadwyn.institute.birthdayreminder.ui.activity;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.sadwyn.institute.birthdayreminder.R;
import com.sadwyn.institute.birthdayreminder.data.Person;
import com.sadwyn.institute.birthdayreminder.receiver.AlarmReceiver;
import com.sadwyn.institute.birthdayreminder.ui.fragment.AddPersonFragment;
import com.sadwyn.institute.birthdayreminder.ui.fragment.DetailFragment;
import com.sadwyn.institute.birthdayreminder.ui.fragment.PeopleListFragment;

public class MainActivity extends AppCompatActivity implements PeopleListFragment.OnPersonClickListener, NavigationView.OnNavigationItemSelectedListener {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;

    public DrawerLayout getDrawerLayout() {
        return drawerLayout;
    }

    public ActionBarDrawerToggle getToggle() {
        return toggle;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigation_view);
        initToolbar();

        if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, PeopleListFragment.newInstance()).commit();
            navigationView.setCheckedItem(R.id.nav_list);
        }
        updateReceiver();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        toggle.setDrawerIndicatorEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(this);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void updateReceiver() {
        ComponentName receiver = new ComponentName(getApplicationContext(), AlarmReceiver.class);
        PackageManager pm = getApplicationContext().getPackageManager();
        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }

    @Override
    public void onPersonClick(Person person) {
        DetailFragment detailFragment = DetailFragment.newInstance(person);
        replaceFragment(detailFragment, true);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        toggle.onConfigurationChanged(newConfig);
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        toggle.syncState();
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_list: {
                replaceFragment(PeopleListFragment.newInstance(), false);
                navigationView.setCheckedItem(item.getItemId());
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            }

            default: {
                replaceFragment(AddPersonFragment.newInstance(), false);
                navigationView.setCheckedItem(item.getItemId());
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            }
        }
        return true;
    }


    private void replaceFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
        transaction.replace(R.id.fragment_container, fragment);
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }
}
