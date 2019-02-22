package com.lxkj.qiqihunshe.app.ui.mine.model

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.lxkj.qiqihunshe.BR

/**
 * Created by Slingge on 2019/2/22
 */
class RechargeModel : BaseObservable() {

    @Bindable
    var money = ""


    fun notifyMoney(){
        notifyPropertyChanged(BR.money)
    }


}