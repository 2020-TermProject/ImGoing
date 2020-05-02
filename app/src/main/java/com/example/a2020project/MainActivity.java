package com.example.a2020project;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.net.Uri;
import android.database.sqlite.SQLiteDatabase;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    public static final int sub = 1001;
    public ImageButton loginBtn;
    public TextView nameText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String Nick_name = intent.getStringExtra("NICKNAME");

        loginBtn = (ImageButton)findViewById(R.id.login);
        nameText =(TextView) findViewById(R.id.namebox);
        loginBtn = (ImageButton) findViewById(R.id.login);
        Log.d("nickname", Nick_name + "확인되었습니다");
        if(Nick_name != null){
            loginBtn.setImageResource(R.drawable.logout);
            nameText.setText(intent.getStringExtra("NICKNAME")+"님 반갑습니다. ");
        }
        else {
            loginBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);

                    startActivityForResult(intent, sub);
                }
            });
        }
    }


}
