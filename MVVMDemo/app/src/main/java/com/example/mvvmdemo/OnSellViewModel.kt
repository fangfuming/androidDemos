package com.example.mvvmdemo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.viewModelScope
import com.example.mvvmdemo.bean.OnSellData
import kotlinx.coroutines.launch

/**
 *  Created by fangfuming
 *  2020/10/24
 */
class OnSellViewModel :ViewModel() {

    private val repository:OnSellRepository by lazy {
        OnSellRepository()
    }

    private var mCurrentPage = 1 //默认第一页

    val contentList = MutableLiveData<MutableList<String>>()




    //加载首页内容
    fun loadContent(){
        this.listContentByPage(mCurrentPage)
    }


    private fun listContentByPage(page:Int){
        viewModelScope.launch {
            val sellList:OnSellData = repository.getSellList(page)
            println("结果数量"+sellList.tbk_dg_optimus_material_response.result_list.map_data.size)
        }
    }

    fun loadMore(){

    }

}