package com.example.pawel.myapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.BreakIterator;
import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {


    private LayoutInflater inflater;
    private ArrayList<DataProduct> dataCartArrayList;


    public CartAdapter (ArrayList<DataProduct> dataCartArrayList){
        this.dataCartArrayList=dataCartArrayList;
    }


    @Override
    public CartAdapter.MyViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_product_cart,parent,false);

        CartAdapter.MyViewHolder holder = new CartAdapter.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder( CartAdapter.MyViewHolder holder, int position) {
        holder.name.setText(dataCartArrayList.get(position).getName());
    }

    @Override
    public int getItemCount() {


        return dataCartArrayList.size();
    }

     class MyViewHolder extends RecyclerView.ViewHolder {
         TextView name;

         public MyViewHolder( View itemView) {
             super(itemView);
             name=(TextView) itemView.findViewById(R.id.product_cart_name);

         }
     }
}
