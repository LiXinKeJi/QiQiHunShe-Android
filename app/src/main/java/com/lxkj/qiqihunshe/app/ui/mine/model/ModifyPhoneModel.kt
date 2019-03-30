package com.lxkj.qiqihunshe.app.ui.mine.model

import android.databinding.Bindable
import com.lxkj.qiqihunshe.BR
import com.lxkj.qiqihunshe.app.base.BaseModel
import com.lxkj.qiqihunshe.app.util.StaticUtil

/**
 * Created by Slingge on 2019/3/19
 */
class ModifyPhoneModel : BaseModel() {

    val cmd = "changePhone"

    val uid = StaticUtil.uid

    var answer = ""

    @Bindable
    var newPhone = ""
    @Bindable
    var validate = ""

    fun noify() {
        notifyPropertyChanged(BR.newPhone)
        notifyPropertyChanged(BR.validate)
    }

}