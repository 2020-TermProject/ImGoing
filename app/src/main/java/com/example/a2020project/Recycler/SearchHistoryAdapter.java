package com.example.a2020project.Recycler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a2020project.DBHelper;
import com.example.a2020project.FragmentSearch;
import com.example.a2020project.R;
import com.example.a2020project.SearchResultActivity;

import java.util.ArrayList;

import androidx.cursoradapter.widget.SimpleCursorAdapter;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

public class SearchHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    public Activity activity = null;
    private Activity context = null;
    public String s;
    public SQLiteDatabase db;
    public DBHelper helper;
    //인텐트로 검색했던 결과를 넘기기 위한 text
    private String searchText;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView history;
        ImageButton delete;

        MyViewHolder(View view){
            super(view);
            delete = view.findViewById(R.id.delete);
            history = view.findViewById(R.id.history);

        }
    }

    private SimpleCursorAdapter SearchArrayList;
    public SearchHistoryAdapter(Activity context, SimpleCursorAdapter SearchArrayList){
        this.context = context;
        this.SearchArrayList = SearchArrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.searchhistorylayout, parent, false);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reusltintent = new Intent(v.getContext(), SearchResultActivity.class);
                reusltintent.putExtra("SEARCH", searchText);
                context.startActivity(reusltintent);
            }
        });
        return new MyViewHolder(v);
    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        MyViewHolder myViewHolder = (MyViewHolder) holder;

        Cursor cursor = (Cursor)SearchArrayList.getItem(position);
        final String ss = cursor.getString(1);
        //검색한 것 stirng에 넣기
        searchText = ss;

        myViewHolder.history.setText(ss);
        myViewHolder.history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //검색기록으로 검색
                Toast.makeText(context, s+"로 검색했습니다.", Toast.LENGTH_SHORT).show();
            }
        });
        myViewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, "검색기록을 삭제했습니다.", Toast.LENGTH_SHORT).show();
                helper = new DBHelper(context, "searchdata.db", null, 1);
                db = helper.getWritableDatabase();
                helper.onCreate(db);
                db.delete("searchdata","name=?", new String[]{ss});

                Intent intent = context.getIntent();
                intent.putExtra("delete", "true");
                Log.d("tag2",intent.getStringExtra("delete"));
                context.finish();
                context.startActivity(intent);


                /*// 다이얼로그 바디
                AlertDialog.Builder alertdialog = new AlertDialog.Builder(context);
                // 다이얼로그 메세지
                alertdialog.setMessage("검색기록을 삭제하시겠습니까?");

                // 확인버튼
                alertdialog.setPositiveButton("확인", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "검색기록을 삭제했습니다.", Toast.LENGTH_SHORT).show();
                        helper = new DBHelper(context, "searchdata.db", null, 1);
                        db = helper.getWritableDatabase();
                        helper.onCreate(db);
                        db.delete("searchdata","name=?", new String[]{s});
                    }
                });

                // 취소버튼
                alertdialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "취소하셨습니다.", Toast.LENGTH_SHORT).show();
                    }
                });*/

            }
        });
    }

    @Override
    public int getItemCount() {
        return SearchArrayList.getCount();
    }
}

