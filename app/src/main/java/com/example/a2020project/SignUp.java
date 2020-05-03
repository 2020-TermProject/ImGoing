package com.example.a2020project;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class SignUp extends AsyncTask<String, Void, String> {
    protected String doInBackground(String... strings){
        StringBuilder result = new StringBuilder();
        HttpURLConnection urlConnection;
        URL url = null;
        JSONObject js = new JSONObject();
        String name = strings[1];
        String email = strings[2];
        String kid = strings[3];
        String owneruser = strings[4];
        String restaurantName = strings[5];
        String category = strings[6];
        String restaurantLatitude = strings[7];
        String restaurantLongitude = strings[8];
        try {
            url = new URL(strings[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");

            js.put("email", email);
            js.put("userName", name);
            js.put("kid", kid);
            js.put("owneruser", owneruser);
            js.put("retaurantName", restaurantName);
            js.put("category", category);
            js.put("restaurantLatitude", restaurantLatitude);
            js.put("retaurantLongitude", restaurantLongitude);

            String message = js.toString();
            String data = URLEncoder.encode("postData", "UTF-8") + "=" + URLEncoder.encode(message, "UTF-8");

            OutputStreamWriter outr = new OutputStreamWriter(urlConnection.getOutputStream());
            outr.write(data);
            outr.flush();

        }catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}
