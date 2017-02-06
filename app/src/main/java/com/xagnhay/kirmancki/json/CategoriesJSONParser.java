package com.xagnhay.kirmancki.json;

import android.util.Log;
import com.google.gson.Gson;

import com.xagnhay.kirmancki.model.Category;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hidir on 19.01.2017.
 */

public class CategoriesJSONParser {
    private String TAG = CategoriesJSONParser.class.getSimpleName() + ": KIRMANCKI";
    List<Category> categoryList = null;

    public List<Category> parseJSON(String jsonStr) {

        if (jsonStr != null) {
            try {

                JSONObject root = new JSONObject(jsonStr);

                JSONObject response = root.getJSONObject("response");

                JSONObject langs1 = response.getJSONObject("Language");

                JSONArray catarray = langs1.getJSONArray("Categories");

                categoryList = new ArrayList<>();

                Gson gson = new Gson();
                for (int i = 0; i < catarray.length(); i++) {
                    JSONObject c = catarray.getJSONObject(i);

                    Category category = gson.fromJson(c.toString(), Category.class);
                    categoryList.add(category);
                }
                //return categoryList;

            } catch (final JSONException e) {
                //Log.e(TAG, "Json parsing error: " + e.getMessage());
            }
        }

        return categoryList;
    }


}
