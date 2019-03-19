package com.lxkj.qiqihunshe.app.ui.mine.model

import com.lxkj.qiqihunshe.app.base.BaseModel
import com.lxkj.qiqihunshe.app.util.StaticUtil

/**
 * Created by Slingge on 2019/3/19
 */
class PermissionBuyXuQiuModel(var type:String) : BaseModel() {

    val cmd = "buyPermission"
    val uid = StaticUtil.uid

    var birthplace = ""
    var residence = ""

    var age = ""
    var salary = ""

    var car = ""
    var house = ""

    var education = ""
    var content = ""
}