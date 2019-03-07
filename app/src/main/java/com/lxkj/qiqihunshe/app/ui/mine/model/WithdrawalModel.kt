package com.lxkj.qiqihunshe.app.ui.mine.model

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.lxkj.qiqihunshe.BR
import com.lxkj.qiqihunshe.app.util.StaticUtil

/**
 * Created by Slingge on 2019/2/22
 */
class WithdrawalModel : BaseObservable() {

    var cmd = "cash"
    var uid = StaticUtil.uid

    @Bindable
    var amount = ""

    var type = "1"//目前只有一个支付宝

    @Bindable
    var account = ""

    @Bindable
    var realname = ""

    fun nitifyMoney() {
        notifyPropertyChanged(BR.amount)
        notifyPropertyChanged(BR.account)
        notifyPropertyChanged(BR.realname)
    }


}