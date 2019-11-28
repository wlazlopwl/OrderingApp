package com.example.pawel.myapp.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pawel.myapp.Model.UserModel;
import com.example.pawel.myapp.R;

import java.util.ArrayList;

public class PeopleListAdapter extends RecyclerView.Adapter<PeopleListAdapter.MyViewholder> {
    public ArrayList<UserModel> PeopleListForWorker;

    public PeopleListAdapter(ArrayList<UserModel> PeopleListForWorker){
        this.PeopleListForWorker=PeopleListForWorker;
    }

    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_worker_user_list, viewGroup, false);
        PeopleListAdapter.MyViewholder adapter = new MyViewholder(view, new MyViewholder.MyClickListener(){

            @Override
            public void getUserName(int i) {

            }
        });


    return adapter;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewholder myViewholder, int i) {

    myViewholder.name.setText(PeopleListForWorker.get(i).getName()+" ");
    myViewholder.surname.setText(PeopleListForWorker.get(i).getSurname());
    }

    @Override
    public int getItemCount() {
         return (PeopleListForWorker == null) ? 0 : PeopleListForWorker.size();
    }

    public static class MyViewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name, surname;
        MyClickListener listener;

        public MyViewholder(@NonNull View itemView, MyClickListener listener) {
            super(itemView);
            this.listener=listener;
            name = (TextView) itemView.findViewById(R.id.layout_user_list_name);
            surname = (TextView) itemView.findViewById(R.id.layout_user_list_surname);
            name.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            switch (v.getId()){
                case R.id.layout_user_list_name:
                    Log.d("test",""+v.getId());
            }

        }

        public interface MyClickListener {
            void getUserName(int p);
        }
    }
}
