package com.example.a2020project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.a2020project.Recycler.SearchResult;

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

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        ArrayList<SearchResult> foodInfoArrayList = new ArrayList<>();
//        foodInfoArrayList.add(new SearchResult(R.drawable.strawberry, "5,000원"));
//        foodInfoArrayList.add(new SearchResult(R.drawable.bread, "4,600원"));
//        foodInfoArrayList.add(new SearchResult(R.drawable.noodle, "4,000원"));
//        MyAdapter myAdapter = new MyAdapter(foodInfoArrayList);
//        mRecyclerView.setAdapter(myAdapter);
    }
}
