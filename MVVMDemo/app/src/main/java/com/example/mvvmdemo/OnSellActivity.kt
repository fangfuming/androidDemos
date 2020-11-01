package com.example.mvvmdemo

import android.graphics.Rect
import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmdemo.adapter.OnSellListAdapter
import com.example.mvvmdemo.base.LoadState
import kotlinx.android.synthetic.main.activity_on_sell.*
import kotlinx.android.synthetic.main.state_on_error.*

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
            addItemDecoration(object :RecyclerView.ItemDecoration(){
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    outRect.top=8
                    outRect.bottom=8
                    outRect.left=8
                    outRect.right=8
                }
            })
        }

        //点击重新加载
        reloadLl.setOnClickListener{
            viewModel.loadContent()
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
            loadState.observe(this@OnSellActivity, Observer {
                hideAll()
                when(it){
                    LoadState.EMPTY-> emptyView.visibility = View.VISIBLE
                    LoadState.ERROR-> errorView.visibility = View.VISIBLE
                    LoadState.SUCCESS-> recyclerView.visibility = View.VISIBLE
                    else -> loadingView.visibility = View.VISIBLE
                }
            })
        }


    }


    private val viewModel by lazy {
        ViewModelProvider(this).get(OnSellViewModel::class.java)
    }

    private fun hideAll(){
        recyclerView.visibility = View.GONE
        emptyView.visibility = View.GONE
        loadingView.visibility = View.GONE
        errorView.visibility = View.GONE
    }
}