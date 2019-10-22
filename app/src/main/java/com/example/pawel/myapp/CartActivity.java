package com.example.pawel.myapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    private static DataProduct data;
    private CartAdapter CartAdapter;
    public static ArrayList<DataProduct> dataCartArrayList ;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);








       


        setupProductCartRecycler();
    }


    public void setupProductCartRecycler(){




        LinearLayoutManager layoutManager= new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        RecyclerView recyclerView=findViewById(R.id.recyclerViewProductCart);
        recyclerView.setLayoutManager( layoutManager);

        recyclerView.setLayoutManager( layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setClickable(true);
        CartAdapter  = new CartAdapter(dataCartArrayList );
        recyclerView.setAdapter(CartAdapter);
    }

    public static void addItem(String p){
        if (dataCartArrayList == null) {
            dataCartArrayList = new ArrayList<>();
        }

        data = new DataProduct();
        data.setName(p);
        dataCartArrayList.add(data);

    }


}
