package com.example.pawel.myapp.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pawel.myapp.Model.DataModel;
import com.example.pawel.myapp.R;
import com.example.pawel.myapp.RecyclerViewClickListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<DataModel> dataModelArrayList;
    private RecyclerViewClickListener recyclerViewClickListener;


    public Adapter(  RecyclerViewClickListener recyclerViewClickListener, ArrayList<DataModel> dataModelArrayList){


        this.dataModelArrayList = dataModelArrayList;
        this.recyclerViewClickListener=recyclerViewClickListener;
    }

    @Override
    public Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_home_category, parent, false);
        MyViewHolder holder = new MyViewHolder(view, recyclerViewClickListener);

        return holder;


    }

    @Override
    public void onBindViewHolder(Adapter.MyViewHolder holder, int position) {
        holder.name.setText(dataModelArrayList.get(position).getName());
        Picasso.get().load(dataModelArrayList.get(position).getImgUrl()).into(holder.category_image);

    }

    @Override
    public int getItemCount() {
        return dataModelArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView name;
        ImageView category_image;
        RecyclerViewClickListener recyclerViewClickListener;

        public MyViewHolder(View itemView, RecyclerViewClickListener recyclerViewClickListener) {
            super(itemView);
            this.recyclerViewClickListener=recyclerViewClickListener;
            name = (TextView) itemView.findViewById(R.id.name);
            category_image = (ImageView) itemView.findViewById(R.id.category_image);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
        recyclerViewClickListener.onClick(getAdapterPosition());
        }
    }}