package com.example.pawel.myapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pawel.myapp.Model.DataProduct;
import com.example.pawel.myapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ChildOrderAdapter extends RecyclerView.Adapter<ChildOrderAdapter.MyViewHolder> {


    public ArrayList<DataProduct> ChildOrderList;
    private Context mContext;


    public ChildOrderAdapter(Context context, ArrayList<DataProduct> ChildOrderList) {
        this.ChildOrderList = ChildOrderList;
        this.mContext = context;


    }


    @Override
    public ChildOrderAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_order_simple_item, viewGroup, false);


        ChildOrderAdapter.MyViewHolder adapter = new MyViewHolder(itemView);
        return adapter;

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.name.setText((ChildOrderList.get(i).getName()));
        myViewHolder.orderProductDesc.setText(ChildOrderList.get(i).getDescription());
        myViewHolder.orderProductQuantity.setText(ChildOrderList.get(i).getQuantity() + "x");
        Picasso.get().load(ChildOrderList.get(i).getImgUrl()).into(myViewHolder.mProductImage);

    }

    @Override
    public int getItemCount() {
//
        return (ChildOrderList == null) ? 0 : ChildOrderList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, orderProductDesc, orderProductQuantity;
        ImageView mProductImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.product_order_name);
            orderProductDesc = (TextView) itemView.findViewById(R.id.order_product_desc);
            orderProductQuantity = (TextView) itemView.findViewById(R.id.order_product_quantity);
            mProductImage = (ImageView) itemView.findViewById(R.id.user_product_order_image);
        }
    }
}
