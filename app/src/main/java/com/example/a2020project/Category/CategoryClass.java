package com.example.a2020project.Category;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a2020project.Json.CategoryJson;
import com.example.a2020project.R;
import com.example.a2020project.Recycler.CategoryAdapter;
import com.example.a2020project.Recycler.CategoryRow;

import org.json.JSONObject;

import java.util.ArrayList;

public class CategoryClass extends AppCompatActivity {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    TextView setCategoryName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categoryfoodlayout);
        //리사이클러 선언
        mRecyclerView = findViewById(R.id.recycler_category);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //인텐드 값 받기
        Intent secondIntent = getIntent();
        String name = secondIntent.getStringExtra("CategoryName");
        String category_name = secondIntent.getStringExtra("Category");

        //카테고리 이름 설정
        setCategoryName = findViewById(R.id.layout_categoryName);
        setCategoryName.setText(name + " 내가 간다");

        ArrayList<CategoryRow> categoryInfoArrayList = new ArrayList<>();
        try {
            //서버로 한국 음식 카테고리 정보 보내기.
            CategoryJson loginTask = new CategoryJson();
            ArrayList<JSONObject> resultInJson = loginTask.execute("http://khprince.com/restaurantApp/categorySearch.php", category_name).get();

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
        CategoryAdapter myAdapter = new CategoryAdapter(this, categoryInfoArrayList);
        mRecyclerView.setAdapter(myAdapter);
    }
}
