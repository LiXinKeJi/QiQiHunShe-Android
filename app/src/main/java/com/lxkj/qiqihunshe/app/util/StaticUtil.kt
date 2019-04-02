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
    var nickName = ""//昵称
    var age = ""

    var fill = ""// 0未完善资料 1已完善资料

    var lat = ""// 当前纬度
    var lng = ""//当前经度
    var address = "" //当前位置

    var amount = ""//账户总额

    var rytoken = ""//融云token

    val Beecloud_Appid = "b66edf2d-f7c3-480f-83de-0339007bded0"
    val Beecloud_AppSecret = "17dc426a-120b-47b9-a788-ddeb094542b4"
    val Weixin_Appid = "wx56f7adc719a7d5cd"
    val Weixin_AppSecret = "ecdf8ad6bd9d50d9575b6918a31f00a1"


    var isReal = ""//是否实名认证,  0未认证 1待审核 2已认证 3认证失败


}