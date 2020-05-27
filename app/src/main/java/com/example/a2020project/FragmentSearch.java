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
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONObject;

import java.util.ArrayList;

public class FragmentSearch extends Fragment {
    EditText searchText;
    ImageButton searchButton;

    public DBHelper helper;
    public SQLiteDatabase db;
    public Cursor c;
    public SimpleCursorAdapter adapter;
    public ListView list;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
       // return inflater.inflate(R.layout.fragment_search, container, false);
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        helper = new DBHelper(getActivity(), "searchdata.db", null, 1);
        db = helper.getReadableDatabase();
        helper.onCreate(db);

        searchText = (EditText)v.findViewById(R.id.editSearch) ;
        searchButton = (ImageButton)v.findViewById(R.id.search_button);

        searchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //검색 창에 아무 단어도 안 넣었을 시
                if(searchText.getText().toString().length()==0){
                    Toast.makeText(getActivity(), "검색어를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                }
                else{
                    try {
                        //로그인 서버로 검색어 보내기
                        SearchJson loginTask = new SearchJson();
                        ArrayList<JSONObject> resultInJson = loginTask.execute("http://khprince.com/restaurantApp/restaurantSearch.php", searchText.getText().toString()).get();

                        int i = 0;
                        while(i < resultInJson.size()){
                            String restaurantName = (String)resultInJson.get(i).get("restaurantName");
                            String ownerName = (String)resultInJson.get(i).get("ownerName");
                            String category = (String)resultInJson.get(i).get("category");
                            String restaurantLongitude = (String)resultInJson.get(i).get("restaurantLongitude");
                            String restaurantLatitude = (String)resultInJson.get(i).get("restaurantLatitude");
                            String reservedSeat = (String)resultInJson.get(i).get("reservedSeat");
                            String availableSeat = (String)resultInJson.get(i).get("availableSeat");

                            i++;
                            //하나씩 뽑아서 여기에 나옴
                            Log.e("search",restaurantName + " " + ownerName + " " + category + " " + restaurantLongitude + " " + restaurantLatitude + " " + reservedSeat + " " + availableSeat);
                            Toast.makeText(getActivity(),restaurantName + " " + ownerName + " " + category + " " + restaurantLongitude + " " + restaurantLatitude + " " + reservedSeat + " " + availableSeat, Toast.LENGTH_SHORT).show();

                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        Log.e("tag","검색 단어 서버로 보내기 실패");
                    }
                    ContentValues values=new ContentValues();
                    values.put("name", searchText.getText().toString());
                    Intent intent = ((Activity) getActivity()).getIntent();
                    intent.putExtra("SEARCH", searchText.getText());
                    db.insert("searchdata", null, values);
                    Toast.makeText(getActivity(), searchText.getText() + "로 검색합니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Send the data from search to database
        //Bring back the result and show it

        c = db.query("searchdata",null, null, null, null, null, null);

        adapter = null;
        adapter = new SimpleCursorAdapter(getActivity(),
                android.R.layout.simple_list_item_1, c,
                new String[]{"name", "latitude", "longitude"},
                new int[]{android.R.id.text1}, 0);
        list = (ListView)v.findViewById(R.id.listView);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        return v;

    }
    public static final int sub = 1001;

}
