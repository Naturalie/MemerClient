package entities;

import org.json.JSONException;
import org.json.JSONObject;

public class UserRegister {
    private final String login;
    private final String password;
    private final String email;
    private final JSONObject json;

    public UserRegister(String login, String password, String email){
        this.login = login;
        this.password = password;
        this.email = email;
        this.json = new JSONObject();

        try {
        json.put("login", this.login);
        json.put("password", this.password);
        json.put("email", this.email);
    } catch(
    JSONException e){
        e.printStackTrace();
    }
}

    public JSONObject getJSON(){
        return this.json;
    }
}
