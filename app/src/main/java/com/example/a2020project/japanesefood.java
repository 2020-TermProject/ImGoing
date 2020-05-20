package com.example.a2020project;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
public class japanesefood extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.japanesefoodlayout);

        try {
            //서버로 한국 음식 카테고리 정보 보내기.
            CategoryJson loginTask = new CategoryJson();
            String msg = String.valueOf(loginTask.execute("http://khprince.com/restaurantApp/chategorysearch.php", "japanesefood"));
        }catch (Exception e){
            e.printStackTrace();
            Log.e("tag","fail to send category name to server");
        }
    }
}
