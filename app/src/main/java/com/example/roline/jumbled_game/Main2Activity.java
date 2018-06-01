package com.example.roline.jumbled_game;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Random;

public class Main2Activity extends AppCompatActivity {

     static int highscores;


    int q=0;
    TextView jumbled,score;
    Button next,submit;
    EditText ans;
    TextView time;

    String []city={"absorb","actual","acetic","advice","android","barbie","battle","behold","bottle","campus","cattle","cheat","centre","choice","cocain","deadly","defeat","drinks","dwarfs","effort","enough","expand","eyelid","express","faulty","friend","france","freeze","future","facebook","gender","gospel","gossip","google","heaven","harbour","health","horse","iconic","invite","invoke","install","junior","jungle","juice","joyful","kidney","lawyer","lizard","legend","marine","medium","minute","music","microsoft","nestle","nozzle","nurse","naughty","orange","organ","ozone","olive","parlour","patent","peace","pelvic","petrol","queen","quiet","quick","rabbit","rescue","reward","revolt","sensor","shrine","stitch","syntax","tomato","tribal","tremor","train","update","usual","uncle","unity","value","venue","violin","violet","verify","wizard","white","woman","weird","youth","young","xerox","yellow","yeild","zebra","zigzag","people","estate","apple","mango"};


    SharedPreferences sp;



    ToggleButton t;
    Button exits,hints;

    Random r=new Random();
    int x;
    String z;


    // code for the timer
    class MyCount extends CountDownTimer {
        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        MainActivity app1 = new MainActivity();

        @Override
        public void onFinish() {
            // TODO Auto-generated method stub

            submit.performClick();
            time.setText("0");

        }

        @Override
        public void onTick(long millisUntilFinished) {
            // TODO Auto-generated method stub

            time.setText(Long.toString(millisUntilFinished/1000));

        }
    }
    final MyCount counter = new MyCount(30000,1000);



    @Override
    protected void onCreate(Bundle savedInstanceState) {





        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        jumbled=(TextView)findViewById(R.id.textView);
        ans=(EditText)findViewById(R.id.editText);
        next=(Button)findViewById(R.id.button2);
        submit=(Button)findViewById(R.id.button3);
        score=(TextView)findViewById(R.id.textView2);
        t=(ToggleButton)findViewById(R.id.toggleButton);
        time=(TextView)findViewById(R.id.timer);
        exits=(Button)findViewById(R.id.exit);
        hints=(Button)findViewById(R.id.hint);


        //play song at start
        Intent i=new Intent(Main2Activity.this,MusicActivity.class);
        startService(i);
        //Toast.makeText(Main2Activity.this,"song started",Toast.LENGTH_SHORT).show();
        // start the timer


        //starts the timer
        counter.start();

        //exists the game pressing exit button
        exits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter.cancel();
                if(q > highscores)
                {
                    highscores=q;
                    sp=getSharedPreferences("score",MODE_PRIVATE);
                    SharedPreferences.Editor editor=sp.edit();
                    String sc=Integer.toString(highscores);

                    editor.putString("highsc",sc);
                    editor.commit();


                }
                finish();
            }
        });





        //background music on off
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Main2Activity.this,MusicActivity.class);

                if(!t.isChecked())
                {

                    startService(i);
                }
                else
                {
                    stopService(i);
                }
            }
        });




        score.setText(Integer.toString(q));
        x=r.nextInt(city.length);
        z=city[x];

        String t=scramble(r,city[x]);
        jumbled.setText(t);

        //code for next button

        next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                hints.setVisibility(View.VISIBLE);
                counter.cancel();
                counter.start();
                x=r.nextInt(city.length);
                z=city[x];

                String t=scramble(r,city[x]);
                ans.setText("");
                jumbled.setText(t);


            }
        });


        // gives the hint
        hints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /*Toast.makeText(Main2Activity.this,z,Toast.LENGTH_SHORT).show();*/
                char a[] = z.toCharArray();
                char m=a[0];
                ans.setText(""+m);
                q-=25;
                score.setText(Integer.toString(q));
                hints.setVisibility(View.GONE);
            }
        });

        //code for submit button
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hints.setVisibility(View.VISIBLE);
                String a=ans.getText().toString();

                if(z.equals(a))
                {
                    jumbled.setText(z);
                    Toast.makeText(Main2Activity.this,"PERFECT "+("\ud83d\ude01"),Toast.LENGTH_SHORT).show();
                    q+=100;
                    score.setText(Integer.toString(q));
                    next.performClick();

                }
                else
                {
                    q-=50;
                    score.setText(Integer.toString(q));
                    Toast.makeText(Main2Activity.this,"ANS: "+z,Toast.LENGTH_SHORT).show();
                    next.performClick();
                }
            }
        });


        //  submit answer on pressing enter key
        ans.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if((event != null) && (event.getKeyCode()==KeyEvent.KEYCODE_ENTER) || (actionId== EditorInfo.IME_ACTION_DONE))
                {

                    submit.performClick();
                }


                return false;
            }
        });


    }
    //funtion to jumble the word
    public static String scramble( Random random, String inputString )
    {
        // Convert your string into a simple char array:
        char a[] = inputString.toCharArray();

        // Scramble the letters using the standard Fisher-Yates shuffle,
        for( int i=0 ; i<a.length ; i++ )
        {
            int j = random.nextInt(a.length);
            // Swap letters
            char temp = a[i]; a[i] = a[j];  a[j] = temp;
        }

        return new String( a );
    }



    //to destroy the process when we exit from the app
    @Override
    protected void onDestroy() {
        super.onDestroy();

        //Toast.makeText(Main2Activity.this,"ondestroy called",Toast.LENGTH_SHORT).show();
        if(q > highscores)
        {
            highscores=q;
            sp=getSharedPreferences("score",MODE_PRIVATE);
            SharedPreferences.Editor editor=sp.edit();
            String sc=Integer.toString(highscores);

            editor.putString("highsc",sc);
            editor.commit();


        }
        //Toast.makeText(Main2Activity.this,"highest score is "+highscores,Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onPause() {
        super.onStop();
        //stop the timer thread
        counter.cancel();

        //save the high score

        if(q > highscores)
        {
            highscores=q;
            sp=getSharedPreferences("score",MODE_PRIVATE);
            SharedPreferences.Editor editor=sp.edit();
            String sc=Integer.toString(highscores);

            editor.putString("highsc",sc);
            editor.commit();


        }
    }
}

