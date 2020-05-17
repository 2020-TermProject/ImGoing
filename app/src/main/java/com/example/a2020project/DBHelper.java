package com.example.a2020project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }
    public void onCreate(SQLiteDatabase db){
        String sql = "create table if not exists searchdata("
                +"_id integer primary key autoincrement, "
                +"name text, "
                +"latitude integer, "
                +"longitude integer);";
        db.execSQL(sql);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        String sql = "drop table if exists searchdata";
        db.execSQL(sql);

        onCreate(db);
    }
}
