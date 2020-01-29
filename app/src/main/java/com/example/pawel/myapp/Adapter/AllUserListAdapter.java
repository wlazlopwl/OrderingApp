package com.example.pawel.myapp.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.telephony.mbms.MbmsErrors;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pawel.myapp.Admin.SimpleUserBottomOption;
import com.example.pawel.myapp.Model.UserModel;
import com.example.pawel.myapp.R;

import java.util.ArrayList;

public class AllUserListAdapter extends RecyclerView.Adapter<AllUserListAdapter.MyViewHolder> {
    Context mContext;
    public ArrayList<UserModel> AllUserList;
    static String id;
    static Integer position;

    public AllUserListAdapter(ArrayList<UserModel> AllUserList) {
        this.AllUserList = AllUserList;
    }

    public AllUserListAdapter() {

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_all_user_list, viewGroup, false);
        AllUserListAdapter.MyViewHolder adapter = new MyViewHolder(view, new MyViewHolder.MyClickListener(){


            @Override
            public void getId(final int p) {
                position = p;
                id = AllUserList.get(p).getId();

            }
        });



        return adapter;
    }

    @Override
    public void onBindViewHolder(@NonNull AllUserListAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.name.setText(AllUserList.get(i).getName());
        myViewHolder.surname.setText(AllUserList.get(i).getSurname()+" ");


    }

    @Override
    public int getItemCount() {
        return (AllUserList == null) ? 0 : AllUserList.size();

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name, surname;
        MyClickListener listener;
        CardView cardView;
        Context ctx;
        public MyViewHolder(@NonNull View itemView, MyClickListener listener) {

            super(itemView);
            this.listener=listener;
            name=(TextView) itemView.findViewById(R.id.layout_all_user_list_name);
            surname=(TextView) itemView.findViewById(R.id.layout_all_user_list_surname);
            cardView = (CardView) itemView.findViewById(R.id.layout_all_user_list_cardview);

            cardView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.layout_all_user_list_cardview:
                    listener.getId(this.getLayoutPosition());
                   ctx =v.getContext();

                    SimpleUserBottomOption bottomSheetDialogFragment = new SimpleUserBottomOption();
                    String name = this.name.getText().toString();
                    String surname = this.surname.getText().toString();


                    Bundle bundle = new Bundle();
                    bundle.putString("name", name);
                    bundle.putString("surname", surname);
                    bundle.putString("userId", id);
                    bundle.putInt("position",position);

                    bottomSheetDialogFragment.setArguments(bundle);
                    bottomSheetDialogFragment.show(((FragmentActivity)ctx).getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
                    break;

            }

        }

        public interface MyClickListener {
                 void getId(int p);

        }
    }

}
