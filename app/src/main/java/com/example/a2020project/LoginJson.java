package com.example.a2020project;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class LoginJson extends AsyncTask<String, Void, String> {
    protected String doInBackground(String... strings){
        StringBuilder result = new StringBuilder();
        HttpURLConnection urlConnection;
        URL url = null;

        String name = strings[1];
        String email = strings[2];
        String kid = strings[3];
        Log.d("JSonCheck", "이름은"+strings[1]);

        try {
            url = new URL(strings[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");


            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            InputStreamReader isr = new InputStreamReader(in);
            BufferedReader reader = new BufferedReader(isr);

            String line = null;
            while((line = reader.readLine()) != null) {
                result.append(line);
            }

            Log.d("JSonCheck2", "이름은"+strings[1]);
            reader.close();

        }catch (Exception e) {
            e.printStackTrace();
        }

        return result.toString();
    }
}
