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

public class ReservationJson extends AsyncTask<String, Void, String> {
    protected String doInBackground(String... strings){
        StringBuilder result = new StringBuilder();
        HttpURLConnection urlConnection;
        URL url = null;
        JSONObject js = new JSONObject();
        String kid = strings[1];
        String userName = strings[2];
        String restaurantName = strings[3];
        String ownerName = strings[4];
        String reservationTime = strings[5];
        String reservedSeats = strings[6];
        String review = strings[7];
        Log.d("JSonCheck", "이름은 "+strings[1]);

        try {
            Log.d("try check","진입성공");
            url = new URL(strings[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            Log.d("try check","Post");

            js.put("kid", kid);
            js.put("userName", userName);
            js.put("restaurantName", restaurantName);
            js.put("ownerName", ownerName);
            js.put("reservationTime", reservationTime); //
            js.put("reservedSeats", reservedSeats);
            js.put("review", review);

            String message = js.toString();
            String data = URLEncoder.encode("postData", "UTF-8") + "=" + URLEncoder.encode(message, "UTF-8");
            Log.e("jsonData",data);
            OutputStreamWriter outr = new OutputStreamWriter(urlConnection.getOutputStream());
            outr.write(data);
            outr.flush();

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            Log.d("try check","Buffer1");
            InputStreamReader isr = new InputStreamReader(in);
            Log.d("try check","Buffer2");
            BufferedReader reader = new BufferedReader(isr);
            Log.d("try check","Buffer3");

            String line = null;
            while((line = reader.readLine()) != null) {
                result.append(line);
                Log.d("try check","while");
            }

            Log.d("JSonCheck2", "이름은"+strings[1]);
            reader.close();

        }catch (Exception e) {
            e.printStackTrace();
            Log.d("try check", "error");
        }

        return result.toString();
    }
}