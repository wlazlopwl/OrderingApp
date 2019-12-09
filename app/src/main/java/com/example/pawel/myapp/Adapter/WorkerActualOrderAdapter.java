package com.example.pawel.myapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pawel.myapp.Model.DataOrderParentList;
import com.example.pawel.myapp.Model.DataProduct;
import com.example.pawel.myapp.R;
import com.example.pawel.myapp.Worker.WorkerEditTab1Fragment;

import java.util.ArrayList;

public class WorkerActualOrderAdapter extends RecyclerView.Adapter<WorkerActualOrderAdapter.MyViewHolder> {


    private ArrayList<DataOrderParentList> ActualOrderArrayList;

    private Context mContext;



    public WorkerActualOrderAdapter(ArrayList<DataOrderParentList> ActualOrderArrayList) {
        this.ActualOrderArrayList = ActualOrderArrayList;

    }

    @Override
    public WorkerActualOrderAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_worker_actual_order, viewGroup,false);
        WorkerActualOrderAdapter.MyViewHolder holder= new WorkerActualOrderAdapter.MyViewHolder(itemView, new MyViewHolder.MyClickListener(){


            @Override
            public void changeSimpleStatus(int p) {
                String idOrder = ActualOrderArrayList.get(p).getId();
                WorkerEditTab1Fragment.updateStatus(idOrder);
                ActualOrderArrayList.remove(p);
                notifyItemRemoved(p);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(WorkerActualOrderAdapter.MyViewHolder holder, int i) {
    holder.name.setText(ActualOrderArrayList.get(i).getName());
    holder.date_order.setText(ActualOrderArrayList.get(i).getDate());



//ArrayList<DataProduct> ChildOrderList = ActualOrderArrayList.get(i).getDataOrderParentList();
    DataOrderParentList dataOrderParentList = ActualOrderArrayList.get(i);
    ArrayList<DataProduct> dataOrderChildList = dataOrderParentList.getDataProductChildList();
    WorkerChildOrderAdapter childAdapter = new WorkerChildOrderAdapter(mContext, dataOrderChildList);
        holder.childRV.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL,false));
        holder.childRV.setHasFixedSize(true);
        holder.childRV.setVisibility(View.VISIBLE);
        holder.childRV.setClickable(true);
        holder.childRV.setAdapter(childAdapter);
    }

    @Override
    public int getItemCount() {
        return ActualOrderArrayList.size();
    }

    public static class MyViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener {
        MyClickListener listener;
        TextView name, date_order;
        RecyclerView childRV;
        ImageView mChangeOneStatus;
        public MyViewHolder( View itemView, MyClickListener listener) {
            super(itemView);
            name= (TextView) itemView.findViewById(R.id.worker_parent_rv_orderUser_textview);
            date_order= (TextView) itemView.findViewById(R.id.worker_parent_rv_date_textview);
            mChangeOneStatus = (ImageView) itemView.findViewById(R.id.worker_order_change_simple_status);

            childRV= (RecyclerView) itemView.findViewById(R.id.worker_child_order_product_rv);
            this.listener=listener;

            mChangeOneStatus.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.worker_order_change_simple_status:
                    listener.changeSimpleStatus(this.getLayoutPosition());
                    break;
            }

        }

        public interface MyClickListener {

            void changeSimpleStatus(int p);
        }
    }
}
