package connection.handlers;

import java.io.IOException;

import entities.User;
import entities.UserRegister;
import exceptions.RegisterException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterConnection {

    private int success;
    public RegisterConnection(UserRegister userRegister){
        success = 9;
        setSuccess(userRegister);
    }
    private int doRequest(UserRegister userRegister) {
        OkHttpClient client = new OkHttpClient();
        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        String url = "http://" + Host.IP + ":8080/register";
        RequestBody body = RequestBody.create(MEDIA_TYPE, userRegister.getJSON().toString());
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
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        try {
            if (response.isSuccessful()) {
                if (response.code() == 200) {
                    RegisterException re = new RegisterException(response.body().string());
                    return re.getError();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 9;
    }
    public int getSuccess(){
        return this.success;
    }

    private void setSuccess(UserRegister userRegister){
        this.success = doRequest(userRegister);
    }

}
