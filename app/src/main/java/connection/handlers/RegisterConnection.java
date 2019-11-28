package connection.handlers;

import java.io.IOException;

import entities.User;
import entities.UserRegister;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterConnection {

    private boolean success;
    public RegisterConnection(UserRegister userRegister){
        success = false;
        setSuccess(userRegister);
    }
    private boolean doRequest(UserRegister userRegister){
        OkHttpClient client = new OkHttpClient();
        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        String url = "http://"+ Host.IP + ":8080/register";
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
            return false;
        }
        if(response.isSuccessful()){
            try {
                if(response.code() == 200) {
                    if(response.body().string().equals("[]")){
                        return true;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }
    public boolean getSuccess(){
        return this.success;
    }

    private void setSuccess(UserRegister userRegister){
        this.success = doRequest(userRegister);
    }

}
