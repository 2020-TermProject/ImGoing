package com.example.a2020project.Recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.a2020project.R;
import java.util.ArrayList;
//리사이클러 뷰 어뎁터
public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView srRestaurantName;
        TextView srCategory;
        TextView srAvailableSeat;
        TextView srReservedSeat;

        //row 뷰 하나의 xml을 연결해줌
        MyViewHolder(View view){
            super(view);
            srRestaurantName = view.findViewById(R.id.sr_restaurantName);
            srCategory = view.findViewById(R.id.sr_category);
            srAvailableSeat = view.findViewById(R.id.sr_availableSeat);
            srReservedSeat = view.findViewById(R.id.sr_reservedSeat);
        }
    }

    private ArrayList<SearchResult> searchInfoArrayList;
    public MyAdapter(ArrayList<SearchResult> foodInfoArrayList){
        this.searchInfoArrayList = foodInfoArrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_searchresult_row, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        MyViewHolder myViewHolder = (MyViewHolder) holder;

        myViewHolder.srRestaurantName.setText(searchInfoArrayList.get(position).restaurantName);
        myViewHolder.srCategory.setText(searchInfoArrayList.get(position).category);
        myViewHolder.srAvailableSeat.setText(searchInfoArrayList.get(position).availableSeat);
        myViewHolder.srReservedSeat.setText(searchInfoArrayList.get(position).reservedSeat);
    }

    @Override
    public int getItemCount() {
        return searchInfoArrayList.size();
    }
}
