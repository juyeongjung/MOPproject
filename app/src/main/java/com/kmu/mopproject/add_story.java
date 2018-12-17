package com.kmu.mopproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
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
    ImageView media;
    TextView main;
    CheckBox option1;
    CheckBox option2;
    CheckBox option3;
    int id = 0;

    long mNow;
    Date mDate;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    final int REQ_CODE_SELECT_IMAGE=100;


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
        media=(ImageView)findViewById(R.id.imageView2);

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
                } else if(option2.isChecked()){
                    category.setText((CharSequence)"foods");
                } else if(option3.isChecked()){
                    category.setText((CharSequence)"travel");
                } else{
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
                } else if(option2.isChecked()){
                    category.setText((CharSequence)"foods");
                } else if(option3.isChecked()){
                    category.setText((CharSequence)"travel");
                } else{
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

    public void onClick(View v){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
//        intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQ_CODE_SELECT_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        Toast.makeText(getBaseContext(), "resultCode : "+resultCode,Toast.LENGTH_SHORT).show();

        if(requestCode == REQ_CODE_SELECT_IMAGE)
        {
            if(resultCode==Activity.RESULT_OK)
            {
                try {
                    //Uri에서 이미지 이름을 얻어온다.
                    String name_Str = getImageNameToUri(data.getData());

                    media_src.setText(name_Str);
                    //이미지 데이터를 비트맵으로 받아온다.
                    Bitmap image_bitmap 	= MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                    media = (ImageView)findViewById(R.id.imageView2);

                    //배치해놓은 ImageView에 set
                    media.setImageBitmap(image_bitmap);


                    //Toast.makeText(getBaseContext(), "name_Str : "+name_Str , Toast.LENGTH_SHORT).show();


                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getImageNameToUri(Uri data)
    {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(data, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        String imgPath = cursor.getString(column_index);
//        String imgName = imgPath.substring(imgPath.lastIndexOf("/")+1);

        return imgPath;
    }

}