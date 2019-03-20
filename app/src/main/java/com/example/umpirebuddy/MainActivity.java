package com.example.umpirebuddy;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuView;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.app.AlertDialog;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private TextView Strikecounter;
    private TextView totalcount;
    private Button Strike_button;
    private TextView Ballcounter;
    private Button Ball_button;
    private int strike_counter = 0;
    private int ball_counter = 0;
    private int total_count = 0;
    private int totalcount1 = 0;
    private SharedPreferences mpreference;
    private SharedPreferences.Editor editor;
    public static final String key_count1 = "Count: ";
    public static final String Share_pref = "Share_pref";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Strikecounter=(TextView) findViewById(R.id.Strikecounter);
        Ballcounter = (TextView) findViewById(R.id.Ballcounter);
        totalcount = (TextView) findViewById(R.id.totalcount);
        TextView textView = findViewById(R.id.count);
        registerForContextMenu(textView);
        loaddata();

    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.strike:
                strike_counter++;
                Strikecounter.setText("STRIKE: "+Integer.toString(strike_counter));
                if(strike_counter==3){
                    strike_counter=0;
                    Strikecounter.setText("STRIKE: "+Integer.toString(strike_counter));
                    AlertDialog.Builder alert1 = new AlertDialog.Builder(MainActivity.this);
                    alert1.setMessage("Out!");
                    alert1.setPositiveButton("OK", null);
                    alert1.show();
                    total_count++;
                    totalcount.setText("Count: "+Integer.toString(total_count));
                    savedata();
                }
                return true;
            case R.id.ball:
                ball_counter++;
                Ballcounter.setText("BALLS: "+Integer.toString(ball_counter));
                if(ball_counter==4){
                    ball_counter=0;
                    Ballcounter.setText("BALLS: "+Integer.toString(ball_counter));
                    AlertDialog.Builder alert1 = new AlertDialog.Builder(MainActivity.this);
                    alert1.setMessage("Walk!");
                    alert1.setPositiveButton("OK", null);
                    alert1.show();
                }
                return true;
            default:
                return super.onContextItemSelected(item);
        }

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        getMenuInflater().inflate(R.menu.menu_count, menu);

    }

    public void count_strike(View view){
        strike_counter++;
        Strikecounter.setText("STRIKE: "+Integer.toString(strike_counter));
        if(strike_counter==3){
            AlertDialog.Builder alert1 = new AlertDialog.Builder(MainActivity.this);
            alert1.setMessage("Out!");
            alert1.setPositiveButton("OK", null);
            alert1.show();
            total_count++;
            savedata();

            totalcount.setText("Count: "+Integer.toString(total_count));
            strike_counter=0;
            ball_counter=0;
        }
        Strikecounter.setText("STRIKE: "+Integer.toString(strike_counter));
        Ballcounter.setText("BALLS: "+Integer.toString(ball_counter));


    }
    public void count_ball(View view){
        ball_counter++;
        Ballcounter.setText("BALLS: "+Integer.toString(ball_counter));
        if(ball_counter == 4){
            AlertDialog.Builder alert2 = new AlertDialog.Builder(MainActivity.this);
            alert2.setMessage("Walk!");
            alert2.setPositiveButton("OK", null);
            alert2.show();
            ball_counter=0;
            strike_counter=0;
        }
        Ballcounter.setText("BALLS: "+Integer.toString(ball_counter));
        Strikecounter.setText("STRIKE: "+Integer.toString(strike_counter));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater _inflater = getMenuInflater();
        _inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    public void savedata(){
        mpreference = getSharedPreferences(Share_pref, MODE_PRIVATE);
        editor = mpreference.edit();
        editor.putInt(key_count1, total_count);
        editor.apply();
        editor.commit();


    }
    public void loaddata(){
        SharedPreferences sharedPreferences = getSharedPreferences(Share_pref, Context.MODE_PRIVATE);
        totalcount1 = sharedPreferences.getInt(key_count1,0);
        total_count = totalcount1; // This way the value won't be reset
        totalcount.setText("Count: "+Integer.toString(total_count));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.about:
                Intent intent1 = new Intent(this, About.class);
                this.startActivity(intent1);
                return true;

            case R.id.reset:
                strike_counter=0;
                ball_counter=0;
                total_count=0;
                savedata();
                Ballcounter.setText("BALLS: "+Integer.toString(ball_counter));
                Strikecounter.setText("STRIKE: "+Integer.toString(strike_counter));
                totalcount.setText("Count: "+Integer.toString(total_count));


            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
