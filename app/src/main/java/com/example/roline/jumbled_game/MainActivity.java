package com.example.roline.jumbled_game;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.text.DateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    int n;

    SharedPreferences p;
    Button b,exit;
    TextView t,t3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b=(Button)findViewById(R.id.button);
        exit=(Button)findViewById(R.id.button4);
        t=(TextView)findViewById(R.id.textView2);


       // Toast.makeText(MainActivity.this,"onCreate called",Toast.LENGTH_SHORT).show();
        p=getSharedPreferences("score",MODE_PRIVATE);
        String h=p.getString("highsc","0");
        t.setText("highscore:"+h);



        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                Intent i=new Intent(MainActivity.this,Main2Activity.class);
                startActivity(i);

            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        //Toast.makeText(MainActivity.this,"onRestart called",Toast.LENGTH_SHORT).show();
        p=getSharedPreferences("score",MODE_PRIVATE);
        String h=p.getString("highsc","0");
        t.setText("highscore:"+h);

    }

    @Override
    protected void onStart() {
        super.onStart();
        t3=(TextView)findViewById(R.id.times);
        String currentDateTimeString= DateFormat.getTimeInstance().format(new Date());



        // textView is the TextView view that should display it
        t3.setText(currentDateTimeString);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Intent i=new Intent(MainActivity.this,MusicActivity.class);
        stopService(i);
    }
}
