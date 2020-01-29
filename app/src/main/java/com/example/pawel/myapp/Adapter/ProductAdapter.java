package com.example.pawel.myapp.Adapter;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pawel.myapp.Model.DataProduct;
import com.example.pawel.myapp.User.CartActivity;
import com.example.pawel.myapp.User.ProductListActivity;
import com.example.pawel.myapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.example.pawel.myapp.User.ProductListActivity.ctx;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {
 public static String actualNumberAfterChange;
    private LayoutInflater inflater;
    private ArrayList<DataProduct> dataProductArrayList;

    public static String check;

    public ProductAdapter(ArrayList<DataProduct> dataProductArrayList) {

        this.dataProductArrayList = dataProductArrayList;

    }


    @Override
    public ProductAdapter.MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_product, parent, false);

        ProductAdapter.MyViewHolder holder = new ProductAdapter.MyViewHolder(view, new MyViewHolder.MyClickListener() {


            public void AddItem(int p) {

                String b = dataProductArrayList.get(p).getId() ;


                    ProductListActivity.updateCart(b, "1");

//                if (check=="2")
//                {
//                    ProductListActivity.updateCart(b, check);
//                }
//                CartActivity.addItem(a);



            }

            @Override
            public void DelItem(int p) {
                String b = dataProductArrayList.get(p).getId() ;

                ProductListActivity.updateCart(b, "2");
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ProductAdapter.MyViewHolder holder, int position) {
        holder.name.setText(dataProductArrayList.get(position).getName());
        holder.description.setText(dataProductArrayList.get(position).getDescription());
        Picasso.get().load(dataProductArrayList.get(position).getImgUrl()).into(holder.mProductImage);
    }

    @Override
    public int getItemCount() {
        return dataProductArrayList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        MyClickListener listener;

        TextView name, description;
        ImageView mProductImage;
        Button plus;
        EditText mActualNumberProduct;

        public MyViewHolder(View itemView, MyClickListener listener) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.product_name);
            description = (TextView) itemView.findViewById(R.id.product_description);
            plus = (Button) itemView.findViewById(R.id.plus_product);
            mProductImage = (ImageView) itemView.findViewById(R.id.product_image);


            this.listener = listener;
            plus.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.plus_product:
                    listener.AddItem(this.getLayoutPosition());
                    check="1";

                    break;


            }
        }

        public interface MyClickListener {
            void AddItem(int p);
            void DelItem(int p);


        }
    }


}