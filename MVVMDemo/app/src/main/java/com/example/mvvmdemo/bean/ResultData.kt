package com.example.mvvmdemo.bean

import com.example.mvvmdemo.api.ApiException

/**
 *  Created by fangfuming
 *  2020/10/24
 */
data class ResultData<T>(val success:Boolean ,val code:Int,val message:String,val data:T) {

    fun apiData():T {
        //成功的code 就返回数据 否则抛出异常
        if (code == 10000) {
            return data
        }else{
            throw ApiException(code,message)
        }
    }
}