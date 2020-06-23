package com.example.a2020project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ReservationActivity extends AppCompatActivity {
    public Button resBtn;
    public TextView resName;
    public Spinner resNum;
    public Spinner resHour;
    public Spinner resMin;
    public EditText resReq;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        resNum = (Spinner)findViewById(R.id.reservation_Spinner_Number);
        resHour = (Spinner)findViewById(R.id.reservation_Spinner_Hour);
        resMin = (Spinner)findViewById(R.id.reservation_Spinner_Minute);
        resBtn = (Button)findViewById(R.id.reservation_Payment);
        resReq = (EditText)findViewById(R.id.reservation_Request);
        resBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
                intent.putExtra("restaurantName",getIntent().getStringExtra("restaurantName"));
                intent.putExtra("number", resNum.getSelectedItem().toString());
                intent.putExtra("hour", resHour.getSelectedItem().toString());
                intent.putExtra("minute", resMin.getSelectedItem().toString());
                intent.putExtra("request", resReq.getText());
                startActivity(intent);
            }
        });
        resName = (TextView)findViewById(R.id.reservation_StoreName);
        resName.setText(getIntent().getStringExtra("restaurantName"));

    }
}