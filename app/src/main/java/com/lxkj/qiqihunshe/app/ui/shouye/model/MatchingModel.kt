package com.lxkj.qiqihunshe.app.ui.shouye.model

import com.lxkj.qiqihunshe.app.util.StaticUtil
import java.io.Serializable

/**
 * Created by Slingge on 2019/3/19
 */
class MatchingModel :Serializable{


    var cmd = ""
    val uid = StaticUtil.uid
    var type = ""//1聊 2语
    var distance = ""//距离 1-10 单位km
    var age = ""//年龄 18-30


    var marriage = ""// 0全部 1未婚 2离异
    var birthplace = ""//籍贯


    var flag=0//哪一个fragment
}