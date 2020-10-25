package com.example.mvvmdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvmdemo.adapter.OnSellListAdapter
import kotlinx.android.synthetic.main.activity_on_sell.*

/**
 *  Created by fangfuming
 *  2020/10/24
 */
class OnSellActivity :AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_sell)
        initView()
        initObServer()
        loadData()
    }

    private fun loadData() {
        viewModel.loadContent()
    }

    private val onSellListAdapter by lazy {
        OnSellListAdapter()
    }

    private fun initView() {
        recyclerView.run {
            layoutManager = LinearLayoutManager(this@OnSellActivity)
            adapter = onSellListAdapter
        }
    }


    /**
     * 观察数据
     */
    private fun initObServer() {
        viewModel.run {
            contentList.observe(this@OnSellActivity, Observer {
                //内容列表更新
                //更新适配器
                onSellListAdapter.setData(it)
            })
        }
    }


    private val viewModel by lazy {
        ViewModelProvider(this).get(OnSellViewModel::class.java)
    }
}