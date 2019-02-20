package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.databinding.Bindable
import com.lxkj.qiqihunshe.BR
import com.lxkj.qiqihunshe.app.base.BaseViewModel

/**
 * Created by Slingge on 2019/2/20
 */
 class AnswerProblemViewModel:BaseViewModel(){


    @Bindable
    var answer=""

    fun  notifys(){
        notifyPropertyChanged(BR.answer)

    }



}