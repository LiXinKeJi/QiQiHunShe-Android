package com.lxkj.qiqihunshe.app.rongrun

import android.os.IBinder
import io.rong.imkit.RongIM
import io.rong.imlib.OnReceiveMessageListener
import io.rong.imlib.RongIMClient
import io.rong.imlib.model.Message

/**
 * Created by Slingge on 2019/3/22
 */
object RongCloudEvent : RongIMClient.ConnectionStatusListener, RongIM.OnSendMessageListener {

    fun init() {
        RongIM.getInstance().setOnReceiveUnreadCountChangedListener(object : OnReceiveMessageListener,
            RongIM.OnReceiveUnreadCountChangedListener {
            override fun onMessageIncreased(p0: Int) {

            }

            override fun onReceived(p0: Message?, p1: Int, p2: Boolean, p3: Boolean, p4: Int): Boolean {
                return false
            }

            override fun asBinder(): IBinder? {
                return null
            }

        })//设置消息接收监听器
        RongIM.getInstance().setSendMessageListener(this)
        RongIM.setConnectionStatusListener(this)
    }


    override fun onChanged(p0: RongIMClient.ConnectionStatusListener.ConnectionStatus?) {

    }

    override fun onSend(p0: Message?): Message {
        return p0!!
    }

    override fun onSent(p0: Message?, p1: RongIM.SentMessageErrorCode?): Boolean {
        return false
    }


}

