package com.lxkj.qiqihunshe.app.ui.mine.model

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.lxkj.qiqihunshe.BR

/**
 * Created by Slingge on 2019/2/22
 */
class WithdrawalModel : BaseObservable() {


    @Bindable
    var money = ""


    fun nitifyMoney() {
        notifyPropertyChanged(BR.money)
    }


}