package com.lxkj.qiqihunshe.app.rongrun.receiver

import android.content.Context
import com.lxkj.qiqihunshe.app.util.abLog
import io.rong.push.PushType
import io.rong.push.notification.PushMessageReceiver
import io.rong.push.notification.PushNotificationMessage

/**
 * Created by Slingge on 2019/3/22
 */
class SealNotificationReceiver : PushMessageReceiver() {
    override fun onNotificationMessageClicked(p0: Context?, p1: PushType?, p2: PushNotificationMessage?): Boolean {
        abLog.e("收到消息","收到消息")
        return false
    }

    override fun onNotificationMessageArrived(p0: Context?, p1: PushType?, p2: PushNotificationMessage?): Boolean {
        return false
    }
}