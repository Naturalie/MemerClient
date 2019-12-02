package com.example.memer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import adapter.RecyclerAdapter;
import controllers.GetTenMemes;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences prefs;
    private SearchView searchView;
    private RecyclerView recyclerView;
    private int page_number = 0;
    private RecyclerAdapter adapter;
    private LinearLayoutManager layout;
    private TextView errorText;
    private ConstraintLayout recyclerConstraintLayout;
    private int pastVisibleItems, visibleItemsCount, totalItemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setView();
        startPaging();
        setOnScrollListener();

    }

    private void setOnScrollListener(){
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled( RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                visibleItemsCount = layout.getChildCount();
                totalItemCount =  layout.getItemCount();
                pastVisibleItems = layout.findFirstVisibleItemPosition();

                if (pastVisibleItems + visibleItemsCount >= totalItemCount - 1) {
                    page_number++;
                    performPagination();

                    recyclerView.post(new Runnable() {
                        public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    });
                }

            }
        });
    }

    private void setView(){
        errorText = findViewById(R.id.errorView2);
        recyclerConstraintLayout = findViewById(R.id.recyclerLinearLayout);
        recyclerView = findViewById(R.id.recyclerViewLayout);
        layout = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layout);
        prefs = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        searchView = findViewById(R.id.search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Intent intent = new Intent(MainActivity.this, SearchedActivity.class);
                intent.putExtra("memeTitle", s);
                finish();
                startActivity(intent);
                overridePendingTransition(0, 0);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    private void startPaging(){
        GetTenMemes gtm = new GetTenMemes(prefs, page_number);
        if(gtm.succeed() == 0){
            errorText.setVisibility(View.INVISIBLE);
            recyclerConstraintLayout.setVisibility(View.VISIBLE);
            adapter = new RecyclerAdapter(gtm.getMemeList(), MainActivity.this);
            recyclerView.setAdapter(adapter);
        }else if(gtm.succeed() == 2){
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            finish();
            startActivity(intent);
            Toast.makeText(this, "Token is no longer valid!", Toast.LENGTH_SHORT).show();
            overridePendingTransition(0, 0);
        }else{
            errorText.setText("Server Error occured!");
            errorText.setVisibility(View.VISIBLE);
        }
    }

    private void performPagination(){
        GetTenMemes gtm = new GetTenMemes(prefs, page_number);
        if(gtm.succeed() == 0){
            errorText.setVisibility(View.INVISIBLE);
           adapter.addMemes(gtm.getMemeList());
        }else if(gtm.succeed() == 2){
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            finish();
            startActivity(intent);
            Toast.makeText(this, "Token is no longer valid!", Toast.LENGTH_SHORT).show();
            overridePendingTransition(0, 0);
        }
        else{
            page_number = 0;
            recyclerConstraintLayout.setVisibility(View.INVISIBLE);
            errorText.setText("Server error occured!");
            errorText.setVisibility(View.VISIBLE);
        }
    }
    public void sendLogOut(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        this.finish();
        startActivity(intent);
        this.overridePendingTransition(0, 0);

    }

    public void goToRandom(View view){
        Intent intent = new Intent(this, RandomActivity.class);
        // intent.putExtra("WT",weatherText.getText().toString());
        this.finish();
        startActivity(intent);
        this.overridePendingTransition(0, 0);

    }




}
