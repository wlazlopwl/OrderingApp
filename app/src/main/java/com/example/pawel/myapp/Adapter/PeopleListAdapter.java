package com.example.pawel.myapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pawel.myapp.Model.UserModel;
import com.example.pawel.myapp.R;
import com.example.pawel.myapp.Worker.WorkerUserDetailActivity;

import java.util.ArrayList;

public class PeopleListAdapter extends RecyclerView.Adapter<PeopleListAdapter.MyViewholder> {
    public ArrayList<UserModel> PeopleListForWorker;

    public PeopleListAdapter(ArrayList<UserModel> PeopleListForWorker) {
        this.PeopleListForWorker = PeopleListForWorker;
    }

    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_worker_user_list, viewGroup, false);
        PeopleListAdapter.MyViewholder adapter = new MyViewholder(view, new MyViewholder.MyClickListener() {

            @Override
            public void getDetail(int i) {


            }
        });


        return adapter;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewholder myViewholder, int i) {

        myViewholder.name.setText(PeopleListForWorker.get(i).getName() + " ");
        myViewholder.surname.setText(PeopleListForWorker.get(i).getSurname());
        String idU = PeopleListForWorker.get(i).getId();
        //TODO: change
        final String idUser = idU;
        myViewholder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, WorkerUserDetailActivity.class);
                intent.putExtra("idUser", idUser);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return (PeopleListForWorker == null) ? 0 : PeopleListForWorker.size();
    }

    public static class MyViewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name, surname;
        MyClickListener listener;
        CardView cardView;

        public MyViewholder(@NonNull View itemView, MyClickListener listener) {
            super(itemView);
            this.listener = listener;
            name = (TextView) itemView.findViewById(R.id.layout_user_list_name);
            surname = (TextView) itemView.findViewById(R.id.layout_user_list_surname);
            cardView = (CardView) itemView.findViewById(R.id.layout_user_list_cardview);
            cardView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.layout_user_list_cardview:
                    break;
            }

        }

        public interface MyClickListener {
            void getDetail(int p);
        }
    }
}
