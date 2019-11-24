package com.example.memer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;

public class MainActivity extends AppCompatActivity {
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //searchBar code - change activity on search
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

    public void sendLogOut(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        // intent.putExtra("WT",weatherText.getText().toString());
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

    public void goToSearch(View view){
        Intent intent = new Intent(this, SearchedActivity.class);
        // intent.putExtra("WT",weatherText.getText().toString());
        this.finish();
        startActivity(intent);
        this.overridePendingTransition(0, 0);

    }



}
