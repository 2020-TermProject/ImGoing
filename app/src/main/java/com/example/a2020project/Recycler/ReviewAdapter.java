package com.example.a2020project.Recycler;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a2020project.R;

import androidx.annotation.NonNull;
import androidx.cursoradapter.widget.SimpleCursorAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class ReviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView ID;
        TextView comment;
        ImageView point;

        MyViewHolder(View view){
            super(view);
            ID = view.findViewById(R.id.recycler_user_id);
            comment = view.findViewById(R.id.recycler_review);
            point = view.findViewById(R.id.point);

        }
    }

    private SimpleCursorAdapter SearchArrayList;
    public ReviewAdapter(SimpleCursorAdapter SearchArrayList){
        this.SearchArrayList = SearchArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_review_row, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


}
