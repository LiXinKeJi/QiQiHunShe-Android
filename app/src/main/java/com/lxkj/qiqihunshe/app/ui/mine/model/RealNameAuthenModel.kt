package com.lxkj.qiqihunshe.app.ui.mine.model

import com.lxkj.qiqihunshe.app.base.BaseModel
import com.lxkj.qiqihunshe.app.util.StaticUtil

/**
 * Created by Slingge on 2019/3/6
 */
class RealNameAuthenModel : BaseModel() {

    val cme = "userAuth"

    val uid = StaticUtil.uid

    var video=""

    var idnumber=""
    var idcard1=""
    var idcard2=""

    var car=""
    var carimage=""

    var house=""
    var houseimage=""

    var salary=""
    var workcard=""

    var education=""
    var diploma=""

}