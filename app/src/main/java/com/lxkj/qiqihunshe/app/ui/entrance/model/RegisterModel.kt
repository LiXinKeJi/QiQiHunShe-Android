package com.lxkj.qiqihunshe.app.ui.entrance.model

import android.databinding.Bindable
import com.lxkj.qiqihunshe.BR
import com.lxkj.qiqihunshe.app.base.BaseModel

/**
 * Created by Slingge on 2019/2/16
 */
class RegisterModel : BaseModel() {

    @Bindable
    var phone = ""
    @Bindable
    var pass = ""

    @Bindable
    var code = ""

    @Bindable
    var problem = ""
    @Bindable
    var answer = ""//答案

    //刷新双向绑定数据
     fun notif() {
        notifyPropertyChanged(BR.phone)
        notifyPropertyChanged(BR.pass)
        notifyPropertyChanged(BR.code)
        notifyPropertyChanged(BR.problem)
        notifyPropertyChanged(BR.answer)
    }

}