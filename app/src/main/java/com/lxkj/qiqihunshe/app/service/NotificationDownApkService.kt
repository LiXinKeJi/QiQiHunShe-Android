package com.lxkj.qiqihunshe.app.service

import android.app.Notification
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.IBinder
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.util.NotificationUtil
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.app.util.ThreadUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.orhanobut.logger.Logger
import okhttp3.*
import org.greenrobot.eventbus.EventBus
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

/**
 * 下载apk服务
 * Created by Slingge on 2019/2/13
 */
class NotificationDownApkService : Service() {

    override fun onCreate() {
        super.onCreate()
        NotificationUtil.context = this
    }


    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        downApk(intent!!.getStringExtra("url"))
        return super.onStartCommand(intent, flags, startId)
    }


    private var apkFile: File? = null
    fun downApk(url: String) {
        val client = OkHttpClient()
        val request = Request.Builder()
//            .url("http://yuedingle.com/icon/yueding.apk")
            .url(url)
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                EventBus.getDefault().post("")
            }

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResponse(call: Call, response: Response) {
                val insteam = response.body()?.byteStream()//获取输入流
                val total = response.body()?.contentLength()!! / 100//获取文件大小

                if (total.toInt() == -1) {
                    ThreadUtil.runOnMainThread(Runnable {
                        ToastUtil.showToast("获取文件失败")
                    })
                    return
                }
                if (insteam != null) {
                    apkFile = File(StaticUtil.APKPath)// 设置路径
                    if (apkFile!!.exists()) {
                        apkFile?.delete()
                    }
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                        initNotification()
                    }

                    val fos = FileOutputStream(apkFile)
                    val buf = ByteArray(1024)
                    var ch = -1
                    var process: Long = 0
                    while (insteam.read(buf).apply { ch = this } > 0) {
                        fos.write(buf, 0, ch)
                        process += ch.toLong() / 100//实时进度
                        //这里就是关键的实时更新进度了！
                        Logger.e("下载进度：\n $process , $total  " + ":" + (process.toDouble() / total))
//
                        val progress = ((process.toDouble() / total) * 100).toInt()
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            NotificationUtil.Notification(progress)
                        } else {
                            builder?.setContentText("下载进度:$progress%")
                            builder?.setProgress(100, progress, false)
                            notificationManager?.notify(1, builder?.build())
                        }

                    }
                    fos.flush()
                    fos.close()
                    insteam.close()
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        NotificationUtil.Notification(100)
                    } else {
                        notificationManager?.cancel(1)
                    }
                    installApk()
                } else {
                    ThreadUtil.runOnMainThread(Runnable {
                        ToastUtil.showToast("获取文件失败")
                    })
                }
            }
        })
    }


    private var notificationManager: NotificationManager? = null
    private var builder: NotificationCompat.Builder? = null
    private var notification: Notification? = null
    private fun initNotification() {
        notificationManager =
            MyApplication.getInstance().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
        builder = NotificationCompat.Builder(MyApplication.getInstance())
        builder?.let {
            it.setContentTitle("正在更新...") //设置通知标题
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(
                    BitmapFactory.decodeResource(
                        MyApplication.getInstance().resources,
                        R.mipmap.ic_launcher
                    )
                ) //设置通知的大图标
                .setDefaults(Notification.DEFAULT_LIGHTS)
                .priority = NotificationCompat.PRIORITY_MAX //设置通知的优先级：最大
        }
        builder?.setAutoCancel(false)
        builder?.setContentText("已下载：" + "0%")
        builder?.setProgress(100, 0, false)

        notification = builder?.build()//构建通知对象
    }


    private fun installApk() {
        EventBus.getDefault().post("permission")
        stopSelf()
    }


}