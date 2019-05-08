package com.lxkj.qiqihunshe.app.rongrun.model

import com.lxkj.qiqihunshe.app.util.StaticUtil

/**
 * type,1我方失约 2对方失约
 * Created by Slingge on 2019/3/16
 */
class ShiYueModel(var yuejianId: String, var type: String, var messageId: Int) {

    val cmd = "shiyue"
    val uid = StaticUtil.uid


}