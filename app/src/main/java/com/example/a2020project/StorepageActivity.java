package com.example.a2020project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
                Intent intent = new Intent(getApplicationContext(), ReservationActivity.class);
                intent.putExtra("restaurantName",getIntent().getStringExtra("restaurantName"));
                startActivity(intent);
            }
        });
        reviewBtn = findViewById(R.id.storepage_Review);
        reviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ReviewActivity.class);
                startActivity(intent);
            }
        });

        storeName = findViewById(R.id.storepage_StoreName);
        storeName.setText(getIntent().getStringExtra("restaurantName"));
    }
}
