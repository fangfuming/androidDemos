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

    val contentList = MutableLiveData<List<MapData>>()




    //加载首页内容
    fun loadContent(){
        this.listContentByPage(mCurrentPage)
    }


    private fun listContentByPage(page:Int){
        loadState.value = LoadState.LOADING
        viewModelScope.launch {
            try {
                val sellList:OnSellData = repository.getSellList(page)
                println("结果数量"+sellList.tbk_dg_optimus_material_response.result_list.map_data.size)
                contentList.value = sellList.tbk_dg_optimus_material_response.result_list.map_data
                if (sellList.tbk_dg_optimus_material_response.result_list.map_data.isEmpty()) {
                    loadState.value = LoadState.EMPTY
                }else{
                    loadState.value = LoadState.SUCCESS
                }
            }catch (e:Exception){
                e.printStackTrace()
                if (e is NullPointerException) {
                    //todo 没有更多

                }else{
                    loadState.value = LoadState.ERROR
                }

            }



        }
    }

    fun loadMore(){

    }

}