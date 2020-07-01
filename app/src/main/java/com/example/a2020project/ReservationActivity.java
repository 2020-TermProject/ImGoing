package com.example.a2020project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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