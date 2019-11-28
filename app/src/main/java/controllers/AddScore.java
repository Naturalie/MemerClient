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
boolean succeed = false;
    public AddScore(SharedPreferences prefs, String memeName, Activity activity){
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
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(response.isSuccessful()){
                String myReponse = response.body().string();
                System.out.println(response.code());
                if(response.code() == 200){
                    System.out.println(myReponse);
                    if(myReponse.equals("false")){
                        Toast.makeText(activity,"You have already liked that! You silly!",Toast.LENGTH_LONG).show();
                        succeed = false;
                    }else{
                        Toast.makeText(activity,"It's getting even hotter ;)",Toast.LENGTH_LONG).show();
                        succeed = true;
                    }
                }else{
                    succeed = false;
                    Toast.makeText(activity,"Something went wrong!",Toast.LENGTH_LONG).show();
                }

            }
        }catch (NullPointerException e){
            System.out.println("Null");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean getSucceed(){
        return this.succeed;
    }
}
