package controllers;

import android.app.Activity;
import android.content.SharedPreferences;
import android.widget.TextView;

import com.example.memer.R;
import com.example.memer.RandomActivity;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import connection.handlers.Host;
import entities.Meme;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetMeme {
    private final SharedPreferences prefs;
    private String memeTitle, memeScore, memeName;
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

    public GetMeme(SharedPreferences prefs){
        this.prefs = prefs;
        isSuccess = doMagic();
    }

    private int doMagic(){
            OkHttpClient client = new OkHttpClient();
            String url = "http://"+ Host.IP + ":8080/random";
            Request request = new Request.Builder()
                    .addHeader("Authorization","Bearer "+ prefs.getString("token",""))
                    .url(url)
                    .build();
            Response response = null;
            try {
                response = client.newCall(request).execute();

            } catch (IOException e) {
                e.printStackTrace();
            }catch (IllegalStateException e) {
                e.printStackTrace();
            }
            try {
                if(response.isSuccessful()) {
                    final String myResponse;
                        myResponse = response.body().string();
                        if (response.code() == 200) {
                            Meme meme = new Meme(myResponse);
                            memeTitle = meme.getImageTitle();
                            memeScore = meme.getImageScore();
                            memeName = meme.getImageName();
                            return 0;
                        }
                } if(response.code() == 403){
                    return 2;
                }else {
                    return 3;
                }
            }catch (IOException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return 3;
    }

    public int isSucess(){
        return isSuccess;
    }
}
