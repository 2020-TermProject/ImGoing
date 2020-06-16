package com.example.a2020project;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a2020project.EnrollActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentSetting extends Fragment {
    Button loginBtn;
    Button enrollBtn;
    Button manageBtn;
    //TextView nameText;
    //Context mContext;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        //return inflater.inflate(R.layout.fragment_setting, container, false);
        View v = inflater.inflate(R.layout.fragment_setting, container, false);
        Intent intent = ((Activity) getActivity()).getIntent();
        String Nick_name = intent.getStringExtra("NICKNAME");

        //loginBtn = (ImageButton)v.findViewById(R.id.login);
        loginBtn = v.findViewById(R.id.login);
        enrollBtn = v.findViewById(R.id.setting_Enroll);
        manageBtn = v.findViewById(R.id.setting_Manage);
        //nameText =(TextView)v.findViewById(R.id.namebox);
        //loginBtn = (ImageButton)v.findViewById(R.id.login);
        Log.d("nickname", Nick_name + "확인되었습니다");
        if(Nick_name != null){
            //로그인 버튼 지금 imageButton이 아닌 그냥 버튼으로 만들어놔서 주석 처리 해놨습니다
            //loginBtn.setImageResource(R.drawable.logout);
            //nameText.setText(intent.getStringExtra("NICKNAME")+"님 반갑습니다. ");
            Log.d("check", intent.getStringExtra("NICKNAME")+"  "+intent.getStringExtra("E-MAIL")+"  "+intent.getStringExtra("USER_ID"));
            loginBtn.setText("로그아웃");
            enrollBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), EnrollActivity.class);
                    startActivityForResult(intent, sub);
                }
            });
            manageBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), StoremanagementActivity.class);
                    startActivityForResult(intent, sub);
                }
            });
        }
        else {
            loginBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), Login.class);
                    startActivityForResult(intent, sub);
                }
            });
            enrollBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Toast myToast = Toast.makeText(getActivity(),"로그인 후 이용해 주세요.", Toast.LENGTH_SHORT);
                    myToast.show();
                    //임시로 로그인 안해도 들어갈수 있게 만들어 놨습니다.
                    Intent intent = new Intent(getActivity(), EnrollActivity.class);
                    startActivityForResult(intent, sub);
                }
            });
            manageBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Toast myToast = Toast.makeText(getActivity(),"로그인 후 이용해 주세요.", Toast.LENGTH_SHORT);
                    myToast.show();
                    //임시로 로그인 안해도 들어갈수 있게 만들어 놨습니다.
                    Intent intent = new Intent(getActivity(), StoremanagementActivity.class);
                    startActivityForResult(intent, sub);
                }
            });
        }
        return v;
    }

    public static final int sub = 1001;

}
