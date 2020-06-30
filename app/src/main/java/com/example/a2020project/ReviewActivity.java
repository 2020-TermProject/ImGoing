package com.example.a2020project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ReviewActivity extends AppCompatActivity {
    public Button reviewBtn;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        reviewBtn = findViewById(R.id.Review_button);
        reviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getIntent().getStringExtra("NICKNAME")!=null){
                    Intent intent = new Intent(getApplicationContext(), WritereviewActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast myToast = Toast.makeText(ReviewActivity.this,"로그인 후 이용해 주세요.", Toast.LENGTH_SHORT);
                    myToast.show();
                }
            }
        });

    }
}
