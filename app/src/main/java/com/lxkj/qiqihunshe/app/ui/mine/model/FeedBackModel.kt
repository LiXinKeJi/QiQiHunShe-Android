package com.lxkj.qiqihunshe.app.ui.mine.model

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.lxkj.qiqihunshe.BR

/**
 * Created by Slingge on 2019/2/20
 */
class FeedBackModel : BaseObservable() {

    @Bindable
    var content = ""


    fun noify() {
        notifyPropertyChanged(BR.content)
    }


}