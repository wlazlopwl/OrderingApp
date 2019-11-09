package com.example.pawel.myapp;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<DataProduct> dataProductArrayList;

    public ProductAdapter(ArrayList<DataProduct> dataProductArrayList) {

        this.dataProductArrayList = dataProductArrayList;

    }


    @Override
    public ProductAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_product, parent, false);

        ProductAdapter.MyViewHolder holder = new ProductAdapter.MyViewHolder(view, new MyViewHolder.MyClickListener() {


            public void AddItem(int p) {

                String a = dataProductArrayList.get(p).getName();
                String b = dataProductArrayList.get(p).getId();
                CartActivity.addItem(a);
                Log.d("sad", +p + a + "id: " + b);
            }

        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ProductAdapter.MyViewHolder holder, int position) {
        holder.name.setText(dataProductArrayList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return dataProductArrayList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        MyClickListener listener;

        TextView name;
        ImageView plus, minus;
        EditText mActualNumberProduct;

        public MyViewHolder(View itemView, MyClickListener listener) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.product_name);
            plus = (ImageView) itemView.findViewById(R.id.btn_plus_product);
            minus = (ImageView) itemView.findViewById(R.id.btn_minus_product);

            mActualNumberProduct = (EditText) itemView.findViewById(R.id.actual_number_product);
            this.listener = listener;
            plus.setOnClickListener(this);
            minus.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            int actualNumber = Integer.parseInt(mActualNumberProduct.getText().toString().trim());
            switch (view.getId()) {
                case R.id.btn_plus_product:
                    listener.AddItem(this.getLayoutPosition());

                    mActualNumberProduct.setText(Integer.toString(actualNumber+1));

                    break;
                case R.id.btn_minus_product:
                    if (actualNumber>0) {
                        mActualNumberProduct.setText(Integer.toString(actualNumber-1));

                    }


                    break;

            }
        }

        public interface MyClickListener {
            void AddItem(int p);
        }
    }


}