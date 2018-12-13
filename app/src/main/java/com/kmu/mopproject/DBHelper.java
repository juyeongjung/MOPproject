package com.kmu.mopproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "MyMovies.db";
    public static final String MOVIES_TABLE_NAME = "movies";
    public static final String MOVIES_COLUMN_ID = "id";
    public static final String MOVIES_COLUMN_NAME = "name";
    public static final String MOVIES_COLUMN_DIRECTOR = "director";
    public static final String MOVIES_COLUMN_YEAR = "year";
    public static final String MOVIES_COLUMN_NATION = "nation";
    public static final String MOVIES_COLUMN_RATING = "rating";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table movies " +
                        "(id integer primary key,name text, director text, year text, nation text, rating text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS movies");
        onCreate(db);
    }

    public boolean insertMovie(String name, String director, String year, String nation, String rating) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("name", name);
        contentValues.put("director", director);
        contentValues.put("year", year);
        contentValues.put("nation", nation);
        contentValues.put("rating", rating);

        db.insert("movies", null, contentValues);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from movies where id=" + id + "", null);
        return res;
    }

    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, MOVIES_TABLE_NAME);
        return numRows;
    }

    public boolean updateMovie(Integer id, String name, String director, String year, String nation, String rating) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("director", director);
        contentValues.put("year", year);
        contentValues.put("nation", nation);
        contentValues.put("rating", rating);
        db.update("movies", contentValues, "id = ? ", new String[]{Integer.toString(id)});
        return true;
    }

    public Integer deleteMovie(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("movies",
                "id = ? ",
                new String[]{Integer.toString(id)});
    }

    public ArrayList getAllMovies() {
        ArrayList array_list = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from movies", null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            array_list.add(res.getString(res.getColumnIndex(MOVIES_COLUMN_ID))+" "+
                    res.getString(res.getColumnIndex(MOVIES_COLUMN_NAME)));
            res.moveToNext();
        }
        return array_list;
    }

//    public static final String DATABASE_NAME = "MyStory.db";
//    public static final String STORY_TABLE_NAME = "stories";
//    public static final String STORY_COLUMN_ID = "id";
//    public static final String STORY_COLUMN_HEADLINE = "headline";
//    public static final String STORY_COLUMN_DATE = "date";
//    public static final String STORY_COLUMN_CATEGORY = "category";
//    public static final String STORY_COLUMN_MEDIA_SRC = "media_src";
//    public static final String STORY_COLUMN_MAIN = "main";
////    public static final String MOVIES_COLUMN_RATING = "rating";
//
//    public DBHelper(Context context) {
//        super(context, DATABASE_NAME, null, 1);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        db.execSQL(
//                "create table stories " +
//                        "(id integer primary key,headline text, date text, category text, media_src text, main text)" //media_src 어떻게 처리할지 생각해보기
//        );
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS stories");
//        onCreate(db);
//    }
//
//    public boolean insertStory(String headline, String date, String category, String media_src, String main) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//
//        contentValues.put("headline", headline);
//        contentValues.put("date", date);
//        contentValues.put("category", category);
//        contentValues.put("media_src", media_src);
//        contentValues.put("main", main);
//
//        db.insert("stories", null, contentValues);
//        return true;
//    }
//
//    public Cursor getData(int id) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor res = db.rawQuery("select * from stories where id=" + id + "", null);
//        return res;
//    }
//
//    public int numberOfRows() {
//        SQLiteDatabase db = this.getReadableDatabase();
//        int numRows = (int) DatabaseUtils.queryNumEntries(db, STORY_TABLE_NAME);
//        return numRows;
//    }
//
//    public boolean updateStory(Integer id,String headline, String date, String category, String media_src, String main) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("headline", headline);
//        contentValues.put("date", date);
//        contentValues.put("category", category);
//        contentValues.put("media_src", media_src);
//        contentValues.put("main", main);
//        db.update("stories", contentValues, "id = ? ", new String[]{Integer.toString(id)});
//        return true;
//    }
//
//    public Integer deleteStory(Integer id) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        return db.delete("stories",
//                "id = ? ",
//                new String[]{Integer.toString(id)});
//    }
//
//    public ArrayList getAllStories() {
//        ArrayList array_list = new ArrayList();
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor res = db.rawQuery("select * from stories", null);
//        res.moveToFirst();
//        while (res.isAfterLast() == false) {
//            array_list.add(res.getString(res.getColumnIndex(STORY_COLUMN_ID))+" "+
//                    res.getString(res.getColumnIndex(STORY_COLUMN_HEADLINE)));
//            res.moveToNext();
//        }
//        return array_list;
//    }
}