package com.example.a2020project.Recycler;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.a2020project.R;

import androidx.appcompat.app.AppCompatActivity;

public class EnrollActivity extends AppCompatActivity {

    public static final int sub = 1001;
    public Button addButton;
    //public Button backButton;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addrestaurant);

        addButton =(Button)findViewById(R.id.addmenu);
        addButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                Intent intent = new Intent(getApplicationContext(), AddmenuActivity.class);
                startActivityForResult(intent,sub);

            }
        });
/*
        backButton =(Button)findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                finish();

            }
        });


        UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
            @Override
            public void onCompleteLogout() {
                //로그아웃 성공 후 하고싶은 내용 코딩 ~

            }
        });

    }

 */
}}
