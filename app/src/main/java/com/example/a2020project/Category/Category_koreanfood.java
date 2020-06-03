package com.example.a2020project.Category;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a2020project.CategoryJson;
import com.example.a2020project.R;
import com.example.a2020project.Recycler.CategoryAdapter;
import com.example.a2020project.Recycler.CategoryRow;
import com.example.a2020project.Recycler.MyAdapter;
import com.example.a2020project.Recycler.SearchResult;

import org.json.JSONObject;

import java.util.ArrayList;

public class Category_koreanfood extends AppCompatActivity {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.koreanfoodlayout);

        mRecyclerView = findViewById(R.id.recycler_category);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

//        Intent secondIntent = getIntent();
//        String message = secondIntent.getStringExtra("SEARCH");

        ArrayList<CategoryRow> categoryInfoArrayList = new ArrayList<>();
        try {
            //서버로 한국 음식 카테고리 정보 보내기.
            CategoryJson loginTask = new CategoryJson();
            ArrayList<JSONObject> resultInJson = loginTask.execute("http://khprince.com/restaurantApp/categorySearch.php", "koreanfood").get();

            int i = 0;
            while(i < resultInJson.size()){
                String restaurantName = (String)resultInJson.get(i).get("restaurantName");
                String ownerName = (String)resultInJson.get(i).get("ownerName");
                String category = (String)resultInJson.get(i).get("category");
                String restaurantLongitude = (String)resultInJson.get(i).get("restaurantLongitude");
                String restaurantLatitude = (String)resultInJson.get(i).get("restaurantLatitude");
                String reservedSeat = (String)resultInJson.get(i).get("reservedSeat");
                String availableSeat = (String)resultInJson.get(i).get("availableSeat");
                categoryInfoArrayList.add(new CategoryRow(restaurantName,ownerName,category,restaurantLongitude,restaurantLatitude,reservedSeat,availableSeat));
                i++;
                Log.e("category",restaurantName + " " + ownerName + " " + category + " " + restaurantLongitude + " " + restaurantLatitude + " " + reservedSeat + " " + availableSeat);
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.e("tag","fail to send category name to server");
        }
        CategoryAdapter myAdapter = new CategoryAdapter(categoryInfoArrayList);
        mRecyclerView.setAdapter(myAdapter);
    }
}
