package com.example.a2020project;


import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {
    public static final int sub = 1001;
    public ImageButton loginBtn;
    public TextView nameText;
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private FragmentSearch fragmentSearch = new FragmentSearch();
    private FragmentCategory fragmentCategory = new FragmentCategory();
    private FragmentHome fragmentHome = new FragmentHome();
    private FragmentSetting fragmentSetting = new FragmentSetting();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragmentHome).commitAllowingStateLoss();

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new ItemSelectedListener());
        bottomNavigationView.setSelectedItemId(R.id.home);
    }

    class ItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener{
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem){
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            switch (menuItem.getItemId())
            {
                case R.id.home:
                    transaction.replace(R.id.frameLayout, fragmentHome).commitAllowingStateLoss();
                    break;

                case R.id.search:
                    transaction.replace(R.id.frameLayout, fragmentSearch).commitAllowingStateLoss();
                    break;

                case R.id.category:
                    transaction.replace(R.id.frameLayout, fragmentCategory).commitAllowingStateLoss();
                    break;

                case R.id.setting:
                    transaction.replace(R.id.frameLayout, fragmentSetting).commitAllowingStateLoss();
                    break;
            }
            return true;
        }

    }


//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        Intent intent = getIntent();
//        String Nick_name = intent.getStringExtra("NICKNAME");
//
//        loginBtn = (ImageButton)findViewById(R.id.login);
//        nameText =(TextView) findViewById(R.id.namebox);
//        loginBtn = (ImageButton) findViewById(R.id.login);
//        Log.d("nickname", Nick_name + "확인되었습니다");
//        if(Nick_name != null){
//            loginBtn.setImageResource(R.drawable.logout);
//            nameText.setText(intent.getStringExtra("NICKNAME")+"님 반갑습니다. ");
//        }
//        else {
//            loginBtn.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View v) {
//                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//
//                    startActivityForResult(intent, sub);
//                }
//            });
//        }
//    }


}
