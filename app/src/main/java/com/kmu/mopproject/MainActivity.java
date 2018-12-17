package com.kmu.mopproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.kmu.mopproject.add_story.*;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ListView myListView;
    DBHelper mydb;
    ArrayAdapter mAdapter;

    long mNow;
    Date mDate;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mydb = new DBHelper(this);
        ArrayList array_list = mydb.getAllStories();

        mAdapter =
                new ArrayAdapter(this, android.R.layout.simple_list_item_1, array_list);

        myListView = (ListView) findViewById(R.id.listView1);
        myListView.setAdapter(mAdapter);

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long arg4) {
                String item = (String) ((ListView) parent).getItemAtPosition(position);
                String[] strArray = item.split(" ");
                int id = Integer.parseInt(strArray[0]);
                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", id);
                Intent intent = new Intent(getApplicationContext(), add_story.class);
                intent.putExtras(dataBundle);
                startActivity(intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", 0);
                Intent intent = new Intent(getApplicationContext(), add_story.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id==R.id.nav_normal_day){
            Intent intent = new Intent(MainActivity.this, normal_day.class);
            startActivity(intent);
        } else if (id == R.id.nav_special_day) {
            Intent intent = new Intent(MainActivity.this, special_day.class);
            startActivity(intent);
        } else if (id == R.id.nav_travel) {
            Intent intent=new Intent(MainActivity.this,travel.class);
            startActivity(intent);
        } else if (id == R.id.nav_foods_pic) {
            Intent intent=new Intent(MainActivity.this,foods_pic.class);
            startActivity(intent);
        } else if (id == R.id.nav_bucket_list) {
            Intent intent=new Intent(MainActivity.this,bucket_list.class);
            startActivity(intent);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private String getTime(){
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        return mFormat.format(mDate);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.clear();
        mAdapter.addAll(mydb.getAllStories());
        mAdapter.notifyDataSetChanged();
    }

    public void onClick(View view){

        TextView fastadd=(TextView) findViewById(R.id.editText5);
        if(fastadd.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "내용을 입력하세요", Toast.LENGTH_SHORT).show();
        }
        else{
            mAdapter.addAll(mydb.insertStory(fastadd.getText().toString(),getTime(),"normal_day","null",fastadd.getText().toString()));
//            mAdapter.addAll(ContextCompat.getDrawable(this,R.drawable.one));
            fastadd.setText(null);
            HideKeyboard(this);
            onResume();
        }
    }

    public static void HideKeyboard(Activity activity)
    {
        InputMethodManager im = (InputMethodManager) activity.getSystemService(activity.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

}
