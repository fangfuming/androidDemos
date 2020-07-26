package com.example.myplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.res.Configuration;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.example.myplayer.viewModel.MediaPlayerViewModel;

public class MainActivity extends AppCompatActivity {

    private MediaPlayerViewModel mViewModel;

    private ProgressBar mProgressBar;

    private SurfaceView mSurfaceView;
    private FrameLayout playerFrame;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            hideSystemUi();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressBar = findViewById(R.id.progressBar);
        mSurfaceView = findViewById(R.id.surfaceView);
        playerFrame = findViewById(R.id.playerFrame);


        mViewModel = new ViewModelProvider(this).get(MediaPlayerViewModel.class);
//        Uri mUri = Uri.parse("android.resource://" + getPackageName() + "/"+ R.raw.video2);
//        mViewModel.initPlayer(this, mUri);
//        mViewModel.init();
        mViewModel.getProgressBarVisible().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                mProgressBar.setVisibility(integer);
            }
        });

        mViewModel.getVideoResolution().observe(this, new Observer<Point>() {
            @Override
            public void onChanged(final Point point) {
                playerFrame.post(new Runnable() {
                    @Override
                    public void run() {
                        resizePlayer(point.x,point.y);
                    }
                });
            }
        });

        getLifecycle().addObserver(mViewModel.getMediaPlayer());

        mSurfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                mViewModel.getMediaPlayer().setDisplay(mSurfaceView.getHolder());
                //保持屏幕常亮
                mViewModel.getMediaPlayer().setScreenOnWhilePlaying(true);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {}
        });
    }

    private void resizePlayer(int width,int height){
        if (width == 0 || height ==0) {
            return;
        }
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                playerFrame.getHeight()*width/height,
                ViewGroup.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        mSurfaceView.setLayoutParams(layoutParams);
    }


    private void hideSystemUi(){
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE
                |View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                |View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                |View.SYSTEM_UI_FLAG_FULLSCREEN
                |View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
    }
}
