package com.example.android.booklisting;


import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class QueryUtils {

    private QueryUtils() {
    }

    public static String formatListOfAuthors(JSONArray authorsList) throws JSONException {

        String authorsListInString = null;

        if (authorsList.length() == 0) {
            return null;
        }

        for (int i = 0; i < authorsList.length(); i++) {
            if (i == 0) {
                authorsListInString = authorsList.getString(0);
            } else {
                authorsListInString += ", " + authorsList.getString(i);
            }
        }

        return authorsListInString;
    }


    public static List<Book> extractBooks(String responseJson) {

        List<Book> books = new ArrayList<>();

        try {
            JSONObject volumes = new JSONObject(responseJson);
            JSONArray items = volumes.getJSONArray("items");

            if (volumes.getInt("totalItems") == 0) {
                return books;
            }
            JSONArray jsonArray = volumes.getJSONArray("items");

            for (int i = 0; i < items.length(); i++) {
                JSONObject item = items.getJSONObject(i);
                JSONObject volume = item.getJSONObject("volumeInfo");
                String title = volume.getString("title");
                String authors;
                if (volume.has("authors")) {
                    authors = volume.getJSONArray("authors").get(0).toString();
                } else {
                    authors = "Unknown author";
                }
                ;


                Book book = new Book(authors, title);
                books.add(book);
            }
        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the book JSON results", e);
        }
        return books;
    }
}