//package com.kmu.mopproject;
//
//import android.content.Intent;
//import android.database.Cursor;
//import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//public class add_story extends AppCompatActivity {
//
//    private DBHelper mydb;
//    TextView headline;
//    TextView date;
//    TextView category;
//    TextView media_src;
//    TextView main;
//    int id = 0;
//
//    long mNow;
//    Date mDate;
//    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.content_add_story);
//        headline = (TextView) findViewById(R.id.editHeadline);
//        date = (TextView) findViewById(R.id.editHeadline); //수정필요
//        category = (TextView) findViewById(R.id.editHeadline);
//        media_src = (TextView) findViewById(R.id.editHeadline);
//        main = (TextView) findViewById(R.id.editMain);
//
//        mydb = new DBHelper(this);
//        Bundle extras = getIntent().getExtras();
//        if (extras != null) {
//            int Value = extras.getInt("id");
//            if (Value > 0) {
//                Cursor rs = mydb.getData(Value);
//                id = Value;
//                rs.moveToFirst();
//                String n = rs.getString(rs.getColumnIndex(DBHelper.STORY_COLUMN_HEADLINE));
//                String d = rs.getString(rs.getColumnIndex(DBHelper.STORY_COLUMN_DATE));
//                String y = rs.getString(rs.getColumnIndex(DBHelper.STORY_COLUMN_CATEGORY));
//                String na = rs.getString(rs.getColumnIndex(DBHelper.STORY_COLUMN_MEDIA_SRC));
//                String r = rs.getString(rs.getColumnIndex(DBHelper.STORY_COLUMN_MAIN));
//                if (!rs.isClosed()) {
//                    rs.close();
//                }
//                Button b = (Button) findViewById(R.id.addStory);
//                b.setVisibility(View.INVISIBLE);
//
//                headline.setText((CharSequence) n);
//                date.setText((CharSequence) d);
//                category.setText((CharSequence) y);
//                media_src.setText((CharSequence) na);
//                main.setText((CharSequence) r);
//            }
//        }
//
//        setContentView(R.layout.activity_add_story);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//    }
//        public void insert(View view) {
//            System.out.println("insert");
//            Bundle extras = getIntent().getExtras();
//            if (extras != null) {
//                System.out.println("Not null");
//                int Value = extras.getInt("id");
//                if (Value > 0) {
//                    System.out.println("update");
//
//                    if (mydb.updateStory(id, headline.getText().toString(), date.getText().toString(), category.getText().toString(), media_src.getText().toString(), main.getText().toString())) {
//                        Toast.makeText(getApplicationContext(), "수정되었음", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(getApplicationContext(), com.kmu.mopproject.MainActivity.class);
//                        startActivity(intent);
//                    } else {
//                        Toast.makeText(getApplicationContext(), "수정되지 않았음", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    System.out.println("insert story");
//
//                    if (mydb.insertStory(headline.getText().toString(), date.getText().toString(), category.getText().toString(), media_src.getText().toString(), main.getText().toString())) {
//                        Toast.makeText(getApplicationContext(), "추가되었음", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(getApplicationContext(), "추가되지 않았음", Toast.LENGTH_SHORT).show();
//                    }
//                    finish();
//                }
//            }
//            System.out.println("null?");
//
//        }
//
//        public void delete(View view) {
//            Bundle extras = getIntent().getExtras();
//            if (extras != null) {
//                int Value = extras.getInt("id");
//                if (Value > 0) {
//                    mydb.deleteStory(id);
//                    Toast.makeText(getApplicationContext(), "삭제되었음", Toast.LENGTH_SHORT).show();
//                    finish();
//                } else {
//                    Toast.makeText(getApplicationContext(), "삭제되지 않았음", Toast.LENGTH_SHORT).show();
//                }
//            }
//        }
//
//        public void edit(View view) {
//            Bundle extras = getIntent().getExtras();
//            if (extras != null) {
//                int value = extras.getInt("id");
//                if (value > 0) {
//                    if (mydb.updateStory(id, headline.getText().toString(), date.getText().toString(), category.getText().toString(), media_src.getText().toString(), main.getText().toString())) {
//                        Toast.makeText(getApplicationContext(), "수정되었음", Toast.LENGTH_SHORT).show();
//                        finish();
//                    } else {
//                        Toast.makeText(getApplicationContext(), "수정되지 않았음", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//        }
//
//}
