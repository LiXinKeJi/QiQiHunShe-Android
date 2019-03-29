package com.lxkj.qiqihunshe.app.service

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.text.TextUtils

import com.baidu.location.BDLocation
import com.baidu.location.BDLocationListener
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption
import com.lxkj.qiqihunshe.app.retrofitnet.*
import com.lxkj.qiqihunshe.app.retrofitnet.exception.dispatchFailure
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.app.util.abLog
import java.util.*
import kotlin.concurrent.timerTask


/**
 * 定位
 * Created by Slingge on 2017/3/8 0008.
 */

class LocationService : Service() {

    var mLocationClient: LocationClient? = null
    var myListener: BDLocationListener = MyLocationListener()

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        mLocationClient = LocationClient(applicationContext)
        //声明LocationClient类
        initLocation()
        mLocationClient!!.registerLocationListener(myListener)
        mLocationClient!!.start()

        if (timer != null) {
            timer?.cancel()
            timer = null
        }
        timer = Timer()

        timer?.schedule(object : TimerTask() {
            override fun run() {
                val json =
                    "{\"cmd\":\"uploadCoords\",\"uid\":\"" + StaticUtil.uid + "\",\"lon\":\"" + StaticUtil.lng +
                            "\",\"lat\":\"" + StaticUtil.lat + "" + "\"}"
                RetrofitUtil.getRetrofit().create(RetrofitService::class.java).getData(json).async()
                    .doOnSuccess { abLog.e("上传用户坐标\n$json", it) }
                    .subscribe({}, { dispatchFailure(it) })
            }
        }, 1000, 300000)
        return super.onStartCommand(intent, flags, startId)
    }


    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    private var timer: Timer? = null

    inner class MyLocationListener : BDLocationListener {

        @SuppressLint("CheckResult")
        override fun onReceiveLocation(location: BDLocation?) {

            if (TextUtils.isEmpty(StaticUtil.uid)) {
                mLocationClient?.stop()
                stopSelf()
                return
            }

            if (location != null) {
                StaticUtil.lat = location.latitude.toString() + ""
                StaticUtil.lng = location.longitude.toString() + ""
            }
        }
    }

    private fun initLocation() {
        val option = LocationClientOption()
        option.locationMode = LocationClientOption.LocationMode.Hight_Accuracy
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll")
        //可选，默认gcj02，设置返回的定位结果坐标系
        val span = 50000//5分钟一次
        option.setScanSpan(span)
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true)
        //可选，设置是否需要地址信息，默认不需要
        option.isOpenGps = true
        //可选，默认false,设置是否使用gps
        option.isLocationNotify = true
        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true)
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true)
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false)
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false)
        //可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false)
        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        mLocationClient!!.locOption = option
    }

    override fun onDestroy() {
        super.onDestroy()
        mLocationClient?.let {
            it.stop()
            mLocationClient = null

            timer?.cancel()
            timer = null
        }
    }


}