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
public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Activity context = null;
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView ctRestaurantName;
        TextView ctCategory;
        TextView ctAvailableSeat;
        TextView ctReservedSeat;

        //row 뷰 하나의 xml을 연결해줌
        MyViewHolder(View view) {
            super(view);
            ctRestaurantName = view.findViewById(R.id.ct_restaurantName);
            ctCategory = view.findViewById(R.id.ct_category);
            ctAvailableSeat = view.findViewById(R.id.ct_availableSeat);
            ctReservedSeat = view.findViewById(R.id.ct_reservedSeat);
        }
    }

    private ArrayList<CategoryRow> categoryInfoArrayList;

    public CategoryAdapter(Activity context, ArrayList<CategoryRow> categoryInfoArrayList) {
        this.context = context;
        this.categoryInfoArrayList = categoryInfoArrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_category_row, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        MyViewHolder myViewHolder = (MyViewHolder) holder;
        final String rename = categoryInfoArrayList.get(position).restaurantName;

        myViewHolder.ctRestaurantName.setText(categoryInfoArrayList.get(position).restaurantName);
        myViewHolder.ctCategory.setText(categoryInfoArrayList.get(position).category);
        myViewHolder.ctAvailableSeat.setText(categoryInfoArrayList.get(position).availableSeat);
        myViewHolder.ctReservedSeat.setText(categoryInfoArrayList.get(position).reservedSeat);
        myViewHolder.ctRestaurantName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("recycler cate test","hi");
                Intent intent = new Intent(context, StorepageActivity.class);
                intent.putExtra("restaurantName", rename);
                context.startActivity(intent);
            }
        });
        myViewHolder.ctCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("recycler cate test","hi");
                Intent intent = new Intent(context, StorepageActivity.class);
                intent.putExtra("restaurantName", rename);
                context.startActivity(intent);
            }
        });
        myViewHolder.ctAvailableSeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("recycler cate test","hi");
                Intent intent = new Intent(context, StorepageActivity.class);
                intent.putExtra("restaurantName", rename);
                context.startActivity(intent);
            }
        });
        myViewHolder.ctReservedSeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("recycler cate test","hi");
                Intent intent = new Intent(context, StorepageActivity.class);
                intent.putExtra("restaurantName", rename);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return categoryInfoArrayList.size();
    }
}
