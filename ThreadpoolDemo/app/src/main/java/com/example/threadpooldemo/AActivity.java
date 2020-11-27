package com.example.threadpooldemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class AActivity extends AppCompatActivity {

    private ExecutorService mFixedThreadPool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);
        //        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(2); //创建一个线程池
//        ExecutorService fixedThreadPool = Executors.newCachedThreadPool(); //创建一个可缓存的线程池
        //创建单个线程的线程池
        mFixedThreadPool = Executors.newSingleThreadExecutor();
//        ExecutorService fixedThreadPool = Executors.newScheduledThreadPool(1); //创建一个大小无限的线程池。此线程池支持定时以及周期性执行任务的需求。

    }

    public void startThread(View view) {
        int a = 20;

        for (int i = 0; i < a; i++) {

            final int finalI = i;
            Future<?> submit = mFixedThreadPool.submit(new Runnable() {
                @Override
                public void run() {
//                    System.out.println("正在执行任务"+ finalI);
                    try {
                        Thread.sleep(3000);
                        System.out.println("任务执行完毕" + finalI);
                        System.out.println("线程名字" + Thread.currentThread().getName());
                    } catch (InterruptedException e) {
                        System.out.println("任务被打断" + finalI + e.getMessage());
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFixedThreadPool.shutdownNow();
        mFixedThreadPool = null;
    }
}