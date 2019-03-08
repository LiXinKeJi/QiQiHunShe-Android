package com.lxkj.qiqihunshe.app.util

import android.os.Environment

/**
 * Created by Slingge on 2019/2/20
 */
object StaticUtil {


    val APKPath = Environment.getExternalStorageDirectory().path + "/Download/Earn.apk"


    var uid = ""//用户id
    var sex = ""//用户性别  0女 1男
    var headerUrl = ""//用户自己的头像
    var nickName=""//昵称
    var age=""

    var fill = ""// 0未完善资料 1已完善资料

    var lat = "35.155"// 当前纬度
    var lng = "125.45549"//当前经度
    var address = "" //当前位置

    var amount=""//账户总额
}