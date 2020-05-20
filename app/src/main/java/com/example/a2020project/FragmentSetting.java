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
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentSetting extends Fragment {
    ImageButton loginBtn;
    //TextView nameText;
    //Context mContext;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        //return inflater.inflate(R.layout.fragment_setting, container, false);
        View v = inflater.inflate(R.layout.fragment_setting, container, false);
        Intent intent = ((Activity) getActivity()).getIntent();
        String Nick_name = intent.getStringExtra("NICKNAME");

        loginBtn = (ImageButton)v.findViewById(R.id.login);
        //nameText =(TextView)v.findViewById(R.id.namebox);
        //loginBtn = (ImageButton)v.findViewById(R.id.login);
        Log.d("nickname", Nick_name + "확인되었습니다");
        if(Nick_name != null){
            loginBtn.setImageResource(R.drawable.logout);
            //nameText.setText(intent.getStringExtra("NICKNAME")+"님 반갑습니다. ");
            Log.d("check", intent.getStringExtra("NICKNAME")+"  "+intent.getStringExtra("E-MAIL")+"  "+intent.getStringExtra("USER_ID"));
        }
        else {
            loginBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), Login.class);

                    startActivityForResult(intent, sub);
                }
            });
        }
        return v;
    }

    public static final int sub = 1001;

}
