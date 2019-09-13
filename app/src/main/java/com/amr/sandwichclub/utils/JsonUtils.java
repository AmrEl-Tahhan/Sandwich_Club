package com.amr.sandwichclub.utils;

import com.amr.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class JsonUtils {


    public static Sandwich parseSandwichJson(String json)  {
        Sandwich mSandwich = new Sandwich();


        try {
            final JSONObject sandwichObject = new JSONObject(json);
            final JSONObject nameObject = sandwichObject.getJSONObject("name");
            mSandwich.setMainName(nameObject.optString("mainName"));
            mSandwich.setAlsoKnownAs(jsonStringArrayToStringList(nameObject.getJSONArray("alsoKnownAs")));

            mSandwich.setPlaceOfOrigin(sandwichObject.optString("placeOfOrigin"));
            mSandwich.setDescription(sandwichObject.optString("description"));
            mSandwich.setImage(sandwichObject.optString("image"));
            mSandwich.setIngredients(jsonStringArrayToStringList(sandwichObject.getJSONArray("ingredients")));
        } catch (JSONException e) {
            e.printStackTrace();
        }

       return mSandwich;
    }
    private static List<String> jsonStringArrayToStringList(JSONArray array) {

        final List<String> list = new ArrayList<>(array.length());

        for (int i = 0; i < array.length(); ++i)
            list.add(array.optString(i));

        return list;
    }
}
