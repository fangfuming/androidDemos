package com.example.xietiaolayoutdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }



    public void withToolBar(View view) {

        Intent intent = new Intent(this,WithToolBarActivity.class);
        startActivity(intent);
    }

    public void scrollToChangeTitle(View view) {

        Intent intent = new Intent(this,scrollToChangeTitleActivity.class);
        startActivity(intent);
    }
}