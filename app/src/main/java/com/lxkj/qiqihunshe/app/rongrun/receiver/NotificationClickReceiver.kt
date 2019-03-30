package com.lxkj.qiqihunshe.app.rongrun.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import io.rong.imkit.RongIM

/**
 * 通知栏点击事件
 * Created by Slingge on 2019/3/25
 */
class NotificationClickReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        RongIM.getInstance().startPrivateChat(context, intent?.getStringExtra("id"), intent?.getStringExtra("name"))
    }
}