package com.example.pawel.myapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Console;
import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<DataProduct> dataProductArrayList;

    public ProductAdapter(   ArrayList<DataProduct> dataProductArrayList){

this.dataProductArrayList=dataProductArrayList;

    }



    @Override
    public ProductAdapter.MyViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_product,parent,false);

        ProductAdapter.MyViewHolder holder = new ProductAdapter.MyViewHolder(view, new MyViewHolder.MyClickListener() {



            public void AddItem(int p){

                String a=dataProductArrayList.get(p).getName();
                String b=dataProductArrayList.get(p).getId();
                CartActivity.addItem(a);
                Log.d("sad", + p + a + "id: "+ b );
            }

        });
        return holder;
    }

    @Override
    public void onBindViewHolder( ProductAdapter.MyViewHolder holder, int position) {
    holder.name.setText(dataProductArrayList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return dataProductArrayList.size();
    }

     static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        MyClickListener listener;

         TextView name;
         Button add;

         public MyViewHolder(View itemView, MyClickListener listener) {
             super(itemView);
             name= (TextView) itemView.findViewById(R.id.product_name);
             add = (Button)   itemView.findViewById(R.id.btn_plus_product);
             this.listener = listener;
            add.setOnClickListener(this);
         }


         @Override
         public void onClick(View view) {


             switch (view.getId()) {
                 case R.id.btn_plus_product:
                     listener.AddItem(this.getLayoutPosition());

                     break;

         }}

         public interface MyClickListener {
             void AddItem(int p);
         }
     }


}