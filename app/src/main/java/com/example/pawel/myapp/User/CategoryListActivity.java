package com.example.pawel.myapp.User;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.pawel.myapp.Adapter.CategoryAdapter;
import com.example.pawel.myapp.R;
import com.example.pawel.myapp.RecyclerViewClickListener;

public class CategoryListActivity extends AppCompatActivity implements RecyclerViewClickListener {


    public static Context context;
    private CategoryAdapter CategoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);
        context = getApplicationContext();

        Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbara);
        toolbar.setTitle("Lista kategorii");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        setupRecycler();


    }

    public void setupRecycler() {


        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = findViewById(R.id.category_list_listview);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setClickable(true);
        CategoryAdapter = new CategoryAdapter(this, MainActivity.categoryModelArrayList);
        recyclerView.setAdapter(CategoryAdapter);


    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onClick(int position) {
        Intent i = new Intent(CategoryListActivity.this, ProductListActivity.class);
        String id = MainActivity.categoryModelArrayList.get(position).getId();
        i.putExtra("position", id);
        startActivity(i);
    }
}
