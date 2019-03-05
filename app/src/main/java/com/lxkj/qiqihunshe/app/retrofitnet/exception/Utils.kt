package com.lxkj.qiqihunshe.app.retrofitnet.exception

/**
 * 页面描述：Utils
 *
 * Created by ditclear on 2017/9/26.
 */

internal object Utils {
    fun check(message: String?): Boolean = message.isNullOrEmpty()

    fun check(o: Any?): Boolean = o ==null
}
