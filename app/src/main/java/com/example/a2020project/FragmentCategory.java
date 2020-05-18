package com.example.a2020project;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentCategory extends Fragment {
    Button koreanfood;
    Button chinesefood;
    Button japanesefood;
    Button yangsikfood;
    Button bunsikfood;
    Button cafefood;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

        View v = inflater.inflate(R.layout.fragment_category, container, false);
        Intent intent = ((Activity) getActivity()).getIntent();
        koreanfood = (Button)v.findViewById(R.id.koreanfood);
        chinesefood = (Button)v.findViewById(R.id.chinesefood);
        japanesefood = (Button)v.findViewById(R.id.japanesefood);
        yangsikfood = (Button)v.findViewById(R.id.yangsikfood);
        bunsikfood = (Button)v.findViewById(R.id.bunsikfood);
        cafefood = (Button)v.findViewById(R.id.cafefood);

        koreanfood.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), koreanfood.class);
                startActivityForResult(intent, sub);
            }
        });
        chinesefood.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), chinesefood.class);
                startActivityForResult(intent, sub);
            }
        });
        japanesefood.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), japanesefood.class);
                startActivityForResult(intent, sub);
            }
        });
        yangsikfood.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), yangsikfood.class);
                startActivityForResult(intent, sub);
            }
        });
        bunsikfood.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), bunsikfood.class);
                startActivityForResult(intent, sub);
            }
        });
        cafefood.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), cafefood.class);
                startActivityForResult(intent, sub);
            }
        });

        return v;
    }
    public static final int sub = 1001;
};
