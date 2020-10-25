package com.example.mvvmdemo.api

import com.example.mvvmdemo.bean.OnSellData
import com.example.mvvmdemo.bean.ResultData
import retrofit2.http.GET
import retrofit2.http.Path

/**
 *  Created by fangfuming
 *  2020/10/24
 */
interface ApiService {
    companion object{
        const val BASE_URL = "https://api.sunofbeach.net/shop/"
    }

    @GET("onSell/{page}")
    suspend fun getOnSellList(@Path("page") page:Int): ResultData<OnSellData>
}