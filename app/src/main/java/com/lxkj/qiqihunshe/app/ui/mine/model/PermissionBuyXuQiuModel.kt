package com.lxkj.qiqihunshe.app.ui.mine.model

import android.databinding.Bindable
import com.lxkj.qiqihunshe.BR
import com.lxkj.qiqihunshe.app.base.BaseModel
import com.lxkj.qiqihunshe.app.util.StaticUtil

/**
 * Created by Slingge on 2019/3/19
 */
class PermissionBuyXuQiuModel : BaseModel() {

    var type:String=""

    val cmd = "buyPermission"
    val uid = StaticUtil.uid

    var birthplace = "请选择"
    var residence = "请选择"

    @Bindable
    var age = ""// 年龄
    @Bindable
    var salary = ""// 收入

    var car = "0"// 车 0无 1有
    var house = "0"// 房 0无 1有

    var education = "请选择"// 学历


    var content = ""// 自定义需求


    fun noti(){
        notifyPropertyChanged(BR.age)
        notifyPropertyChanged(BR.salary)
    }

}