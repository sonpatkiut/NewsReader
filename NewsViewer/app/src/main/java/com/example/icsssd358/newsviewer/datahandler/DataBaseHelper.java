package com.example.icsssd358.newsviewer.datahandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.icsssd358.newsviewer.model.NewsHeadlineResponse;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static DataBaseHelper databaseHelper = null;

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "news.db";

    public static final String TABLE = "Headlines";

    public static final String ID = "Id";

    public static final String HEADLINE = "headline";

    public static final String IMAGEPATH = "imagePath";

    public static final String NEWSPATH = "newspath";

    public DataBaseHelper(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    public static DataBaseHelper getInstance(Context context, SQLiteDatabase.CursorFactory factory) {
        if (databaseHelper == null)
            databaseHelper = new DataBaseHelper(context, factory);

        return databaseHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLE + "(" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                HEADLINE + " VARCHAR, " +
                NEWSPATH + " VARCHAR, " +
                IMAGEPATH + " VARCHAR);";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(sqLiteDatabase);
    }

    public void insertDataBase(List<NewsHeadlineResponse> list){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        for(NewsHeadlineResponse news : list){
            ContentValues contentValues = new ContentValues();
            contentValues.put(HEADLINE, news.getTitle());
            contentValues.put(NEWSPATH, news.getUrl());
            contentValues.put(IMAGEPATH, news.getUrlToImage());

            long rowInserted = sqLiteDatabase.insert(TABLE, null, contentValues);
        }
        sqLiteDatabase.close();
    }

    public List<NewsHeadlineResponse> getHeadLinesOffline(){
        List<NewsHeadlineResponse> list = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE;

        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if(cursor!=null){
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                NewsHeadlineResponse news = new NewsHeadlineResponse();
                news.setTitle(cursor.getString(cursor.getColumnIndex(HEADLINE)));
                news.setUrl(cursor.getString(cursor.getColumnIndex(NEWSPATH)));
                news.setUrlToImage(cursor.getString(cursor.getColumnIndex(IMAGEPATH)));
                list.add(news);
                cursor.moveToNext();
            }
        }
        return list;
    }

    public void deleteAll(){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete(TABLE, null, null);
    }
}
