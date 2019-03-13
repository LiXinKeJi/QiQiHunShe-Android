package com.lxkj.qiqihunshe.app.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.text.TextUtils
import com.lxkj.qiqihunshe.app.util.*
import io.rong.calllib.RongCallClient
import java.util.*

/**
 * 通话监听服务
 * Created by Slingge on 2019/3/13
 */
class CallKitService : Service() {


    interface CallKitEndCallBack {
        fun callEnd(count: String, time: String)
    }

    private var callKitEndCallBack: CallKitEndCallBack? = null
    fun sCallKitEndCallBac(callKitEndCallBack: CallKitEndCallBack) {
        this.callKitEndCallBack = callKitEndCallBack
    }

    override fun onBind(intent: Intent): IBinder? {
        return Binder()
    }

    inner class Binder : android.os.Binder() {
        val service: CallKitService
            get() = this@CallKitService
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val price = intent!!.getStringExtra("price")//每分钟价格
        val min = StaticUtil.amount.toDouble() / price.toDouble()

        var adtime = ""//接通时间
        abLog.e("通话计时", "服务开启")
        var count = -1
        val timer = Timer()
        val task = object : TimerTask() {
            override fun run() {
                if (RongCallClient.getInstance().callSession != null && RongCallClient.getInstance().callSession.activeTime != 0.toLong()) {
                    //通话中
                    count++
                    if (count.toDouble() == min) {
                        ThreadUtil.runOnMainThread(Runnable {
                            ToastUtil.showToast("您的余额即将用完")
                            return@Runnable
                        })
                    } else if (count.toDouble() > min) {
                        ThreadUtil.runOnMainThread(Runnable {
                            ToastUtil.showToast("您的余额已用完")
                            callKitEndCallBack?.callEnd((count / 60).toString(), adtime)
                            count = -1
                            adtime = ""
                            stopSelf()
                            return@Runnable
                        })
                    }
                    if (TextUtils.isEmpty(adtime)) {
                        adtime = GetDateTimeUtil.getYMDHMS()
                    }
                    abLog.e("通话计时", count.toString())
                } else {
                    if (count != -1) {//通话结束
                        callKitEndCallBack?.callEnd((count / 60).toString(), adtime)
                        count = -1
                        adtime = ""
                        stopSelf()
                    }
                }
            }
        }
        timer.schedule(task, 1000, 1000)

        return super.onStartCommand(intent, flags, startId)
    }


}