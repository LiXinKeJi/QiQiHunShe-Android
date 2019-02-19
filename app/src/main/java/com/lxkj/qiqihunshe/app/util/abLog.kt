package com.lxkj.qiqihunshe.app.util

import android.util.Log
import com.lxkj.qiqihunshe.app.MyApplication
import com.orhanobut.logger.Logger

/**
 * Created by Slingge on 2018/1/3 0003.
 */

object abLog {

    var E: Boolean? = true

    fun e(tag: String, text: String) {
        if (E!!) {
            if (text.length > 4000) {
                var count = 0
                var i = 0
                while (i < text.length) {
                    count++
                    if (i + 4000 < text.length) {
                        Log.e(tag + count, text.substring(i, i + 4000))
                    } else {
                        Log.e(tag + count, text.substring(i, text.length))
                    }
                    i += 4000
                }
            } else {
                Logger.e(tag + "\n" + text)
            }
        }
    }

    fun e2(text: String) {
        if (E!!) {
            if (text.length > 4000) {
                var count = 0
                var i = 0
                while (i < text.length) {
                    count++
                    if (i + 4000 < text.length) {
                        Log.e(MyApplication.CONTEXT.javaClass.simpleName + count, text.substring(i, i + 4000))
                    } else {
                        Log.e(MyApplication.CONTEXT.javaClass.simpleName + count, text.substring(i, text.length))
                    }
                    i += 4000
                }
            } else
                Logger.e(MyApplication.CONTEXT.javaClass.simpleName + "\n" + text)
        }
    }
}