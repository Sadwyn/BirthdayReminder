package com.sadwyn.institute.birthdayreminder.ui.adapter;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
        notifyDataSetChanged();
    }

    public PeopleAdapter(OnPersonClickListener context) {
        if (context != null) {
            onPersonClickListener = context;
        } else {
            throw new NullPointerException("Context is null " + OnPersonClickListener.class.getSimpleName());
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
        CardView root;
        ConstraintLayout layout;
        TextView name;
        Button btnDial;

        public PersonViewHolder(View itemView) {
            super(itemView);
            root = itemView.findViewById(R.id.peopleHolder);
            layout = itemView.findViewById(R.id.layout);
            name = itemView.findViewById(R.id.tvName);
            btnDial = itemView.findViewById(R.id.btn_dial);
            itemView.setOnClickListener(this);
            btnDial.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + people.get(getAdapterPosition()).getPhone()));
                v.getContext().startActivity(intent);
            });
        }

        @Override
        public void onClick(View v) {
            onPersonClickListener.onPersonClick(people.get(getAdapterPosition()));
        }
    }

    public interface OnPersonClickListener {
        void onPersonClick(Person person);
    }
}
