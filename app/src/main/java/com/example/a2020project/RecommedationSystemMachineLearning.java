package com.example.a2020project;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import org.json.JSONException;
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
import java.util.concurrent.ExecutionException;


public class RecommedationSystemMachineLearning {
    public void initiallizePython(Context context){
        if(!Python.isStarted()){
            Python.start(new AndroidPlatform(context));
        }
    }

    public JSONObject runModel(String userKID){

        UpdateTheDatabase updateTheDatabase = new UpdateTheDatabase();

        try {
            String updateResult = updateTheDatabase.execute("http://khprince.com/restaurantApp/machineModelExecuter.php", userKID).get();
            Python myCode = Python.getInstance();
            PyObject pyObject = myCode.getModule("MachineLearningCode");
            JSONObject jsonObject = new JSONObject(pyObject.callAttr("stringBack").toString());
            return jsonObject;


        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;


    }
}

class UpdateTheDatabase extends AsyncTask<String, Void, String> {
    protected String doInBackground(String... strings){

        HttpURLConnection urlConnection;
        URL url = null;
        JSONObject js = new JSONObject();
        String userKID = strings[1];
        String line = null;

        try {
            Log.d("try check","진입성공");
            url = new URL(strings[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            Log.d("try check","Post");

            js.put("userKID", userKID);
            String message = js.toString();
            String data = URLEncoder.encode("postData", "UTF-8") + "=" + URLEncoder.encode(message, "UTF-8");
            Log.e("Category jsonData",data);
            OutputStreamWriter outr = new OutputStreamWriter(urlConnection.getOutputStream());
            outr.write(data);
            outr.flush();

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            InputStreamReader isr = new InputStreamReader(in);
            BufferedReader reader = new BufferedReader(isr);


            while((line = reader.readLine()) != null) {
                Log.d("try check","while");
            }

            reader.close();

        }catch (Exception e) {
            e.printStackTrace();
            Log.d("try check", "error");
        }

        return line;
    }
}
