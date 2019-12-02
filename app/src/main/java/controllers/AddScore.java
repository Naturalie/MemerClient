package controllers;

import android.app.Activity;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.io.IOException;

import connection.handlers.Host;
import entities.Score;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddScore {
private int succeed = 2;
    public AddScore(SharedPreferences prefs, String memeName){
        try {
            OkHttpClient client = new OkHttpClient();
            MediaType MEDIA_TYPE = MediaType.parse("application/json");
            String url = "http://"+ Host.IP + ":8080/increaseScore";
            Score score = new Score(memeName, prefs.getString("token", ""));

            RequestBody body = RequestBody.create(MEDIA_TYPE, score.getScoreJSON().toString());
            Request request = new Request.Builder()
                    .addHeader("Authorization", "Bearer " + prefs.getString("token", ""))
                    .url(url)
                    .post(body)
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .build();

            Response response = null;
            try {
                response = client.newCall(request).execute();
                if(response.isSuccessful()){
                    String myReponse = response.body().string();
                    if(response.code() == 200){
                        if(myReponse.equals("false")){
                            succeed = 1;
                        }else{
                            succeed = 0;
                        }
                    }else if(response.code() == 403){
                        succeed = 2;
                    }else{
                        succeed = 3;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                succeed = 2;
            }

        }catch (NullPointerException e){
            e.printStackTrace();
            succeed = 2;
        } catch (Exception e){
            e.printStackTrace();
            succeed = 2;
        }
    }

    public int getSucceed(){
        return this.succeed;
    }
}
