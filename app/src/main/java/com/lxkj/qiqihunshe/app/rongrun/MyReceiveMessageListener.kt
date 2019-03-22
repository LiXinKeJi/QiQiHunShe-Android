package com.lxkj.qiqihunshe.app.rongrun

import com.lxkj.qiqihunshe.app.util.abLog
import io.rong.imlib.RongIMClient
import io.rong.imlib.model.Message

/**
 * Created by Slingge on 2019/3/22
 */
class MyReceiveMessageListener : RongIMClient.OnReceiveMessageListener {
    override fun onReceived(p0: Message?, p1: Int): Boolean {
        p0?.let {
            abLog.e("targetId", it.targetId)
            abLog.e("objectName",it.objectName)
        }

        return false
    }
}