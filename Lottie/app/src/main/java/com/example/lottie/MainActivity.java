package com.example.lottie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;

import android.os.Bundle;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;

public class MainActivity extends AppCompatActivity {

    private LottieAnimationView mLottieAnimationView;
    private LottieAnimationView mLottieAnimationView2;
    private MotionLayout motionLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLottieAnimationView = findViewById(R.id.lottie);
        mLottieAnimationView2 = findViewById(R.id.lottie2);
        motionLayout = findViewById(R.id.motionLayout);


        motionLayout.setTransitionListener(new MotionLayout.TransitionListener() {
            @Override
            public void onTransitionStarted(MotionLayout motionLayout, int i, int i1) {

            }

            @Override
            public void onTransitionChange(MotionLayout motionLayout, int i, int i1, float v) {
                mLottieAnimationView.setProgress(v);
                mLottieAnimationView2.setProgress(v);
            }

            @Override
            public void onTransitionCompleted(MotionLayout motionLayout, int i) {

            }

            @Override
            public void onTransitionTrigger(MotionLayout motionLayout, int i, boolean b, float v) {

            }
        });
    }

    public void showAnimation(View view) {
        mLottieAnimationView.playAnimation();
    }

    public void showAnimation2(View view) {
        mLottieAnimationView2.playAnimation();
    }
}