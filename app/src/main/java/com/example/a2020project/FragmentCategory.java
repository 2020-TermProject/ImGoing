package com.example.a2020project;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.a2020project.Category.CategoryClass;

public class FragmentCategory extends Fragment {
    Button koreanfood;
    Button chinesefood;
    Button japanesefood;
    Button yangsikfood;
    Button bunsikfood;
    Button cafefood;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){

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
                Intent intent = new Intent(getActivity(), CategoryClass.class);
                intent.putExtra("Category", "koreanfood");
                intent.putExtra("CategoryName", "한식");
                startActivityForResult(intent, sub);
            }
        });
        chinesefood.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CategoryClass.class);
                intent.putExtra("Category", "chinesefood");
                intent.putExtra("CategoryName", "중식");
                startActivityForResult(intent, sub);
            }
        });
        japanesefood.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CategoryClass.class);
                intent.putExtra("Category", "japanesefood");
                intent.putExtra("CategoryName", "일식");
                startActivityForResult(intent, sub);;
            }
        });
        yangsikfood.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CategoryClass.class);
                intent.putExtra("Category", "yangsikfood");
                intent.putExtra("CategoryName", "양식");
                startActivityForResult(intent, sub);
            }
        });
        bunsikfood.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CategoryClass.class);
                intent.putExtra("Category", "bunsikfood");
                intent.putExtra("CategoryName", "분식");
                startActivityForResult(intent, sub);
            }
        });
        cafefood.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CategoryClass.class);
                intent.putExtra("Category", "cafefood");
                intent.putExtra("CategoryName", "카페");
                startActivityForResult(intent, sub);
            }
        });

        return v;
    }
    public static final int sub = 1001;
};
