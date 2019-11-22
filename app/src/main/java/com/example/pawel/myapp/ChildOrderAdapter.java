package com.example.pawel.myapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ChildOrderAdapter extends RecyclerView.Adapter<ChildOrderAdapter.MyViewHolder> {


    public ArrayList<DataProduct> ChildOrderList ;
    private Context mContext;



    public ChildOrderAdapter(Context context,ArrayList<DataProduct> ChildOrderList) {
        this.ChildOrderList = ChildOrderList;
        this.mContext=context;


    }



    @Override
    public ChildOrderAdapter.MyViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_order_simple_item, viewGroup,false);


        ChildOrderAdapter.MyViewHolder adapter = new MyViewHolder(itemView);
        return adapter;

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.name.setText((ChildOrderList.get(i).getName()));
        myViewHolder.name.setText("test");

    }

    @Override
    public int getItemCount() {
//
        return   (ChildOrderList == null) ? 0 :ChildOrderList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.product_order_name);

        }
    }
}
