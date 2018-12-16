package com.kmu.mopproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "MyStories";//"MyMovies.db";
    public static final String STORIES_TABLE_NAME = "stories";//"movies";
    public static final String STORIES_COLUMN_ID = "id";
    public static final String STORIES_COLUMN_TITLE = "title";//"name";
    public static final String STORIES_COLUMN_DATE = "date";//"director";
    public static final String STORIES_COLUMN_CATEGORY = "category";//"year";
    public static final String STORIES_COLUMN_MEDIA = "media_src";//"nation";
    public static final String STORIES_COLUMN_MAIN = "main";//"rating";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table stories " +
                        "(id integer primary key,title text, date text, category text, media_src text, main text)" //modified
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS stories");
        onCreate(db);
    }

    public boolean insertStory(String title, String date, String category, String media_src, String main) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("title", title);
        contentValues.put("date", date);
        contentValues.put("category", category);
        contentValues.put("media_src", media_src);
        contentValues.put("main", main);

        db.insert("stories", null, contentValues);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from stories where id=" + id + "", null);
        return res;
    }

    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, STORIES_TABLE_NAME);
        return numRows;
    }

    public boolean updateStory(Integer id, String title, String date, String category, String media_src, String main) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("date", date);
        contentValues.put("category", category);
        contentValues.put("media_src", media_src);
        contentValues.put("main", main);
        db.update("stories", contentValues, "id = ? ", new String[]{Integer.toString(id)});
        return true;
    }

    public Integer deleteStory(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("stories",
                "id = ? ",
                new String[]{Integer.toString(id)});
    }

    public ArrayList getAllStories() {
        ArrayList array_list = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from stories", null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            array_list.add(res.getString(res.getColumnIndex(STORIES_COLUMN_ID))+" "+
                    res.getString(res.getColumnIndex(STORIES_COLUMN_TITLE)));
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList getSpecialStories(){

        ArrayList array_list = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from stories where category='special_day'", null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            array_list.add(res.getString(res.getColumnIndex(STORIES_COLUMN_ID))+" "+
                    res.getString(res.getColumnIndex(STORIES_COLUMN_TITLE)));
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList getFoodsStories(){

        ArrayList array_list = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from stories where category='foods'", null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            array_list.add(res.getString(res.getColumnIndex(STORIES_COLUMN_ID))+" "+
                    res.getString(res.getColumnIndex(STORIES_COLUMN_TITLE)));
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList getTravelStories(){

        ArrayList array_list = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from stories where category='travel'", null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            array_list.add(res.getString(res.getColumnIndex(STORIES_COLUMN_ID))+" "+
                    res.getString(res.getColumnIndex(STORIES_COLUMN_TITLE)));
            res.moveToNext();
        }
        return array_list;
    }
}