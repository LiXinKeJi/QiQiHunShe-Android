package com.lxkj.qiqihunshe.app.retrofitnet.exception

import com.lxkj.qiqihunshe.app.retrofitnet.RetrofitService
import com.lxkj.qiqihunshe.app.retrofitnet.RetrofitUtil

/**
 * 页面描述：Utils
 *
 * Created by ditclear on 2017/9/26.
 */

internal object Utils {
    fun check(message: String?): Boolean = message.isNullOrEmpty()

    fun check(o: Any?): Boolean = o ==null


    val retrofit by lazy { RetrofitUtil.getRetrofit().create(RetrofitService::class.java) }

}
