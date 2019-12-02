package com.example.memer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;


import connection.handlers.Host;
import controllers.AddScore;
import controllers.SearchedMeme;

public class SearchedActivity extends AppCompatActivity {
    private SharedPreferences prefs;
    private ImageView imageView;
    private TextView titleText, scoreText, errorText;
    private SearchView searchView;
    private String memeName;
    private ConstraintLayout layout;
    private SearchedMeme entity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searched);

        setView();
        doMagic();


    }


    public void addLike(View view)
    {
        AddScore as = new AddScore(prefs, memeName);
        if(as.getSucceed() == 0) {
            scoreText.setText(Integer.parseInt(scoreText.getText().toString().split(" ")[0]) + 1 + " points");
            Toast.makeText(this, "It's gettin' even hotter ;)", Toast.LENGTH_SHORT).show();
        }else if (as.getSucceed() == 1){
            Toast.makeText(this, "You have already liked that!", Toast.LENGTH_SHORT).show();
        }else if (as.getSucceed() == 2){
            Intent intent = new Intent(SearchedActivity.this, LoginActivity.class);
            finish();
            startActivity(intent);
            Toast.makeText(this, "Token is no longer valid!", Toast.LENGTH_SHORT).show();
            overridePendingTransition(0, 0);
        }else{
            Intent intent = new Intent(SearchedActivity.this, LoginActivity.class);
            finish();
            startActivity(intent);
            Toast.makeText(this, "Server Error occured!", Toast.LENGTH_SHORT).show();
            overridePendingTransition(0, 0);
        }
    }

    private void setView(){
        //searchBar code - change activity on search
        layout = findViewById(R.id.constraintLayout);
        searchView = findViewById(R.id.search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Intent intent = new Intent(SearchedActivity.this, SearchedActivity.class);
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

        prefs = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        entity = new SearchedMeme(prefs, getIntent().getStringExtra("memeTitle"));

        imageView = findViewById(R.id.imageView);
        titleText = findViewById(R.id.titleView);
        scoreText = findViewById(R.id.scoreView);
        errorText = findViewById(R.id.errorView3);
    }

    private void doMagic(){
        if(entity.succeed() == 0) {
            errorText.setVisibility(View.INVISIBLE);
            layout.setVisibility(View.VISIBLE);
            SearchedActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    titleText.setText(entity.getMemeTitle());
                    scoreText.setText(entity.getMemeScore());
                    memeName = entity.getMemeName();
                    Picasso.get().load("http://" + Host.IP + ":8080/images/" + entity.getMemeName()).fit().into(imageView);
                }
            });
        }else if(entity.succeed() == 2){
            Intent intent = new Intent(SearchedActivity.this, LoginActivity.class);
            finish();
            startActivity(intent);
            Toast.makeText(this, "Token is no longer valid!", Toast.LENGTH_SHORT).show();
            overridePendingTransition(0, 0);
        }else{
            errorText.setText("Server Error occured!");
            errorText.setVisibility(View.VISIBLE);
            layout.setVisibility(View.INVISIBLE);
        }

    }


    public void sendLogOut(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        this.finish();
        startActivity(intent);
        this.overridePendingTransition(0, 0);

    }

    public void goToMain(View view){
        Intent intent = new Intent(this, MainActivity.class);
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
}
