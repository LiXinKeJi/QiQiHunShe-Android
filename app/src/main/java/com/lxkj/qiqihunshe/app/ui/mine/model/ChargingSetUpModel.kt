package com.lxkj.qiqihunshe.app.ui.mine.model

import android.databinding.Bindable
import com.lxkj.qiqihunshe.BR
import com.lxkj.qiqihunshe.app.base.BaseModel
import com.lxkj.qiqihunshe.app.util.StaticUtil

/**
 * Created by Slingge on 2019/2/25
 */
class ChargingSetUpModel:BaseModel(){

    val cmd="updateCaiyiFee"
    val uid=StaticUtil.uid


    @Bindable
    var voice=""
    @Bindable
    var video=""


    fun noti(){
        notifyPropertyChanged(BR.voice)
        notifyPropertyChanged(BR.video)
    }
}