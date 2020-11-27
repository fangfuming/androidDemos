package com.example.intentflagdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);
        System.out.println("onCreate     A");
    }

    public void toB(View view) {
        Intent intent = new Intent(this,BActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        System.out.println("onNewIntent     A");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("onDestroy     A");

    }
}