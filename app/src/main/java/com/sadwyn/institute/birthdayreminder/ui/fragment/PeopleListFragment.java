package com.sadwyn.institute.birthdayreminder.ui.fragment;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.JobIntentService;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sadwyn.institute.birthdayreminder.R;
import com.sadwyn.institute.birthdayreminder.data.Person;
import com.sadwyn.institute.birthdayreminder.presentation.presenter.PeopleListPresenter;
import com.sadwyn.institute.birthdayreminder.presentation.view.PeopleListView;
import com.sadwyn.institute.birthdayreminder.service.AlarmService;
import com.sadwyn.institute.birthdayreminder.ui.activity.MainActivity;
import com.sadwyn.institute.birthdayreminder.ui.adapter.PeopleAdapter;

import java.util.List;

public class PeopleListFragment extends BaseFragment implements PeopleAdapter.OnPersonClickListener, PeopleListView {
    private PeopleListPresenter presenter = new PeopleListPresenter();


    public OnPersonClickListener listener;
    private RecyclerView peopleListRecycler;
    private PeopleAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_people_list_frament, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        peopleListRecycler = view.findViewById(R.id.people_recycler_view);

        adapter = new PeopleAdapter(this);
        ((MainActivity)getActivity()).getSupportActionBar().setTitle("People List");
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        peopleListRecycler.setLayoutManager(manager);
        peopleListRecycler.setAdapter(adapter);
        assert getContext() != null;
        JobIntentService.enqueueWork(getContext().getApplicationContext(), AlarmService.class, 666, new Intent());
        presenter.onViewCreated(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnPersonClickListener) {
            listener = (OnPersonClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnPersonClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onViewDestroyed();
    }

    @Override
    public void addPersonToList(List<Person> people) {
        adapter.setPeople(people);
    }

    @Override
    public ContentResolver getContentResolver() {
        return getActivity().getContentResolver();
    }

    public interface OnPersonClickListener {
        void onPersonClick(Person person);
    }

    @Override
    public void onPersonClick(Person person) {
        listener.onPersonClick(person);
    }

    public static PeopleListFragment newInstance() {
        return new PeopleListFragment();
    }
}
