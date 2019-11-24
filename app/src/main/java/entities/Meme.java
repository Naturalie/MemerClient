package entities;

import org.json.JSONException;
import org.json.JSONObject;

public class Meme {
    private  String imageName;
    private  String imageTitle;
    private  int imageScore;

    public String getImageName() {
        return imageName;
    }

    public String getImageTitle() {
        return imageTitle;
    }

    public String getImageScore() {
            return Integer.toString(imageScore) + " points";

    }

    public Meme(String jsonString){
        try {
            JSONObject obj = new JSONObject(jsonString);
            imageName = obj.getString("imageName");
            imageTitle = obj.getString("imageTitle");
            imageScore = obj.getInt("imageScore");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
