package com.example.a2020project;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.InputStream;


public class MainActivity extends AppCompatActivity {
    public static final int sub = 1001;
    public ImageButton loginBtn;
    public TextView nameText;
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private FragmentSearch fragmentSearch = new FragmentSearch();
    private FragmentCategory fragmentCategory = new FragmentCategory();
    private FragmentHome fragmentHome = new FragmentHome();
    private FragmentSetting fragmentSetting = new FragmentSetting();
    private final int MY_PERMISSIONS_REQUEST_CAMERA=1001;

    final String TAG = getClass().getSimpleName();
    ImageView imageView;
    Button cameraBtn;
    final static int TAKE_PICTURE = 1;
    Button button;
    private WebView webView;
    private TextView txt_address;
    private Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String Nick_name = getIntent().getStringExtra("NICKNAME");
        String User_ID = getIntent().getStringExtra("USER_ID");
        /* 이미지 첨부
         */
        imageView = findViewById(R.id.getimage);
        /*button = (Button)findViewById(R.id.picbutton);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent();
                intent.setType("getimage/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,1);
            }
        });*///에러떠서 주석처리




        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragmentHome).commitAllowingStateLoss();

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new ItemSelectedListener());
        bottomNavigationView.setSelectedItemId(R.id.home);

        /*Intent intent = getIntent();

        Log.d("tag","프레그먼트 이동 준비");
        if(getIntent().getStringExtra("delete")=="true"){
            Log.d("tag","프레그먼트 이동 성공");
            transaction.replace(R.id.frameLayout, fragmentSearch).commitAllowingStateLoss();
        }
        else{
            intent.putExtra("delete", "false");
        }
        Log.d("tag",intent.getStringExtra("delete"));*/

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
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(this,"승인이 허가되어 있습니다.",Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(this,"아직 승인받지 않았습니다.",Toast.LENGTH_LONG).show();
                }
                return;
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                try {
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();
                    imageView.setImageBitmap(img);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
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
