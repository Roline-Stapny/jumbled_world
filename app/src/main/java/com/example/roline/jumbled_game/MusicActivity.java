package com.example.roline.jumbled_game;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by Roline on 26-06-2017.
 */
public class MusicActivity extends Service {

    MediaPlayer mp;

    @Override
    public void onCreate() {
        super.onCreate();
        mp=MediaPlayer.create(MusicActivity.this,R.raw.song1);
        mp.setLooping(true);

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {


        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(!mp.isPlaying())
        {
            mp.start();
        }

        return super.onStartCommand(intent, flags, startId);

    }


    @Override
    public void onDestroy() {
        super.onDestroy();



        //Toast.makeText(MusicActivity.this,"song stopped",Toast.LENGTH_SHORT).show();
        if(mp.isPlaying())
        {
            mp.pause();

        }

    }
}