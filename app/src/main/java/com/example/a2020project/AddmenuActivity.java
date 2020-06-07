package com.example.a2020project.Recycler;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.a2020project.MainActivity;
import com.example.a2020project.R;

import java.io.InputStream;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class AddmenuActivity extends AppCompatActivity {
    ImageView imageView;
    Button picBtn;
    Button takepicBtn;
    public static final int sub = 1001;
    //public Button addButton;
    //public Button backButton;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addmenu);

        /* 이미지 첨부
         */
        imageView = (ImageView)findViewById(R.id.getimage);
        picBtn = (Button)findViewById(R.id.picbutton);
        takepicBtn = (Button)findViewById(R.id.takepicture);

        picBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,1);
            }
        });

        takepicBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                int permissionCheck = ContextCompat.checkSelfPermission(AddmenuActivity.this,Manifest.permission.CAMERA);
                if(permissionCheck == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(AddmenuActivity.this,new String[]{Manifest.permission.CAMERA},0);
                } else {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent,2);
                }


            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == 1) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                try {
                    // 선택한 이미지에서 비트맵 생성
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();
                    // 이미지 표시
                    imageView.setImageBitmap(img);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        else if(requestCode == 2) {
            if (resultCode == RESULT_OK) {
                try {
                    // 선택한 이미지에서 비트맵 생성
                    Bundle bundle = data.getExtras();
                    Bitmap bitmap = (Bitmap)bundle.get("data");
                    imageView.setImageBitmap(bitmap);
                    Log.e("Capture", "Capture ok: ");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
