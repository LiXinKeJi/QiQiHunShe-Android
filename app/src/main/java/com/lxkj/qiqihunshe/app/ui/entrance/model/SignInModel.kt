package com.lxkj.qiqihunshe.app.ui.entrance.model

import android.databinding.Bindable
import com.lxkj.qiqihunshe.BR
import com.lxkj.qiqihunshe.app.base.BaseModel

/**
 * Created by Slingge on 2019/2/16
 */
class SignInModel : BaseModel() {

    @Bindable
    var phone = "15903691791"
    @Bindable
    var pass = "123456"


    //刷新双向绑定数据
    fun notif() {
        notifyPropertyChanged(BR.phone)
        notifyPropertyChanged(BR.pass)
    }


    var uid=""
    var fill=""// 0未完善资料 1已完善资料

}