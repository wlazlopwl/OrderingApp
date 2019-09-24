package com.example.pawel.myapp;

import android.content.Context;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;

public class RecycleVievAdapter extends RecyclerView.Adapter<RecycleVievAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> nNames=new ArrayList<>();

    private ArrayList<String> nImageUrls=new ArrayList<>();
    private Context mContext;

    public RecycleVievAdapter(Context mContext, ArrayList<String> nNames ) {
        this.nNames = nNames;
        this.mContext = mContext;
    }

    @NonNull
    @Override

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_home_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onCreateViewHolder: called.");
        holder.category_name.setText(nNames.get(position));
    }

    @Override
    public int getItemCount() {
        return nNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView category_name;
        ImageView category_image;

        public ViewHolder(View itemView) {
            super(itemView);
            category_image = itemView.findViewById(R.id.category_image);
            category_name = itemView.findViewById(R.id.category_name);
        }
    }
}
