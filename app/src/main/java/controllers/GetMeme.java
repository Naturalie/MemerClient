package controllers;

import android.app.Activity;
import android.content.SharedPreferences;
import android.widget.TextView;

import com.example.memer.R;
import com.example.memer.RandomActivity;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import entities.Meme;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetMeme {
    private final SharedPreferences prefs;
    private String memeTitle, memeScore, memeName;
    private boolean isSuccess = false;

    public String getMemeTitle() {
        return memeTitle;
    }

    public String getMemeScore() {
        return memeScore;
    }

    public String getMemeName() {
        return memeName;
    }

    public GetMeme(SharedPreferences prefs){
        this.prefs = prefs;
        isSuccess = doMagic();
    }

    private boolean doMagic(){
        try{
            OkHttpClient client = new OkHttpClient();
            String url = "http://192.168.1.41:8080/random";
            Request request = new Request.Builder()
                    .addHeader("Authorization","Bearer "+ prefs.getString("token",""))
                    .url(url)
                    .build();
            Response response = null;
            try {
                response = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(response.isSuccessful()){
                final String myResponse = response.body().string();

                if(response.code() == 200){
                    System.out.println(myResponse);
                    Meme meme = new Meme(myResponse);
                    memeTitle = meme.getImageTitle();
                    memeScore = meme.getImageScore();
                    memeName = meme.getImageName();
                    return true;
                }
            }
        }catch (NullPointerException e){
            System.out.println("Null");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean succeed(){
        return isSuccess;
    }
}
