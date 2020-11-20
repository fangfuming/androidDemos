package com.example.mvvmdemo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmdemo.base.LoadState
import com.example.mvvmdemo.bean.MapData
import com.example.mvvmdemo.bean.OnSellData
import kotlinx.coroutines.launch
import java.lang.Exception
import java.lang.NullPointerException
import java.util.Collections.list

/**
 *  Created by fangfuming
 *  2020/10/24
 */
class OnSellViewModel :ViewModel() {


    val loadState = MutableLiveData<LoadState>()



    private val repository:OnSellRepository by lazy {
        OnSellRepository()
    }

    private var mCurrentPage = 1 //默认第一页

    val contentList = MutableLiveData<MutableList<MapData>>()



    private var isLoadMore = false

    //加载首页内容
    fun loadContent(){
        isLoadMore = false
        loadState.value = LoadState.LOADING
        this.listContentByPage(mCurrentPage)
    }


    /**
     * 加载更多
     */
    fun loadMore(){
        isLoadMore = true
        loadState.value = LoadState.LOADMORE_LOADING
        mCurrentPage++
        listContentByPage(mCurrentPage)
    }


    private fun listContentByPage(page:Int){
        viewModelScope.launch {
            try {
                val sellList:OnSellData = repository.getSellList(page)
                val oldList = contentList.value?: mutableListOf()
                oldList.addAll(sellList.tbk_dg_optimus_material_response.result_list.map_data)
                println("结果数量"+sellList.tbk_dg_optimus_material_response.result_list.map_data.size)
                contentList.value = oldList
                if (sellList.tbk_dg_optimus_material_response.result_list.map_data.isEmpty()) {
                    loadState.value = if(isLoadMore)LoadState.LOADMORE_EMPTY else LoadState.EMPTY
                }else{
                    loadState.value = LoadState.SUCCESS
                }
            }catch (e:Exception){
                mCurrentPage--
                e.printStackTrace()
                if (e is NullPointerException) {
                    //todo 没有更多
                    loadState.value = LoadState.LOADMORE_EMPTY
                }else{
                    loadState.value =if(isLoadMore)LoadState.LOADMORE_ERROR else LoadState.ERROR
                }

            }



        }
    }


}