package com.example.a2020project;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a2020project.AddmenuActivity;
import com.example.a2020project.R;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class EnrollActivity extends Activity {

    public static final int sub = 1001;
    public Button addButton;
    public Button convertTest;
    private Context context;
    public MainActivity activity;


    public void onAttach(Activity activity){
        this.activity = (MainActivity) activity;
    }

    //public Button backButton;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addrestaurant);
        context = getApplicationContext();

        addButton = (Button) findViewById(R.id.goto_addmenu);
        convertTest = findViewById(R.id.test_addresss);
        onclick();


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
    }
    void onclick(){
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddmenuActivity.class);
                startActivityForResult(intent, sub);
            }
        });
        convertTest.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"hi",Toast.LENGTH_SHORT).show();
                try {

                    ConvertAddress convert = new ConvertAddress("태평로1가35",getApplicationContext());
                    double lon = convert.getlon();
                    double lat = convert.getlat();
                    Toast.makeText(getApplicationContext(),"태평로1가35의 위치 - \n위도: " + lon + "\n경도: " + lat,Toast.LENGTH_LONG).show();
                    Log.d("point is"," lon: " + lat + " lon: " + lon);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
