package com.example.pawel.myapp.Adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pawel.myapp.Model.CategoryModel;
import com.example.pawel.myapp.R;
import com.example.pawel.myapp.RecyclerViewClickListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {

    public static ArrayList<CategoryModel> categoryModelArrayList;
    private RecyclerViewClickListener recyclerViewClickListener;


    public CategoryAdapter(RecyclerViewClickListener recyclerViewClickListener, ArrayList<CategoryModel> categoryModelArrayList) {


        this.categoryModelArrayList = categoryModelArrayList;
        this.recyclerViewClickListener = recyclerViewClickListener;
    }

    @Override
    public CategoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Activity activity = (Activity) parent.getContext();
        if (activity.getClass().getSimpleName().contains("Main")) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_home_category, parent, false);
            MyViewHolder holder = new MyViewHolder(view, recyclerViewClickListener);
            return holder;


        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_category_vertical, parent, false);
            MyViewHolder holder = new MyViewHolder(view, recyclerViewClickListener);
            return holder;


        }


    }

    @Override
    public void onBindViewHolder(CategoryAdapter.MyViewHolder holder, int position) {
        holder.name.setText(categoryModelArrayList.get(position).getName());
        Picasso.get().load(categoryModelArrayList.get(position).getImgUrl()).into(holder.category_image);

    }

    @Override
    public int getItemCount() {
        return categoryModelArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView name;
        ImageView category_image;
        RecyclerViewClickListener recyclerViewClickListener;

        public MyViewHolder(View itemView, RecyclerViewClickListener recyclerViewClickListener) {
            super(itemView);
            this.recyclerViewClickListener = recyclerViewClickListener;
            name = itemView.findViewById(R.id.category_name);
            category_image = itemView.findViewById(R.id.category_image);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            recyclerViewClickListener.onClick(getAdapterPosition());
        }
    }
}