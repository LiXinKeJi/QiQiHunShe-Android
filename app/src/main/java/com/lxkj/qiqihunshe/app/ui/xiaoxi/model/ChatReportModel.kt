package com.lxkj.qiqihunshe.app.ui.xiaoxi.model

import com.lxkj.qiqihunshe.app.base.BaseModel
import com.lxkj.qiqihunshe.app.util.StaticUtil

/**
 * Created by Slingge on 2019/3/14
 */
class ChatReportModel : BaseModel() {

    val cmd = "liaotianjubao"
    val uid = StaticUtil.uid
    var taid = ""//对方ID
    var content = ""
    var images = ""
}