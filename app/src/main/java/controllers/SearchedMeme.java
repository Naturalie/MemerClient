package controllers;

import android.content.Intent;
import android.content.SharedPreferences;

import java.io.IOException;

import entities.Meme;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchedMeme {
    private final SharedPreferences prefs;
    private String memeTitle, memeScore, memeName, intentExtra;
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

    public SearchedMeme(SharedPreferences prefs, String intentExtra){
        this.prefs = prefs;
        this.intentExtra = intentExtra;
        isSuccess = doMagic();

    }

    private boolean doMagic(){
        try{
            OkHttpClient client = new OkHttpClient();
            String url = "http://192.168.1.41:8080/getPictureByTitle?title=" + intentExtra;
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
