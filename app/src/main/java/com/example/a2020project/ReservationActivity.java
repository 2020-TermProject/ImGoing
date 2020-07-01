package com.example.a2020project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a2020project.Json.MenuJson;
import com.example.a2020project.Json.SearchJson;
import com.example.a2020project.Recycler.MenuAdapter;
import com.example.a2020project.Recycler.MenuRow;
import com.example.a2020project.Recycler.MyAdapter;
import com.example.a2020project.Recycler.SearchResult;

import org.json.JSONObject;

import java.util.ArrayList;

public class ReservationActivity extends AppCompatActivity {
    public Button resBtn;
    public TextView resName;
    public Spinner res1Num;
    public Spinner res2Num;
    public Spinner res4Num;
    public Spinner res6Num;
    public Spinner resHour;
    public Spinner resMin;
    public EditText resReq;
    public Button backBtn;

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        res1Num = findViewById(R.id.reservation_Spinner_Oneseat);
        res2Num = findViewById(R.id.reservation_Spinner_Twoseat);
        res4Num = findViewById(R.id.reservation_Spinner_Fourseat);
        res6Num = findViewById(R.id.reservation_Spinner_Sixseat);
        resHour = findViewById(R.id.reservation_Spinner_Hour);
        resMin = findViewById(R.id.reservation_Spinner_Minute);
        resBtn = findViewById(R.id.reservation_Payment);
        resReq = findViewById(R.id.reservation_Request);
        //메뉴 선택 리사이클러 뷰
        mRecyclerView = findViewById(R.id.select_menu);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        ArrayList<MenuRow> menuInfoArrayList = new ArrayList<>();
        try {
            //로그인 서버로 검색어 보내기
            MenuJson loginTask = new MenuJson();
            String resName = getIntent().getStringExtra("restaurantName");
            ArrayList<JSONObject> resultInJson = loginTask.execute("http://khprince.com/restaurantApp/showMenu.php",resName).get();
            int price = 7000;
            int i = 0;
            while(i < resultInJson.size()){
                String restaurantName = (String)resultInJson.get(i).get("restaurantName");
                String ownerName = (String)resultInJson.get(i).get("ownerName");
                String menuItem = (String)resultInJson.get(i).get("menuItem");
                menuInfoArrayList.add(new MenuRow(menuItem,price));
                i++;
                //하나씩 뽑아서 여기에 나옴
                Log.e("search",restaurantName + " " + ownerName + " " + menuItem);

            }
        }catch (Exception e){
            e.printStackTrace();
            Log.e("tag","검색 단어 서버로 보내기 실패");
        }
        MenuAdapter myAdapter = new MenuAdapter(this, menuInfoArrayList);
        mRecyclerView.setAdapter(myAdapter);
//      ----------------------
        resBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getIntent().getStringExtra("NICKNAME")!=null){
                    Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
                    intent.putExtra("restaurantName",getIntent().getStringExtra("restaurantName"));
                    intent.putExtra("number", res2Num.getSelectedItem().toString());
                    intent.putExtra("hour", resHour.getSelectedItem().toString());
                    intent.putExtra("minute", resMin.getSelectedItem().toString());
                    intent.putExtra("request", resReq.getText());
                    intent.putExtra("NICKNAME",getIntent().getStringExtra("NICKNAME"));
                    intent.putExtra("USER_ID",getIntent().getStringExtra("USER_ID"));
                    startActivity(intent);
                }
                else{
                    Toast myToast = Toast.makeText(ReservationActivity.this,"로그인 후 이용해 주세요.", Toast.LENGTH_SHORT);
                    myToast.show();
                    //임시로 로그인 전에도 사용 가능
                    Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
                    intent.putExtra("restaurantName",getIntent().getStringExtra("restaurantName"));
                    intent.putExtra("number", res2Num.getSelectedItem().toString());
                    intent.putExtra("hour", resHour.getSelectedItem().toString());
                    intent.putExtra("minute", resMin.getSelectedItem().toString());
                    intent.putExtra("request", resReq.getText());
                    intent.putExtra("NICKNAME",getIntent().getStringExtra("NICKNAME"));
                    intent.putExtra("USER_ID",getIntent().getStringExtra("USER_ID"));
                    startActivity(intent);
                }

            }
        });
        resName = findViewById(R.id.reservation_StoreName);
        resName.setText(getIntent().getStringExtra("restaurantName"));
        backBtn = findViewById(R.id.back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}