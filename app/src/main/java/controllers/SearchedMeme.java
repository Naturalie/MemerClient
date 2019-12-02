package controllers;

import android.content.Intent;
import android.content.SharedPreferences;

import java.io.IOException;

import connection.handlers.Host;
import entities.Meme;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchedMeme {
    private final SharedPreferences prefs;
    private String memeTitle, memeScore, memeName, intentExtra;
    private int isSuccess = 3;

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

    private int doMagic(){

            OkHttpClient client = new OkHttpClient();
            String url = "http://"+ Host.IP + ":8080/getPictureByTitle?title=" + intentExtra;
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
            try{
            if(response.isSuccessful()){
                final String myResponse = response.body().string();

                if(response.code() == 200){
                    Meme meme = new Meme(myResponse);
                    memeTitle = meme.getImageTitle();
                    memeScore = meme.getImageScore();
                    memeName = meme.getImageName();
                    return 0;
                }
            }
            if(response.code() == 403){
                return 2;
            }else {
                return 3;
            }
            }catch (NullPointerException e){
                e.printStackTrace();
                return 3;
            }
            catch (Exception e){
                e.printStackTrace();
                return 3;
            }
    }

    public int succeed(){
        return isSuccess;
    }
}
