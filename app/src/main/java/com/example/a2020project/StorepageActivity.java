package com.example.a2020project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class StorepageActivity extends AppCompatActivity {
    Button reservationBtn;
    Button reviewBtn;
    TextView storeName;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storepage);

        reservationBtn = findViewById(R.id.storepage_Reserve);
        reservationBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(getIntent().getStringExtra("NICKNAME")!=null){
                    Intent intent = new Intent(getApplicationContext(), ReservationActivity.class);
                    intent.putExtra("restaurantName",getIntent().getStringExtra("restaurantName"));
                    intent.putExtra("NICKNAME", getIntent().getStringExtra("NICKNAME"));
                    intent.putExtra("USER_ID",getIntent().getStringExtra("USER_ID"));
                    startActivity(intent);
                }
                else{
                    Toast myToast = Toast.makeText(StorepageActivity.this,"로그인 후 이용해 주세요.", Toast.LENGTH_SHORT);
                    myToast.show();
                    //임시로 로그인 전에도 사용가능하게
                    Intent intent = new Intent(getApplicationContext(), ReservationActivity.class);
                    intent.putExtra("restaurantName",getIntent().getStringExtra("restaurantName"));
                    intent.putExtra("NICKNAME", getIntent().getStringExtra("NICKNAME"));
                    intent.putExtra("USER_ID",getIntent().getStringExtra("USER_ID"));
                    startActivity(intent);
                }

            }
        });
        reviewBtn = findViewById(R.id.storepage_Review);
        reviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ReviewActivity.class);
                intent.putExtra("NICKNAME", getIntent().getStringExtra("NICKNAME"));
                intent.putExtra("USER_ID",getIntent().getStringExtra("USER_ID"));
                startActivity(intent);
            }
        });

        storeName = findViewById(R.id.storepage_StoreName);
        storeName.setText(getIntent().getStringExtra("restaurantName"));
    }
}
