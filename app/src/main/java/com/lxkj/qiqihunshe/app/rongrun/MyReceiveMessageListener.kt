package com.lxkj.qiqihunshe.app.rongrun

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.rongrun.receiver.NotificationClickReceiver
import com.lxkj.qiqihunshe.app.ui.fujin.activity.ChatActivity
import com.lxkj.qiqihunshe.app.util.abLog
import io.rong.imkit.RongIM
import io.rong.imlib.RongIMClient
import io.rong.imlib.model.Message

/**
 * Created by Slingge on 2019/3/22
 */
class MyReceiveMessageListener(val context: Context) : RongIMClient.OnReceiveMessageListener,
    RongIMClient.ConnectionStatusListener, RongIM.OnSendMessageListener {
    override fun onChanged(p0: RongIMClient.ConnectionStatusListener.ConnectionStatus?) {

    }

    override fun onSend(p0: Message?): Message {
        return p0!!
    }

    override fun onSent(p0: Message?, p1: RongIM.SentMessageErrorCode?): Boolean {
        return false
    }

    override fun onReceived(p0: Message?, p1: Int): Boolean {
        p0?.let {
            abLog.e("targetId", it.targetId)
            abLog.e("objectName", it.objectName)
        }
        if (Build.VERSION.SDK_INT >= 26) {
            initNotification26()
        } else {
            showNoticeBar()
        }

        return false
    }


    private fun showNoticeBar() {
        val builder = NotificationCompat.Builder(context)
        val intent = Intent(context, NotificationClickReceiver::class.java)
        intent.putExtra("id","")
        intent.putExtra("name","")
        val manager = context
            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        builder.setContentText("查看新联系人").setSmallIcon(R.mipmap.ic_launcher).setContentTitle("有新联系人")
        builder.setDefaults(Notification.DEFAULT_ALL).setContentIntent(pendingIntent)
        builder.setAutoCancel(true)
        builder.priority = Notification.DEFAULT_ALL
        manager.notify(0, builder.build())
    }


    private var notificationManager: NotificationManager? = null
    //7.0通知栏
//初始化通知栏
    @RequiresApi(api = Build.VERSION_CODES.O)
    fun initNotificationChannel() {
        notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager!!.createNotificationChannelGroup(NotificationChannelGroup("a", "a"))

        @SuppressLint("WrongConstant") val channel = NotificationChannel(
            ChannelId,
            "Channel1", NotificationManager.IMPORTANCE_MAX
        )//通知优先级
        channel.enableLights(false)// 设置通知出现时的闪灯（如果 android 设备支持的话）
        channel.lightColor = Color.GREEN
        channel.setShowBadge(false)//角标
//        channel.setSound(null, null)
//        channel.vibrationPattern = null

        notificationManager!!.createNotificationChannel(channel)
    }

    /**
     * 通知管理对象
     */
    private var mNotificationManager: NotificationManager? = null
    private val ChannelId = "2"
    private var mBuilder26: NotificationCompat.Builder? = null
    private var hangPendingIntent: PendingIntent? = null
    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun initNotification26() {
        if (hangPendingIntent == null) {
            val intent = Intent(context, NotificationClickReceiver::class.java)
            intent.putExtra("id","")
            intent.putExtra("name","")
            hangPendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)
        }

        if (mBuilder26 == null) {
            mBuilder26 = NotificationCompat.Builder(context, ChannelId)

        }
        mBuilder26!!.setSmallIcon(R.mipmap.ic_launcher)
            .setContentText("有新消息")
            .setAutoCancel(true)
            .setContentIntent(hangPendingIntent)
            .setContentTitle("Title")

        if (mNotificationManager == null) {
            mNotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        }
        mNotificationManager!!.notify(0, mBuilder26!!.build())
    }


    init {
        initDefaultListener()
        if (Build.VERSION.SDK_INT >= 26) {
            initNotificationChannel()
        }
    }


    private fun initDefaultListener() {
//        RongIM.getInstance().setonrec(this);//设置消息接收监听器
        RongIM.getInstance().setSendMessageListener(this)
        RongIM.setConnectionStatusListener(this)
    }


}