package entities;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class TenMemes {
    private List<Meme> listMemes;

    public TenMemes(String jsonString){
        listMemes = new ArrayList<>();
        try {
            JSONArray ja = new JSONArray(jsonString);
            for(int i = 0; i < ja.length(); i++){
                Meme temp = new Meme(ja.getJSONObject(i).toString());
                this.listMemes.add(temp);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public List<Meme> getListMemes(){
        return this.listMemes;
    }
}