package com.lxkj.qiqihunshe.app.rongrun

import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil
import io.rong.imlib.RongIMClient

/**
 * Created by Slingge on 2019/3/12
 */
object RongYunUtil {


    //连接融云服务器
    fun initService() {
        RongIMClient.connect(StaticUtil.rytoken, object : RongIMClient.ConnectCallback() {
            override fun onSuccess(p0: String?) {
            }

            override fun onError(p0: RongIMClient.ErrorCode?) {
            }

            override fun onTokenIncorrect() {
            }
        })
    }


}