package com.example.memer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import connection.handlers.Host;
import entities.Meme;

import java.io.IOException;
import java.util.Random;

import entities.Score;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import controllers.*;

public class RandomActivity extends AppCompatActivity {
    private SharedPreferences prefs;
    private ImageView imageView;
    private TextView titleText, scoreText;
    private SearchView searchView;
    private String memeName;
    private GetMeme entity;
    private ConstraintLayout constraintLayout;
    private View view2, view3, view4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random);

        //searchBar code - change activity on search
        searchView = findViewById(R.id.search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Intent intent = new Intent(RandomActivity.this, SearchedActivity.class);
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
        entity = new GetMeme(prefs);

        view2 = findViewById(R.id.view2);
        view3 = findViewById(R.id.view3);
        view4 = findViewById(R.id.view4);
        constraintLayout = findViewById(R.id.layoutRandomView);
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

    public void addLike(View view){
        AddScore as = new AddScore(prefs, memeName, RandomActivity.this);
        if(as.getSucceed()) {
            scoreText.setText(Integer.parseInt(scoreText.getText().toString().split(" ")[0]) + 1 + " points");
        }
    }

    private void doMagic(){
        RandomActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                titleText.setText(entity.getMemeTitle());
                scoreText.setText(entity.getMemeScore());
                memeName = entity.getMemeName();
                Picasso.get().load("http://" + Host.IP + ":8080/images/" + entity.getMemeName()).fit().into(imageView);
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
    public void refresh(View view){
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

}
