package com.example.mvvmdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

/**
 *  Created by fangfuming
 *  2020/10/24
 */
class OnSellActivity :AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_sell)


        val viewModel:OnSellViewModel = ViewModelProvider(this).get(OnSellViewModel::class.java)

        viewModel.loadContent()
    }
}