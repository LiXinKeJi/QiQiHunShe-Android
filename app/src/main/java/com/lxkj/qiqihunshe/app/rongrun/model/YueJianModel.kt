package com.lxkj.qiqihunshe.app.rongrun.model

import com.lxkj.qiqihunshe.app.util.StaticUtil

/**
 * Created by Slingge on 2019/3/16
 */
class YueJianModel {

    val cmd="addYuejian"
    val uid=StaticUtil.uid

    var taid=""//对方id

    var address=""

    var lon=""
    var lat=""

    var arrivaltime=""//到场时间
}