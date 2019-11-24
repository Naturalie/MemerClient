package connection.handlers;
import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.MediaType;
import entities.User;

public class LoginConnection {
    private String tokenString;
    private boolean success;
    public LoginConnection(User user){
        success = false;
        setSuccess(user);
    }
    private boolean doRequest(User user){
        OkHttpClient client = new OkHttpClient();
        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        String url = "http://192.168.1.41:8080/login";
        RequestBody body = RequestBody.create(MEDIA_TYPE, user.getJSON().toString());
        Request request = new Request.Builder()
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
            String tokenThis = null;
            try {
                tokenThis = response.body().string();
                if(tokenThis.length() < 1){
                    return false;
                }
                else{
                    tokenString = tokenThis;
                    return true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    public boolean getSuccess(){
        return this.success;
    }

    private void setSuccess(User user){
        this.success = doRequest(user);
    }
    public String getTokenString(){
        return this.tokenString;
    }


}
