package com.example.pawel.myapp.Adapter;

import android.accessibilityservice.GestureDescription;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pawel.myapp.Model.DataProduct;
import com.example.pawel.myapp.R;
import com.example.pawel.myapp.SessionManager;
import com.example.pawel.myapp.User.CartActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    public ArrayList<DataProduct> dataCartArrayList;
    public Integer actualNumber, newNumber;


    public CartAdapter(ArrayList<DataProduct> dataCartArrayList) {
        this.dataCartArrayList = dataCartArrayList;
    }


    @Override
    public CartAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_product_cart, parent, false);

        final CartAdapter.MyViewHolder holder = new CartAdapter.MyViewHolder(view, new MyViewHolder.MyClickListener() {

            @Override
            public void DelProduct(int p) {
                String idProduct = dataCartArrayList.get(p).getId();
                dataCartArrayList.remove(p);

                notifyItemRemoved(p);

                CartActivity.deleteProduct(idProduct, p);



            }

            @Override
            public void DelList() {
//                dataCartArrayList.clear();
//                notifyItemRangeRemoved(0, getItemCount());
            }

            @Override
            public void AddItem(int p) {
                String id = dataCartArrayList.get(p).getId();
                CartActivity.updateCart(id, "1", "0");
            }

            @Override
            public void DelItem(int p) {
                String b = dataCartArrayList.get(p).getId();

                CartActivity.updateCart(b, "2", "0");

            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(final CartAdapter.MyViewHolder holder, final int position) {
        holder.name.setText(dataCartArrayList.get(position).getName());
//        holder.quantity.setText(dataCartArrayList.get(position).getQuantity());
        holder.mQuantityPlusMinus.setText(dataCartArrayList.get(position).getQuantity());
        holder.mCartDesc.setText(dataCartArrayList.get(position).getDescription());
        Picasso.get().load(dataCartArrayList.get(position).getImgUrl()).into(holder.mProductCartImage);
//        if (getItemCount() > 0) {

            holder.mActualNumberProduct.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {

                    if (getItemCount() > 0) {
                        if (!hasFocus) {
                            actualNumber = Integer.parseInt((dataCartArrayList.get(position).getQuantity()));

                            newNumber = Integer.parseInt(holder.mActualNumberProduct.getText().toString());
                            if (actualNumber != newNumber) {
                                if (newNumber > 0) {
                                    String quantity = Integer.toString(newNumber);

                                    CartActivity.updateCart(dataCartArrayList.get(position).getId(), "3", quantity);
                                }

                                else
                                    holder.mActualNumberProduct.setText(Integer.toString(actualNumber));


                            }

                        }
                    }


                }
            });
        }


//    }

    @Override
    public int getItemCount() {


        return dataCartArrayList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        MyClickListener listener;
        SessionManager sessionManager;
        TextView name, mCartDesc;
        ImageView deleteIcon, plus, minus, mProductCartImage;
        Button btnNewOrder;
        EditText mQuantityPlusMinus, mActualNumberProduct;

        public MyViewHolder(View itemView, MyClickListener listener) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.product_cart_name);
            mCartDesc = (TextView) itemView.findViewById(R.id.cart_desc);
            deleteIcon = (ImageView) itemView.findViewById(R.id.deleteProductIcon);
            btnNewOrder = (Button) itemView.findViewById(R.id.btn_order);
            mQuantityPlusMinus = (EditText) itemView.findViewById(R.id.cart_quantity_plus_minus);
            plus = (ImageView) itemView.findViewById(R.id.btn_plus_product_cart);
            minus = (ImageView) itemView.findViewById(R.id.btn_minus_product_cart);
            mProductCartImage = (ImageView) itemView.findViewById(R.id.product_cart_image);
            this.listener = listener;
            deleteIcon.setOnClickListener(this);
            minus.setOnClickListener(this);
            plus.setOnClickListener(this);
            mActualNumberProduct = (EditText) itemView.findViewById(R.id.cart_quantity_plus_minus);

            sessionManager = new SessionManager(itemView.getContext());

        }

        @Override
        public void onClick(View view) {
            int actualNumber = Integer.parseInt(mActualNumberProduct.getText().toString().trim());
            switch (view.getId()) {
                case R.id.deleteProductIcon:
                    listener.DelProduct(this.getLayoutPosition());


                    break;
                case R.id.btn_order:
                    listener.DelList();

                    break;
                case R.id.btn_plus_product_cart:
                    listener.AddItem(this.getLayoutPosition());
                    mActualNumberProduct.setText(Integer.toString(actualNumber + 1));
                    break;
                case R.id.btn_minus_product_cart:
                    listener.DelItem(this.getLayoutPosition());
                    if (actualNumber > 1) {
                        mActualNumberProduct.setText(Integer.toString(actualNumber - 1));

                    }
                    if (actualNumber == 1) {
                        listener.DelProduct(this.getLayoutPosition());
                    }
                    break;


            }

        }

        public interface MyClickListener {
            void DelProduct(int p);

            void DelList();

            void AddItem(int p);

            void DelItem(int p);

        }

    }
}
