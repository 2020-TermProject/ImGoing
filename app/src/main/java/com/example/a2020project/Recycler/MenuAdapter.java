package com.example.a2020project.Recycler;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.a2020project.R;
import com.example.a2020project.StorepageActivity;

import java.util.ArrayList;

//리사이클러 뷰 어뎁터
public class MenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    private Activity context = null;
    String restName;
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mu_name;
        TextView mu_price;


        //row 뷰 하나의 xml을 연결해줌
        MyViewHolder(View view){
            super(view);
            mu_name = view.findViewById(R.id.mu_name);
            mu_price = view.findViewById(R.id.mu_price);
        }
    }

    private ArrayList<MenuRow> searchInfoArrayList;
    public MenuAdapter(Activity context, ArrayList<MenuRow> foodInfoArrayList){
        this.context = context;
        this.searchInfoArrayList = foodInfoArrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_selectmenu_row, parent, false);
        //선택 시 상점 페이지로 넘어가게 만듬
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.mu_name.setText(searchInfoArrayList.get(position).menuName);
        myViewHolder.mu_price.setText(searchInfoArrayList.get(position).price);
    }

    @Override
    public int getItemCount() {
        return searchInfoArrayList.size();
    }
}
