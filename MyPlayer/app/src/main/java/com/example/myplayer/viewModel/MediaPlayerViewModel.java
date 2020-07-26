package com.example.myplayer.viewModel;

import android.content.pm.PackageManager;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myplayer.R;

import java.io.IOException;

/**
 * Created by fangfuming
 * 2020/7/26
 */
    public class MediaPlayerViewModel extends ViewModel {

    private MutableLiveData<Integer> progressBarVisible = new MutableLiveData<>();
    private MutableLiveData<Point> videoResolution = new MutableLiveData<>();

    private MyMediaPlayer mMediaPlayer = new MyMediaPlayer();

    public MutableLiveData<Integer> getProgressBarVisible() {
        return progressBarVisible;
    }

    public MutableLiveData<Point> getVideoResolution() {
        return videoResolution;
    }

    public MyMediaPlayer getMediaPlayer() {
        return mMediaPlayer;
    }

    public MediaPlayerViewModel(){
        mMediaPlayer.reset();
        videoResolution.setValue(new Point(0,0));
        try {
            progressBarVisible.setValue(View.VISIBLE);
            mMediaPlayer.setDataSource("https://img.tukuppt.com/video_show/3670116/00/02/03/5b4f40c2b2fa3.mp4");
        } catch (IOException e) {
            e.printStackTrace();
        }


        /**
         * 监听视频准备完毕回调
         */
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                progressBarVisible.setValue(View.INVISIBLE);
                mMediaPlayer.setLooping(true);
                mMediaPlayer.start();
            }
        });

        /**
         * 获取视频的宽高回调
         */
        mMediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
            @Override
            public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                videoResolution.setValue(new Point(width,height));
            }
        });


        mMediaPlayer.prepareAsync();

    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mMediaPlayer.release();
    }
}
