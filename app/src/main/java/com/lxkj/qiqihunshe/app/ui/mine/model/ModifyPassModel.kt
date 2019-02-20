package com.lxkj.qiqihunshe.app.ui.mine.model

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.lxkj.qiqihunshe.BR

/**
 * Created by Slingge on 2019/2/20
 */
 class ModifyPassModel:BaseObservable(){


    @Bindable
    var oldPass=""

    @Bindable
    var newPass=""


    fun noify(){
        notifyPropertyChanged(BR.oldPass)
        notifyPropertyChanged(BR.newPass)
    }

}