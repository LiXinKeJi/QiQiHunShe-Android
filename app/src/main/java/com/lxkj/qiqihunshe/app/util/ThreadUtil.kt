package com.lxkj.qiqihunshe.app.util

import android.os.Handler
import android.os.Looper

/**
 * Create Slingge by 2018/12/3 0003
 */
object ThreadUtil {


    val handler = Handler(Looper.getMainLooper())

    fun runOnMainThread(runnable: Runnable) {
        handler.post(runnable)
    }

}