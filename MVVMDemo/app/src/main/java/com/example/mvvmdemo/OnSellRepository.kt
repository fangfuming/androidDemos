package com.example.mvvmdemo

import com.example.mvvmdemo.api.RetrofitClient
import com.example.mvvmdemo.bean.OnSellData
import com.example.mvvmdemo.bean.ResultData

/**
 *  Created by fangfuming
 *  2020/10/24
 */
class OnSellRepository {

    suspend fun getSellList(page:Int) = RetrofitClient.apiService.getOnSellList(page).apiData()


}