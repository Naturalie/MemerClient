package com.example.memer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;

import connection.handlers.Host;
import controllers.AddScore;
import controllers.GetMeme;
import controllers.SearchedMeme;
import entities.Meme;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SearchedActivity extends AppCompatActivity {
    SharedPreferences prefs;
    ImageView imageView;
    TextView titleText, scoreText;
    SearchView searchView;
    private String memeName;
    private SearchedMeme entity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searched);

        //searchBar code - change activity on search
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
        scoreText =  findViewById(R.id.scoreView);

        if(entity.succeed()){
            doMagic();
        }
        else{
            System.out.println("no nie dziala");
        }
    }


    public void addLike(View view)
    {
        AddScore as = new AddScore(prefs, memeName, SearchedActivity.this);
        if(as.getSucceed()) {
            scoreText.setText(Integer.parseInt(scoreText.getText().toString().split(" ")[0]) + 1 + " points");
        }
    }

    private void doMagic(){
        SearchedActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                titleText.setText(entity.getMemeTitle());
                scoreText.setText(entity.getMemeScore());
                memeName = entity.getMemeName();
                Picasso.get().load("http://"+ Host.IP + ":8080/images/" + entity.getMemeName()).fit().into(imageView);
            }
        });

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
