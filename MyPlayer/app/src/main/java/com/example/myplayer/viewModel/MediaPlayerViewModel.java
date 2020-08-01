package com.example.myplayer.viewModel;

import android.content.pm.PackageManager;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.view.View;

import androidx.annotation.UiThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myplayer.MainActivity;
import com.example.myplayer.R;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by fangfuming
 * 2020/7/26
 */
    public class MediaPlayerViewModel extends ViewModel {

    public enum PlayerStatus{
        Playing,
        Pause,
        Completed,
        NotReady
    }

    private MainActivity mMainActivity;

    public void setMainActivity(MainActivity mainActivity) {
        mMainActivity = mainActivity;
    }

    private MutableLiveData<Integer> progressBarVisible = new MutableLiveData<>(View.INVISIBLE); //转圈圈可见
    private MutableLiveData<Integer> bufferPercent = new MutableLiveData<>(0); //缓冲的百分比
    private MutableLiveData<Integer> controllerViewVisible = new MutableLiveData<>(View.VISIBLE); //下方控制条可见
    private MutableLiveData<Point> videoResolution = new MutableLiveData<>();
    private MutableLiveData<PlayerStatus> playerStatus = new MutableLiveData<>(PlayerStatus.NotReady);


    public void setProgressBarVisible(MutableLiveData<Integer> progressBarVisible) {
        this.progressBarVisible = progressBarVisible;
    }

    public MutableLiveData<Integer> getControllerViewVisible() {
        return controllerViewVisible;
    }

    public void setControllerViewVisible(MutableLiveData<Integer> controllerViewVisible) {
        this.controllerViewVisible = controllerViewVisible;
    }

    private MyMediaPlayer mMediaPlayer = new MyMediaPlayer();

    public MutableLiveData<Integer> getProgressBarVisible() {
        return progressBarVisible;
    }

    public MutableLiveData<Point> getVideoResolution() {
        return videoResolution;
    }


    public MutableLiveData<PlayerStatus> getPlayerStatus() {
        return playerStatus;
    }

    public void setPlayerStatus(MutableLiveData<PlayerStatus> playerStatus) {
        this.playerStatus = playerStatus;
    }

    public MyMediaPlayer getMediaPlayer() {
        return mMediaPlayer;
    }

    public MediaPlayerViewModel(){
        mMediaPlayer.reset();
        videoResolution.setValue(new Point(0,0));
        playerStatus.setValue(PlayerStatus.NotReady);
        controllerViewVisible.setValue(View.INVISIBLE);
        progressBarVisible.setValue(View.VISIBLE);
        try {
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
                mMediaPlayer.start();
                playerStatus.setValue(PlayerStatus.Playing);
            }
        });

        mMediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                 bufferPercent.setValue(percent);
            }
        });

        /**
         * 拖动完成监听
         */
        mMediaPlayer.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
            @Override
            public void onSeekComplete(MediaPlayer mp) {
                mMediaPlayer.start();
                progressBarVisible.setValue(View.INVISIBLE);
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

        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                playerStatus.setValue(PlayerStatus.Completed);
            }
        });



        mMediaPlayer.prepareAsync();

    }

    public MutableLiveData<Integer> getBufferPercent() {
        return bufferPercent;
    }

    public void setBufferPercent(MutableLiveData<Integer> bufferPercent) {
        this.bufferPercent = bufferPercent;
    }

    //重新布局player
    public void  emmitVideoResolution(){
        videoResolution.setValue(videoResolution.getValue());
    }


    private long controllerViewShowTime = 0;

    //
    public void toggleControllerVisibility(){
        if (View.INVISIBLE == controllerViewVisible.getValue()) {
            controllerViewVisible.setValue(View.VISIBLE);
            controllerViewShowTime = System.currentTimeMillis();
            final Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (System.currentTimeMillis() - controllerViewShowTime>6000) {
                        mMainActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                controllerViewVisible.setValue(View.INVISIBLE);
                            }
                        });
                    }
                }
            },6000);
        }else {
            controllerViewVisible.setValue(View.INVISIBLE);
        }
    }

    public void seekToPosition(int position){
        progressBarVisible.setValue(View.VISIBLE);
        mMediaPlayer.seekTo(position);
    }


    public void togglePlayerStatus(){
        if (playerStatus.getValue() == PlayerStatus.Playing) {
            mMediaPlayer.pause();
            playerStatus.setValue(PlayerStatus.Pause);
        }else if (playerStatus.getValue() == PlayerStatus.Pause) {
            mMediaPlayer.start();
            playerStatus.setValue(PlayerStatus.Playing);
        }else if (playerStatus.getValue() == PlayerStatus.Completed) {
            mMediaPlayer.start();
            playerStatus.setValue(PlayerStatus.Playing);
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mMediaPlayer.release();
    }
}
