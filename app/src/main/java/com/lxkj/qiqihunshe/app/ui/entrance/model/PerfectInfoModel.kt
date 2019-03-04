package com.lxkj.qiqihunshe.app.ui.entrance.model

import android.databinding.Bindable
import com.lxkj.qiqihunshe.BR
import com.lxkj.qiqihunshe.app.base.BaseModel
import com.lxkj.qiqihunshe.app.util.StaticUtil

/**
 * Created by Slingge on 2019/2/19
 */
class PerfectInfoModel : BaseModel() {

    val cmd = "fillOut"

    val uid = StaticUtil.uid


    var icon = ""//头像(多张图片以”｜”分隔）

    @Bindable
    var nickname = ""
    @Bindable
    var realname = ""
    var sex = ""// 0女 1男
    var birthday = ""
    var birthplace = ""
    var residence = ""
    var nation = ""
    @Bindable
    var job = ""

    @Bindable
    var height = ""
    var education = ""
    var marriage = ""//情感状态 0未婚 1已婚 2离异
    @Bindable
    var introduction = ""//个性签名
    var plan = ""
    var type = ""

    var interest = ""//兴趣爱好(多个以”,”分隔)
    var locale = ""

    var zeou = ""//择偶开关 0关 1开

    var type2 = ""//他的类型
    var birthplace2 = ""
    var residence2 = ""
    var height2 = ""
    var marriage2 = ""//他的情感状态 0未婚 1已婚 2离异
    var salary2 = ""//他的薪资
    var car2 = ""//车辆价格 0代表无车
    var house2 = ""//房屋面积 0代表无房
    var education2 = ""//他的学历
    var plan2 = ""//他的情感计划


    fun noti() {
        notifyPropertyChanged(BR.nickname)
        notifyPropertyChanged(BR.realname)
        notifyPropertyChanged(BR.introduction)
        notifyPropertyChanged(BR.height)
        notifyPropertyChanged(BR.job)
    }

}