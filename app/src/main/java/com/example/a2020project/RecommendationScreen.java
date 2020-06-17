package com.example.a2020project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.a2020project.Recycler.CategoryAdapter;
import com.example.a2020project.Recycler.CategoryRow;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RecommendationScreen extends AppCompatActivity {
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    TextView setCategoryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation_screen);


        mRecyclerView = findViewById(R.id.recommendedRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        setCategoryName = findViewById(R.id.userNameID);


        String userKID = "sadasp";//(kimMunSeok)
        setCategoryName.setText(userKID + " 내가 간다");

        RecommedationSystemMachineLearning recommedationSystemMachineLearning = new RecommedationSystemMachineLearning();// Class to handle connection between python and android studio
        recommedationSystemMachineLearning.initiallizePython(this); //We need to initiallize it
        JSONObject recommendedResults = recommedationSystemMachineLearning.runModel(userKID); //The recommended results are in the form of JSON


        ArrayList<CategoryRow> categoryInfoArrayList = new ArrayList<>(); // To store all the results



        try {
            JSONObject restaurantName = (JSONObject) recommendedResults.get("restaurantName");
            JSONObject ownerName = (JSONObject) recommendedResults.get("ownerName");
            JSONObject category = (JSONObject) recommendedResults.get("category");
            JSONObject restaurantLongitude = (JSONObject) recommendedResults.get("restaurantLongitude");
            JSONObject restaurantLatitude = (JSONObject) recommendedResults.get("restaurantLatitude");
            JSONObject reservedSeat = (JSONObject) recommendedResults.get("reservedSeat");
            JSONObject availableSeat = (JSONObject) recommendedResults.get("availableSeat");

            JSONArray keys = restaurantName.names();

            //Log.d("Python Test:", temp.toString() );

            int i = 0;
            while(i < keys.length()) {
                String rName = (String) restaurantName.getString(keys.get(i).toString());
                String oName = (String) ownerName.getString(keys.get(i).toString());
                String cat = (String) category.getString(keys.get(i).toString());
                String restLongitude = (String) restaurantLongitude.getString(keys.get(i).toString());
                String restLatitude = (String) restaurantLatitude.getString(keys.get(i).toString());
                String rSeat = (String) reservedSeat.getString(keys.get(i).toString());
                String aSeat = (String) availableSeat.getString(keys.get(i).toString());
                i++;
                Log.e("Python recommendation","i: " + i + " " +rName + " " + " " + cat + " " + restLongitude + " " + restLatitude);
                categoryInfoArrayList.add(new CategoryRow(rName,oName,cat,restLongitude,restLatitude,rSeat,aSeat));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        CategoryAdapter myAdapter = new CategoryAdapter(this, categoryInfoArrayList);
        mRecyclerView.setAdapter(myAdapter);


    }



}
