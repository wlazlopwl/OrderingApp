package com.example.pawel.myapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pawel.myapp.Const;
import com.example.pawel.myapp.Model.DataModel;
import com.example.pawel.myapp.R;
import com.example.pawel.myapp.RecyclerViewClickListener;
import com.example.pawel.myapp.User.CategoryListActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;



public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.MyViewHolder> {

    private ArrayList<DataModel> dataModelArrayList;
    private RecyclerViewClickListener recyclerViewClickListener;
    public Context context;




    public CategoryListAdapter(RecyclerViewClickListener recyclerViewClickListener, ArrayList<DataModel> dataModelArrayList) {


        this.dataModelArrayList = dataModelArrayList;
        this.recyclerViewClickListener = recyclerViewClickListener;
    }

    @Override
    public CategoryListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Activity activity = (Activity) parent.getContext();
        activity.getClass().getSimpleName();
        if (parent.getContext()== CategoryListActivity.context) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_category_vertical, parent, false);

        }

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_category_vertical, parent, false);
        MyViewHolder holder = new MyViewHolder(view, recyclerViewClickListener);

        return holder;


    }

    @Override
    public void onBindViewHolder(CategoryListAdapter.MyViewHolder holder, int position) {
        holder.name.setText(dataModelArrayList.get(position).getName());
        Picasso.get().load(dataModelArrayList.get(position).getImgUrl()).into(holder.category_image);

    }

    @Override
    public int getItemCount() {
        return dataModelArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView name;
        RecyclerViewClickListener recyclerViewClickListener;
        ImageView category_image;

        public MyViewHolder(View itemView, RecyclerViewClickListener recyclerViewClickListener) {
            super(itemView);
            this.recyclerViewClickListener = recyclerViewClickListener;
            name = (TextView) itemView.findViewById(R.id.category_name);
            category_image = (ImageView) itemView.findViewById(R.id.category_image);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            recyclerViewClickListener.onClick(getAdapterPosition());
        }
    }
}
