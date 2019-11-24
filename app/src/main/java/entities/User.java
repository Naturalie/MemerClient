package entities;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
    private final String login;
    private final String password;
    private final JSONObject json;

    public User (String login, String password){
        this.login = login;
        this.password = password;
        this.json = new JSONObject();

        try {
                json.put("login", this.login);
                json.put("password", this.password);
        } catch(JSONException e){
                // TODO Auto-generated catch block
                e.printStackTrace();
        }
    }

    public JSONObject getJSON(){
        return this.json;
    }

}

