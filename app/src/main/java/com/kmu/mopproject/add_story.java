package com.kmu.mopproject;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

public class add_story extends AppCompatActivity {

    public static Context mContext;

    private DBHelper mydb;
    TextView title;
    TextView date;
    TextView category;
    TextView media_src;
    TextView main;
    CheckBox option1;
    CheckBox option2;
    CheckBox option3;
    int id = 0;

    long mNow;
    Date mDate;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_story);

        mNow=System.currentTimeMillis();
        mDate=new Date(mNow);
        mFormat.format(mDate);

        title = (TextView) findViewById(R.id.editTextTitle);
        date = (TextView) findViewById(R.id.editTextDate);
        date.setText(getTime());
        category = (TextView) findViewById(R.id.editTextCategory);
        media_src = (TextView) findViewById(R.id.editTextMedia);
        main = (TextView) findViewById(R.id.editTextMain);

        mydb = new DBHelper(this);

        option1 = (CheckBox) findViewById(R.id.checkBoxCate1);
        option2 = (CheckBox) findViewById(R.id.checkBoxCate2);
        option3 = (CheckBox)findViewById(R.id.checkBoxCate3);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int Value = extras.getInt("id");
            if (Value > 0) {
                Cursor rs = mydb.getData(Value);
                id = Value;
                rs.moveToFirst();
                String n = rs.getString(rs.getColumnIndex(DBHelper.STORIES_COLUMN_TITLE));
                String d = rs.getString(rs.getColumnIndex(DBHelper.STORIES_COLUMN_DATE));
                String y = rs.getString(rs.getColumnIndex(DBHelper.STORIES_COLUMN_CATEGORY));
                String na = rs.getString(rs.getColumnIndex(DBHelper.STORIES_COLUMN_MEDIA));
                String r = rs.getString(rs.getColumnIndex(DBHelper.STORIES_COLUMN_MAIN));
                if (!rs.isClosed()) {
                    rs.close();
                }
                Button b = (Button) findViewById(R.id.button1);
                b.setVisibility(View.INVISIBLE);

                title.setText((CharSequence) n);
                date.setText((CharSequence) d);
                category.setText((CharSequence) y);
                media_src.setText((CharSequence) na);
                main.setText((CharSequence) r);
            }
        }
        mContext=this;
    }

    private String getTime(){
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        return mFormat.format(mDate);
    }

    public void insert(View view) {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            int Value = extras.getInt("id");
            if (Value > 0) {
                if(option1.isChecked()){
                    category.setText((CharSequence) "special_day");
                }
                else if(option2.isChecked()){
                    category.setText((CharSequence)"foods");
                }
                else if(option3.isChecked()){
                    category.setText((CharSequence)"travel");
                }
                else{
                    category.setText((CharSequence)"normal_day");
                }

                if (mydb.updateStory(id, title.getText().toString(), date.getText().toString(), category.getText().toString(), media_src.getText().toString(), main.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "수정되었음", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), com.kmu.mopproject.MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "수정되지 않았음", Toast.LENGTH_SHORT).show();
                }
            } else {
                if(option1.isChecked()){
                    category.setText((CharSequence) "special_day");
                }
                else if(option2.isChecked()){
                    category.setText((CharSequence)"foods");
                }
                else if(option3.isChecked()){
                    category.setText((CharSequence)"travel");
                }
                else{
                    category.setText((CharSequence)"normal_day");
                }

                if (mydb.insertStory(title.getText().toString(), date.getText().toString(), category.getText().toString(), media_src.getText().toString(), main.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "추가되었음", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "추가되지 않았음", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        }
    }

    public void delete(View view) {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int Value = extras.getInt("id");
            if (Value > 0) {
                mydb.deleteStory(id);
                Toast.makeText(getApplicationContext(), "삭제되었음", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "삭제되지 않았음", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void edit(View view) {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int value = extras.getInt("id");
            if (value > 0) {
                if(option1.isChecked()){
                    category.setText((CharSequence) "special_day");
                }
                else if(option2.isChecked()){
                    category.setText((CharSequence)"foods");
                }
                else if(option3.isChecked()){
                    category.setText((CharSequence)"travel");
                }
                else{
                    category.setText((CharSequence)"normal_day");
                }
                if (mydb.updateStory(id, title.getText().toString(), date.getText().toString(), category.getText().toString(), media_src.getText().toString(), main.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "수정되었음", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "수정되지 않았음", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

//    public void fastInsert(){
////        Bundle extras = getIntent().getExtras();
//
//        if (mydb.insertStory("1", "2", "3", "4", "5")) {
//            Toast.makeText(getApplicationContext(), "추가되었음", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(getApplicationContext(), "추가되지 않았음", Toast.LENGTH_SHORT).show();
//        }
//        finish();
//
//    }
}