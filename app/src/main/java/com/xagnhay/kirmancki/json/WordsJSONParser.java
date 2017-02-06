package com.xagnhay.kirmancki.json;

import android.util.Log;
import com.google.gson.Gson;

import com.xagnhay.kirmancki.model.Words;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hidir on 20.01.2017.
 */

public class WordsJSONParser {

    private String TAG = "KIRMANCKI WORDS: " + WordsJSONParser.class.getSimpleName() ;
    List<Words> wordsList = null;

    public List<Words> parseWJSON(String jStr) {

        if (jStr != null) {
            try {
                //Log.e(TAG, "jsonStr : " + jStr);
                JSONObject root = new JSONObject(jStr);

                JSONObject response = root.getJSONObject("response");

                JSONObject language = response.getJSONObject("Language");

                JSONArray wordarray = language.getJSONArray("Words");

                wordsList = new ArrayList<>();

                Gson gson = new Gson();
                for (int i = 0; i < wordarray.length(); i++) {
                    JSONObject c = wordarray.getJSONObject(i);

                    Words words = gson.fromJson(c.toString(), Words.class);
                    wordsList.add(words);
                }
            } catch (final JSONException e) {
                //Log.e(TAG, "Json parsing error: " + e.getMessage());
            }
        }

        return wordsList;
    }
}
