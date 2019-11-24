package entities;

import org.json.JSONException;
import org.json.JSONObject;

public class Score {
    private final String imageName;
    private final String token;
    private final JSONObject json;
    public Score(String name, String token){
        this.imageName = name;
        this.token = token;
        json = new JSONObject();
        try {
            json.put("name", this.imageName);
            json.put("token", this.token);
        } catch(JSONException e){
            e.printStackTrace();
        }
    }

   public JSONObject getScoreJSON(){
        return json;
   }
}
