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

    var icons = ""//头像(多张图片以”｜”分隔）

    @Bindable
    var nickname = ""
    @Bindable
    var realname = ""
    var sex = ""// 0女 1男
    var birthday = ""
    var birthplace = "请选择"
    var residence = "请选择"
    var nation = "请选择"
    @Bindable
    var job = ""

    @Bindable
    var height = ""
    var education = ""
    var marriage = ""//情感状态 0未婚 1已婚 2离异
    @Bindable
    var introduction = ""//个性签名
    var plan = "请选择"
    var type = "请选择"

    var interests = ""//兴趣爱好(多个以”,”分隔)
    var locales = ""

    var zeou = "0"//择偶开关 0关 1开

    //上传用
    var type2 = ""//他的类型
    var birthplace2 = ""//他的家乡
    var residence2 = ""//他的现居地
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


    //获取用
    var zeou_type = ""// 类型（择偶条件）
    var zeou_birthplace = ""// 家乡（择偶条件）
    var zeou_residence = ""// 现居（择偶条件）

    var zeou_height = ""// 身高（择偶条件）
    var zeou_marriage = ""// 情感状态 0未婚 1已婚 2离异（择偶条件）
    var zeou_education = ""// 学历（择偶条件）

    var zeou_salary = ""// 收入（择偶条件）
    var zeou_car = ""// 车 0无车（择偶条件）
    var zeou_house = ""// 房 0无房（择偶条件）

    var zeou_plan = ""// 情感计划（择偶条件）


    var icon = ArrayList<String>()//获取的头像
    var interest = ArrayList<String>()//获取兴趣爱好(多个以”,”分隔)
    var locale = ArrayList<String>()//获取地址

}