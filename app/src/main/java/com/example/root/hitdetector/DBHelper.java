package com.example.root.hitdetector;

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "SCORES_DATABASE";
    public static final String SCORES_TABLE_NAME = "scores";
    public static final String SCORES_COLUMN_ID = "id";
    public static final String SCORES_COLUMN_TIME = "time";
    public static final String SCORES_COLUMN_DATE = "date";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table "+SCORES_TABLE_NAME+" " +
                        "("+SCORES_COLUMN_ID+" integer primary key," +
                        " "+SCORES_COLUMN_TIME+" time text, " +
                        " "+SCORES_COLUMN_DATE+" DATETIME DEFAULT CURRENT_TIMESTAMP )"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS "+SCORES_TABLE_NAME+"");
        onCreate(db);
    }

    public boolean insertRecord (String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SCORES_COLUMN_TIME, time);
        db.insert(SCORES_TABLE_NAME, null, contentValues);
        return true;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, SCORES_TABLE_NAME);
        return numRows;
    }
    public void deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+SCORES_TABLE_NAME);
        db.close();
    }
    public String getBestScore(){
        SQLiteDatabase db = this.getReadableDatabase();
        String results = "--";
        Cursor res = db.rawQuery("select MIN("+SCORES_COLUMN_TIME+") as time from "+SCORES_TABLE_NAME+" LIMIT 1",null);
        if(res.getCount()>0){
            res.moveToFirst();
            results = res.getString(res.getColumnIndex(SCORES_COLUMN_TIME));
            if(results == null){
                results = "--";
            }
        }
        return results;
    }
    public String getPreviousScore(){
        SQLiteDatabase db = this.getReadableDatabase();
        String results = "--";
        Cursor res = db.rawQuery("select "+SCORES_COLUMN_TIME+" from "+SCORES_TABLE_NAME+" ORDER BY "+SCORES_COLUMN_DATE+" DESC LIMIT 1",null);
        if(res.getCount()>0){
            res.moveToFirst();
            results = res.getString(res.getColumnIndex(SCORES_COLUMN_TIME));
        }
        return results;
    }

    public ArrayList<String> getAllContacts() {
        ArrayList<String> array_list = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+SCORES_TABLE_NAME+" ORDER BY DATE DESC", null );
        StringBuilder sb = new StringBuilder();
        res.moveToFirst();

        while(!res.isAfterLast()){
            sb.append(res.getString(res.getColumnIndex(SCORES_COLUMN_TIME)) + " ,: " + res.getString(res.getColumnIndex(SCORES_COLUMN_DATE)));
            array_list.add(sb.toString());
            sb.setLength(0);
            res.moveToNext();
        }
        return array_list;
    }
}