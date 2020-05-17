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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentSearch extends Fragment {
    EditText searchText;
    ImageButton searchButton;
    MainActivity activity;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
       // return inflater.inflate(R.layout.fragment_search, container, false);
        View v = inflater.inflate(R.layout.fragment_search, container, false);

        searchText = (EditText)v.findViewById(R.id.editSearch) ;
        searchButton = (ImageButton)v.findViewById(R.id.search_button);

        searchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(searchText.getText().toString().length()==0){
                    Toast.makeText(getActivity(), "검색어를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = ((Activity) getActivity()).getIntent();
                    intent.putExtra("SEARCH", searchText.getText());
                    Toast.makeText(getActivity(), searchText.getText() + "로 검색합니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Send the data from search to database
        //Bring back the result and show it
        return v;

    }
    public static final int sub = 1001;

}
