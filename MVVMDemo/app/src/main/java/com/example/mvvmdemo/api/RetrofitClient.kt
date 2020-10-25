package com.example.mvvmdemo.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 *  Created by fangfuming
 *  2020/10/24  创建retrofit 提供API Service
 */
object RetrofitClient {


    val okHttpClient = OkHttpClient.Builder().connectTimeout(20,TimeUnit.SECONDS).build()

    val retrofit = Retrofit.Builder()
        .baseUrl(ApiService.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    val apiService = retrofit.create(ApiService::class.java)



}