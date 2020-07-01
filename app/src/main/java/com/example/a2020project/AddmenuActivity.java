package com.example.a2020project;

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
import android.widget.EditText;
import android.widget.ImageView;

import com.example.a2020project.Json.AddmenuJson;
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
    Button addBtn;
    Button returnBtn;
    EditText menuEdit;
    public static final int sub = 1001;
    //public Button addButton;
    //public Button backButton;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addmenu);

        /* 이미지 첨부
         */
        imageView = findViewById(R.id.getimage);
        picBtn = findViewById(R.id.picbutton);
        takepicBtn = findViewById(R.id.takepicture);
        addBtn = findViewById(R.id.goto_addmenu);
        returnBtn = findViewById(R.id.back);
        menuEdit = findViewById(R.id.menu);

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

        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String menuname = menuEdit.getText().toString();
                AddmenuJson addmenu = new AddmenuJson();
                addmenu.execute("http://khprince.com/restaurantApp/addMenu.php", getIntent().getStringExtra("ownerName"), getIntent().getStringExtra("restaurantName"), menuname);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        super.onActivityResult(requestCode, resultCode, data);
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
        } else if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                try {
                    // 선택한 이미지에서 비트맵 생성
                    Bundle bundle = data.getExtras();
                    Bitmap bitmap = (Bitmap) bundle.get("data");
                    imageView.setImageBitmap(bitmap);
                    Log.e("Capture", "Capture ok: ");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
