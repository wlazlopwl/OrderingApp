

package com.example.pawel.myapp.Adapter;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.pawel.myapp.Admin.SimpleUserBottomOption;
import com.example.pawel.myapp.Model.UserModel;
import com.example.pawel.myapp.R;
import com.example.pawel.myapp.User.UserArchivalOrderFragment;

import java.util.ArrayList;

public class WorkerDialogListAdapter extends RecyclerView.Adapter<WorkerDialogListAdapter.MyViewHolder> {
    public ArrayList<UserModel> WorkerList;
    public  int selectedPosition = -1;
    public static String idWorker;

    public WorkerDialogListAdapter(ArrayList<UserModel> WorkerList) {
        this.WorkerList = WorkerList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, int i) {
        final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_select_worker_dialog_item, viewGroup, false);
        final WorkerDialogListAdapter.MyViewHolder adapter = new MyViewHolder(view, new MyViewHolder.MyClickListener() {

            @Override
            public void checkItem(int p) {



            }




        });
        return adapter;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewholder, int i) {

        myViewholder.name.setText(WorkerList.get(i).getName() + " ");
        myViewholder.surname.setText(WorkerList.get(i).getSurname());
        myViewholder.checkBox.setClickable(false);

        if (selectedPosition == i) {
            myViewholder.checkBox.setChecked(true);
            myViewholder.cardView.setCardBackgroundColor(Color.argb(120, 204, 255, 144));

        } else {
            myViewholder.checkBox.setChecked(false);
            myViewholder.cardView.setCardBackgroundColor(Color.TRANSPARENT);

        }


        myViewholder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myViewholder.checkBox.performClick();
                if (myViewholder.checkBox.isChecked()) {
                    selectedPosition = myViewholder.getAdapterPosition();
                    idWorker = WorkerList.get(selectedPosition).getId();
                    SimpleUserBottomOption.selectWorker.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);

                } else {
                    selectedPosition = -1;
                    idWorker = String.valueOf(selectedPosition);
                    SimpleUserBottomOption.selectWorker.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);


                }
                notifyDataSetChanged();
            }


        });

    }

    @Override
    public int getItemCount() {
        return (WorkerList == null) ? 0 : WorkerList.size();

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name, surname;
        CardView cardView;
        CheckBox checkBox;

        MyClickListener listener;

        public MyViewHolder(@NonNull View itemView, MyClickListener listener) {
            super(itemView);
            this.listener = listener;
            name = (TextView) itemView.findViewById(R.id.layout_select_worker_name);
            surname = (TextView) itemView.findViewById(R.id.layout_select_worker_surname);
            cardView = (CardView) itemView.findViewById(R.id.layout_select_worker_cardview);
            checkBox = (CheckBox) itemView.findViewById(R.id.layout_select_worker_checkbox);
            cardView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.layout_select_worker_cardview:
                    listener.checkItem(getLayoutPosition());



                    break;
            }

        }

        public interface MyClickListener {
            void checkItem(int p);
        }
    }
}
