package com.example.mvvmdemo.api

import com.example.mvvmdemo.bean.ResultData
import retrofit2.http.GET
import retrofit2.http.Path

/**
 *  Created by fangfuming
 *  2020/10/24
 */
interface ApiService {
    companion object{
        const val BASE_URL = "https://www.sunofbeach.net/shop/"
    }

    @GET("onSell/{page}")
    suspend fun getOnSellList(@Path(value = "page") page:Int): ResultData<>
}