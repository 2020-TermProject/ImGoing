package com.example.a2020project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class StorepageActivity extends AppCompatActivity {
    Button reservationBtn;
    TextView storeName;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storepage);

        reservationBtn = (Button)findViewById(R.id.storepage_Reserve);
        reservationBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), ReservationActivity.class);
                startActivity(intent);
            }
        });
        storeName = (TextView)findViewById(R.id.storepage_StoreName);
        storeName.setText(getIntent().getStringExtra("restaurantName"));
    }
}
