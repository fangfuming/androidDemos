package com.example.myplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myplayer.viewModel.MediaPlayerViewModel;



public class MainActivity extends AppCompatActivity {




    private MediaPlayerViewModel mViewModel;
    private ProgressBar mProgressBar;
    private SurfaceView mSurfaceView;
    private FrameLayout playerFrame;
    private FrameLayout controllerFrame;
    private SeekBar mSeekBar;
    private ImageView mPlayStatusImage;
    private TextView currentTimeTv;
    private TextView allTimeTv;
    private ImageView changeDirection;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            hideSystemUi();
            mViewModel.emmitVideoResolution();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initEvent();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initEvent() {

        mViewModel.getAllTime().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                allTimeTv.setText(s);
            }
        });

        /**
         * 缓存过程中的转圈圈是否可见
         */
        mViewModel.getProgressBarVisible().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                mProgressBar.setVisibility(integer);
            }
        });
        /**
         * 观察下方控制view的可见与否
         */
        mViewModel.getControllerViewVisible().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                controllerFrame.setVisibility(integer);
            }
        });

        mViewModel.getVideoResolution().observe(this, new Observer<Point>() {
            @Override
            public void onChanged(final Point point) {
                //视频分辨率出来后 时长也有了 给seekBar赋值
                mSeekBar.setMax(mViewModel.getMediaPlayer().getDuration());
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

//        playerFrame.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mViewModel.toggleControllerVisibility();
//            }
//        });

        playerFrame.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;

            }
                GestureDetector gestureDetector = new GestureDetector(MainActivity.this, new GestureDetector.SimpleOnGestureListener() {

                    /**
                     * 发生确定的单击时执行
                     * @param e
                     * @return
                     */
                    @Override
                    public boolean onSingleTapConfirmed(MotionEvent e) {//单击事件

                        mViewModel.toggleControllerVisibility(); //是否出现下方控制条
                        return super.onSingleTapConfirmed(e);
                    }

                    /**
                     * 双击发生时的通知
                     * @param e
                     * @return
                     */
                    @Override
                    public boolean onDoubleTap(MotionEvent e) {//双击事件
                        mViewModel.togglePlayerStatus();
                        return super.onDoubleTap(e);
                    }

                    /**
                     * 双击手势过程中发生的事件，包括按下、移动和抬起事件
                     * @param e
                     * @return
                     */
                    @Override
                    public boolean onDoubleTapEvent(MotionEvent e) {
                        return super.onDoubleTapEvent(e);
                    }
                });
        });


        mViewModel.getBufferPercent().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                mSeekBar.setSecondaryProgress(mSeekBar.getMax() * integer /100);
            }
        });

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //用户的拖动
                if (fromUser) {
                    mViewModel.seekToPosition(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mViewModel.getPlayerStatus().observe(this, new Observer<MediaPlayerViewModel.PlayerStatus>() {
            @Override
            public void onChanged(MediaPlayerViewModel.PlayerStatus playerStatus) {
                mPlayStatusImage.setClickable(true);
                if (playerStatus == MediaPlayerViewModel.PlayerStatus.NotReady) {
                    mPlayStatusImage.setClickable(false);
                }else if(playerStatus == MediaPlayerViewModel.PlayerStatus.Playing){
                    mPlayStatusImage.setImageResource(R.drawable.ic_pause_black_24dp);
                }else if(playerStatus == MediaPlayerViewModel.PlayerStatus.Pause){
                    mPlayStatusImage.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                }else if(playerStatus == MediaPlayerViewModel.PlayerStatus.Completed){
                    mPlayStatusImage.setImageResource(R.drawable.ic_replay_black_24dp);
                }
            }
        });

        mPlayStatusImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.togglePlayerStatus();
            }
        });

        //切换横竖屏
        changeDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    //横屏 切换为竖屏
                    MainActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }else if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
                    MainActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                }
            }
        });
    }

    private void initView() {
        mProgressBar = findViewById(R.id.progressBar);
        mSurfaceView = findViewById(R.id.surfaceView);
        playerFrame = findViewById(R.id.playerFrame);
        mSeekBar = findViewById(R.id.seekBar);
        mPlayStatusImage = findViewById(R.id.playImage);
        controllerFrame = findViewById(R.id.controllerFrame);
        allTimeTv = findViewById(R.id.allTimeTv);
        changeDirection = findViewById(R.id.change_direction);
        currentTimeTv = findViewById(R.id.currentTimeTv);
        mViewModel = new ViewModelProvider(this).get(MediaPlayerViewModel.class);
        mViewModel.setMainActivity(this);

        updatePlayerProgress();
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

    private void updatePlayerProgress(){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true){
                        try {
                            Thread.sleep(500);
                            mSeekBar.post(new Runnable() {
                                @Override
                                public void run() {
                                    mSeekBar.setProgress(mViewModel.getMediaPlayer().getCurrentPosition());
                                }
                            });
                            currentTimeTv.post(new Runnable() {
                                @Override
                                public void run() {
                                    int currentPosition = mViewModel.getMediaPlayer().getCurrentPosition();
                                    float second = currentPosition / 1000f; //秒
                                    int showSec = (int) (second % 60);
                                    boolean secAddZero = false;
                                    boolean minAddZero = false;
                                    if ((showSec / 10) == 0) {
                                        secAddZero = true;
                                    }
                                    int showMin = (int) (second / 60);
                                    if ((showMin / 10) == 0) {
                                        minAddZero = true;
                                    }
                                    currentTimeTv.setText(String.format("%s:%s",minAddZero?"0"+showMin:showMin,secAddZero?"0"+showSec:showSec));
                                }
                            });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
    }


    private void hideSystemUi(){
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                |View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                |View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                |View.SYSTEM_UI_FLAG_FULLSCREEN
                |View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
    }
}
