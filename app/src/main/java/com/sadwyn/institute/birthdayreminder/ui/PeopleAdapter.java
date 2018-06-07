package com.sadwyn.institute.birthdayreminder.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sadwyn.institute.birthdayreminder.R;
import com.sadwyn.institute.birthdayreminder.data.Person;

import java.util.ArrayList;
import java.util.List;

public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.PersonViewHolder> {
    private List<Person> people = new ArrayList<>();
    private OnPersonClickListener onPersonClickListener;

    public void setPeople(List<Person> people) {
        this.people = people;
    }

    public PeopleAdapter(Context context) {
        if (context instanceof OnPersonClickListener) {
            onPersonClickListener = (OnPersonClickListener) context;
        }
        else {
            throw new ClassCastException("instance of class is not implementing " + OnPersonClickListener.class.getSimpleName());
        }
    }

    @NonNull
    @Override
    public PeopleAdapter.PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.person_holder_layout, parent, false);
        return new PersonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PeopleAdapter.PersonViewHolder holder, int position) {
        String firstName = people.get(position).getFirstName();
        String patronymic = people.get(position).getPatronymic();
        String lastName = people.get(position).getLastName();
        if (!TextUtils.isEmpty(firstName) && !TextUtils.isEmpty(lastName) && !TextUtils.isEmpty(patronymic)) {
            holder.name.setText(String.format("%s %s %s", lastName, firstName, patronymic));
        }
    }

    @Override
    public int getItemCount() {
        return people.size();
    }


    class PersonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ConstraintLayout root;
        CardView cardView;
        TextView name;

        public PersonViewHolder(View itemView) {
            super(itemView);
            root = itemView.findViewById(R.id.peopleHolder);
            cardView = itemView.findViewById(R.id.cardView);
            name = itemView.findViewById(R.id.tvName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onPersonClickListener.onPersonClick(people.get(getAdapterPosition()));
        }
    }

    interface OnPersonClickListener {
        void onPersonClick(Person person);
    }
}
