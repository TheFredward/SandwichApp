package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.MainActivity;
import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwichRequested = null;
        try {
            //Initialize object
            JSONObject jsonObject = new JSONObject(json);

            //call jsonobject and go into Object known as name
            JSONObject sandwichDetails = jsonObject.getJSONObject("name");

            //Now inside of name, get  proper keys, ie main name
            String mainName = sandwichDetails.optString("mainName");
            //For loop to iterate and populate the also known as details
            JSONArray alsoKnown = sandwichDetails.optJSONArray("alsoKnownAs");
            //Create a List to pass to Sandwich class
            List<String> alsoKnownStringList = new ArrayList<>();
            if (alsoKnown != null) {
                for (int i = 0; i < alsoKnown.length(); i++) {
                    alsoKnownStringList.add(alsoKnown.optString(i));
                }
            }

            //Populate the rest with the proper jsonKey
            String origin = jsonObject.optString("placeOfOrigin");
            String description = jsonObject.optString("description");
            String image = jsonObject.optString("image");
            //Ingredients is an array so use a for loop
            JSONArray ingredientsArray = jsonObject.getJSONArray("ingredients");
            List<String> ingredientStringList = new ArrayList<>();
            if (ingredientsArray != null) {
                for (int i = 0; i < ingredientsArray.length(); i++) {
                    ingredientStringList.add(ingredientsArray.optString(i));
                }
            }
            //Fill the method sandwich with the requested data by user.
            sandwichRequested = new Sandwich(mainName, alsoKnownStringList, origin, description, image, ingredientStringList);
        } catch (final JSONException e) {
            Log.e(MainActivity.class.getSimpleName(), "JSON parsing error: " + e.getMessage());
        }
        return sandwichRequested;
    }
}
