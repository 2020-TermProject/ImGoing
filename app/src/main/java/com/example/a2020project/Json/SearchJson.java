package com.example.a2020project.Json;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class SearchJson extends AsyncTask<String, Void,ArrayList<JSONObject>> {
    protected ArrayList<JSONObject> doInBackground(String... strings){
        ArrayList<JSONObject> jsonList = new ArrayList<JSONObject>();
        HttpURLConnection urlConnection;
        URL url = null;
        JSONObject js = new JSONObject();
        String search = strings[1];
        Log.d("search - JSonCheck", "검색한 이름은 "+strings[1]);

        try {
            Log.d("try check","진입성공");
            url = new URL(strings[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            Log.d("try check","Post");

            js.put("searchName", search);
            String message = js.toString();
            String data = URLEncoder.encode("postData", "UTF-8") + "=" + URLEncoder.encode(message, "UTF-8");
            Log.e("jsonData",data);
            OutputStreamWriter outr = new OutputStreamWriter(urlConnection.getOutputStream());
            outr.write(data);
            outr.flush();

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            InputStreamReader isr = new InputStreamReader(in);
            BufferedReader reader = new BufferedReader(isr);

            String line = null;
            while((line = reader.readLine()) != null) {
                jsonList.add(new JSONObject(line));
                Log.d("try check","while");
            }

            Log.d("JSonCheck2", "이름은"+strings[1]);
            reader.close();

        }catch (Exception e) {
            e.printStackTrace();
            Log.d("try check", "error");
        }

        return jsonList;
    }
}
