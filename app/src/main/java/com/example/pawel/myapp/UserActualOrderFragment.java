package com.example.pawel.myapp;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class UserActualOrderFragment extends Fragment {
    private ActualOrderAdapter actualAdapter;
    private ChildOrderAdapter ChildOrderAdapter;
    ArrayList<DataOrderParentList> ActualOrderArrayList;
    ArrayList<DataProduct> ChildOrderList;

    public UserActualOrderFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_actual_order, container, false);
        ActualOrderArrayList = new ArrayList<>();
        ChildOrderList = new ArrayList<>();



        DataOrderParentList playerModel = new DataOrderParentList();

        playerModel.setName("Testa");
        ActualOrderArrayList.add(playerModel);



        DataProduct playerModella = new DataProduct();

        playerModella.setName("Testaaaaaa");
        ChildOrderList.add(playerModella);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.parent_actual_order_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        recyclerView.setHasFixedSize(true);
        recyclerView.setClickable(true);
        actualAdapter  = new ActualOrderAdapter( ChildOrderList);
        recyclerView.setAdapter(actualAdapter);

        playerModel.setDataProductList(ActualOrderArrayList);
        playerModella.setDataProductList(ChildOrderList);





        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);




    }
}
