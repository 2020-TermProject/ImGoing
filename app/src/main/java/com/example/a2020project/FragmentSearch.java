package com.example.a2020project;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cursoradapter.widget.SimpleCursorAdapter;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.sqlite.SQLiteDatabase;

import com.example.a2020project.Recycler.SearchHistoryAdapter;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Dictionary;

public class FragmentSearch extends Fragment {
    EditText searchText;
    ImageButton searchButton;

    public DBHelper helper;
    public SQLiteDatabase db;
    public Cursor c;
    public SimpleCursorAdapter adapter;
    public RecyclerView list;
    public RecyclerView.LayoutManager mLayoutManager;
    public ArrayList<String> mArray;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
       // return inflater.inflate(R.layout.fragment_search, container, false);
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        helper = new DBHelper(getActivity(), "searchdata.db", null, 1);
        db = helper.getReadableDatabase();
        helper.onCreate(db);

        //카카오아이디 받기
        String Nick_name = getActivity().getIntent().getStringExtra("NICKNAME");
        String User_ID = getActivity().getIntent().getStringExtra("USER_ID");


        searchText = v.findViewById(R.id.editSearch);
        searchButton = v.findViewById(R.id.search_button);

        searchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String serachName = searchText.getText().toString();
                //검색 창에 아무 단어도 안 넣었을 시
                if(searchText.getText().toString().length()==0){
                    Toast.makeText(getActivity(), "검색어를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                }
                else{
                    //검색 한 로그를 검색창 밑에 띄어주기 위한 intent
                    ContentValues values=new ContentValues();
                    values.put("name", searchText.getText().toString());
                    Intent intent = getActivity().getIntent();
                    intent.putExtra("SEARCH", searchText.getText());
                    db.insert("searchdata", null, values);
                    Toast.makeText(getActivity(), searchText.getText() + "로 검색합니다.", Toast.LENGTH_SHORT).show();

                    //검색 결과 보여주는 리사이클러 뷰로 넘어가는
                    Intent reusltintent = new Intent(getActivity(),SearchResultActivity.class);
                    reusltintent.putExtra("SEARCH", serachName);
                    reusltintent.putExtra("NICKNAME", Nick_name);
                    reusltintent.putExtra("USER_ID", User_ID);
                    startActivity(reusltintent);
                }
            }
        });

        // Send the data from search to database
        //Bring back the result and show it
        //_id DESC는 검색어 입력 순서 역순으로 출력
        c = db.query("searchdata",null, null, null, null, null, "_id DESC");

        adapter = null;
        adapter = new SimpleCursorAdapter(getActivity(),
                android.R.layout.simple_list_item_1, c,
                new String[]{"name", "latitude", "longitude"},
                new int[]{android.R.id.text1}, 0);
        list = v.findViewById(R.id.recycler_view);
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        SearchHistoryAdapter Adapter = new SearchHistoryAdapter(getActivity(),adapter);
        list.setAdapter(Adapter);
        /*list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });*/


        return v;

    }
    public static final int sub = 1001;

}
