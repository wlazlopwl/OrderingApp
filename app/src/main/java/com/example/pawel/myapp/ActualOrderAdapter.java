package com.example.pawel.myapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ActualOrderAdapter extends RecyclerView.Adapter<ActualOrderAdapter.MyViewHolder> {


    private ArrayList<DataProduct> ActualOrderArrayList;

    private Context mContext;



    public ActualOrderAdapter( ArrayList<DataProduct> ActualOrderArrayList) {
        this.ActualOrderArrayList = ActualOrderArrayList;

    }

    @Override
    public ActualOrderAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_user_actual_order, viewGroup,false);
        ActualOrderAdapter.MyViewHolder adapter = new MyViewHolder(itemView);

        return adapter;
    }

    @Override
    public void onBindViewHolder(ActualOrderAdapter.MyViewHolder holder, int i) {
    holder.name.setText(ActualOrderArrayList.get(i).getName());


ArrayList<DataProduct> ChildOrderList = ActualOrderArrayList.get(i).getDataProductList();

    ChildOrderAdapter childAdapter = new ChildOrderAdapter(mContext, ChildOrderList);
        holder.childRV.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL,false));
        holder.childRV.setHasFixedSize(true);
        holder.childRV.setVisibility(View.VISIBLE);
        holder.childRV.setClickable(true);
        holder.childRV.setAdapter(childAdapter);
    }

    @Override
    public int getItemCount() {
        return ActualOrderArrayList.size();
    }

    public class MyViewHolder  extends RecyclerView.ViewHolder {
        TextView name;
        RecyclerView childRV;
        public MyViewHolder( View itemView) {
            super(itemView);
            name= (TextView) itemView.findViewById(R.id.parent_rv_orderNr_textview);
            childRV= (RecyclerView) itemView.findViewById(R.id.child_order_product_rv);
        }
    }
}
