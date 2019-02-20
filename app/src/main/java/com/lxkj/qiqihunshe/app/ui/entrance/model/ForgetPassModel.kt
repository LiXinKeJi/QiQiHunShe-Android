package com.lxkj.qiqihunshe.app.ui.entrance.model

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.lxkj.qiqihunshe.BR

/**
 * Created by Slingge on 2019/2/16
 */
class ForgetPassModel : BaseObservable() {

    @Bindable
    var phone = ""
    @Bindable
    var pass = ""

    @Bindable
    var code = ""


    //刷新双向绑定数据
    fun notif() {
        notifyPropertyChanged(BR.phone)
        notifyPropertyChanged(BR.pass)
        notifyPropertyChanged(BR.code)
    }

}