package exceptions;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class RegisterException {
    private ArrayList<String> errorList;
   public RegisterException(String error) {
       errorList = new ArrayList<>();
       try {
           JSONArray ja = new JSONArray(error);
           for(int i = 0; i < ja.length(); i++){
               errorList.add(ja.get(i).toString());
           }
       } catch (JSONException e) {
           e.printStackTrace();
       }
   }

   public int getError(){
       if(errorList.isEmpty()) return 0;
       for (String r : errorList) {
           return Integer.parseInt(r);
       }
       return 9;
   }
}
