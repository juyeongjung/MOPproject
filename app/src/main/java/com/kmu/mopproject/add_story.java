package com.kmu.mopproject;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class add_story extends AppCompatActivity {

    private DBHelper mydb;
    TextView title;
    TextView date;
    TextView category;
    TextView media_src;
    TextView main;
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
    }

    private String getTime(){
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        return mFormat.format(mDate);
    }

    public void insert(View view) {
        System.out.println("insert start");
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            System.out.println("not null");

            int Value = extras.getInt("id");
            if (Value > 0) {
                if (mydb.updateStory(id, title.getText().toString(), date.getText().toString(), category.getText().toString(), media_src.getText().toString(), main.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "수정되었음", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), com.kmu.mopproject.MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "수정되지 않았음", Toast.LENGTH_SHORT).show();
                }
            } else {
                System.out.println("insert");

                if (mydb.insertStory(title.getText().toString(), date.getText().toString(), category.getText().toString(), media_src.getText().toString(), main.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "추가되었음", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "추가되지 않았음", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        }
        System.out.println("null");

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
                if (mydb.updateStory(id, title.getText().toString(), date.getText().toString(), category.getText().toString(), media_src.getText().toString(), main.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "수정되었음", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "수정되지 않았음", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}