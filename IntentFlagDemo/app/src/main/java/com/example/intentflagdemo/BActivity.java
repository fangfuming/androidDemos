package com.example.intentflagdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class BActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);
    }

    public void toC(View view) {
        Intent intent = new Intent(this,CActivity.class);
        startActivity(intent);
    }
}