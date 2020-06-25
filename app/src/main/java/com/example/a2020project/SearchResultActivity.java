package com.example.a2020project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.a2020project.Json.SearchJson;
import com.example.a2020project.Recycler.MyAdapter;
import com.example.a2020project.Recycler.SearchResult;

import org.json.JSONObject;

import java.util.ArrayList;

public class SearchResultActivity extends AppCompatActivity {
    //사용 결과를 보여주는 엑티비티 입니다. 리사이클러 뷰로 보여줍니다.
    //FragmentSearch의 intent로 이 엑티비티가 호출되며 검색 결과를 받습니다.
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        String Nick_name = getIntent().getStringExtra("NICKNAME");
        String User_ID = getIntent().getStringExtra("USER_ID");

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        Intent secondIntent = getIntent();
        String message = secondIntent.getStringExtra("SEARCH");
        try {
            Log.e("result", message);
        }catch (NullPointerException e){
            Log.e("result", "null값 넘어옴");
        }

        ArrayList<SearchResult> searchInfoArrayList = new ArrayList<>();
//        foodInfoArrayList.add(new SearchResult(R.drawable.strawberry, "5,000원"));
//        foodInfoArrayList.add(new SearchResult(R.drawable.bread, "4,600원"));
//        foodInfoArrayList.add(new SearchResult(R.drawable.noodle, "4,000원"));
//        MyAdapter myAdapter = new MyAdapter(foodInfoArrayList);
//        mRecyclerView.setAdapter(myAdapter);
        try {
            //로그인 서버로 검색어 보내기
            SearchJson loginTask = new SearchJson();
            ArrayList<JSONObject> resultInJson = loginTask.execute("http://khprince.com/restaurantApp/restaurantSearch.php",message).get();

            int i = 0;
            while(i < resultInJson.size()){
                String restaurantName = (String)resultInJson.get(i).get("restaurantName");
                String ownerName = (String)resultInJson.get(i).get("ownerName");
                String category = (String)resultInJson.get(i).get("category");
                String restaurantLongitude = (String)resultInJson.get(i).get("restaurantLongitude");
                String restaurantLatitude = (String)resultInJson.get(i).get("restaurantLatitude");
                String reservedSeat = (String)resultInJson.get(i).get("reservedSeat");
                String availableSeat = (String)resultInJson.get(i).get("availableSeat");
                searchInfoArrayList.add(new SearchResult(restaurantName,ownerName,category,restaurantLongitude,restaurantLatitude,reservedSeat,availableSeat));
                i++;
                //하나씩 뽑아서 여기에 나옴
                Log.e("search",restaurantName + " " + ownerName + " " + category + " " + restaurantLongitude + " " + restaurantLatitude + " " + reservedSeat + " " + availableSeat);

            }
        }catch (Exception e){
            e.printStackTrace();
            Log.e("tag","검색 단어 서버로 보내기 실패");
        }
        MyAdapter myAdapter = new MyAdapter(this, searchInfoArrayList);
        mRecyclerView.setAdapter(myAdapter);
    }
}
