package com.example.a2020project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ReservationActivity extends AppCompatActivity {
    public Button resBtn;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        resBtn = (Button)findViewById(R.id.reservation_Payment);
        resBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
                startActivity(intent);
            }
        });

    }
}