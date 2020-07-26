package com.example.myplayer.viewModel;

import android.media.MediaPlayer;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * Created by fangfuming
 * 2020/7/26
 */
public class MyMediaPlayer extends MediaPlayer implements LifecycleObserver {


    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
   public void pausePlayer(){
       pause();
   }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void resumePlayer(){
        start();
    }
}
