package com.lxkj.qiqihunshe.app.ui.mine.model

import android.databinding.Bindable
import com.lxkj.qiqihunshe.BR
import com.lxkj.qiqihunshe.app.base.BaseModel

/**
 * Created by Slingge on 2019/2/25
 */
class ChargingSetUpModel:BaseModel(){


    @Bindable
    var yuyinFei=""
    @Bindable
    var shipinFei=""


    fun noti(){
        notifyPropertyChanged(BR.yuyinFei)
        notifyPropertyChanged(BR.shipinFei)
    }
}