package com.example.mvvmdemo.api

import java.lang.RuntimeException

/**
 *  Created by fangfuming
 *  2020/10/24
 */
data class ApiException(val code:Int, override val message:String?):RuntimeException() {
}