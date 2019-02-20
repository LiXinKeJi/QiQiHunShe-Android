package com.lxkj.qiqihunshe.app.util

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat


/**
 * Created by Slingge on 2019/2/13
 */
@SuppressLint("StaticFieldLeak")
object NotificationUtil {


    var context: Context? = null

    private val NotificationId = 0

    private var nm: NotificationManager? = null
    var notification: Notification? = null
    @RequiresApi(Build.VERSION_CODES.O)
    fun Notification(progress: Int) {
        if (nm == null) {
            nm = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
        }
        var notificationChannelId: String? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = createNotificationChannel()
            nm?.createNotificationChannel(notificationChannel)
            notificationChannelId = notificationChannel.id
        }

        notification = NotificationCompat.Builder(context!!, notificationChannelId!!)
                .setSmallIcon(context?.applicationInfo!!.icon)
                .setWhen(System.currentTimeMillis())
                .setContentTitle("正在更新...")
                .setContentText("已下载：$progress%")
                .setTicker("ticker")
                .setProgress(100, progress, false)
                .setAutoCancel(true)
                .build()

        nm?.createNotificationChannel(createNotificationChannel())


        if (progress == 100) {
            nm?.cancel(NotificationId)
            return
        }


        try {
            nm?.notify(NotificationId, notification)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    val channelId = "test"
    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun createNotificationChannel(): NotificationChannel {
        val pattern = longArrayOf(0, 0, 0)
        var channel: NotificationChannel? = null
        channel = NotificationChannel(channelId,
                "Channel1", NotificationManager.IMPORTANCE_LOW)
        channel.enableLights(true) //是否在桌面icon右上角展示小红点
        channel.lightColor = Color.RED //小红点颜色
        channel.setShowBadge(true) //是否在久按桌面图标时显示此渠道的通知
        channel.vibrationPattern=pattern
        return channel
    }


}