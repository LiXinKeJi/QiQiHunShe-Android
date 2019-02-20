package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.databinding.Bindable
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.util.TimerUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.ActivityVerificationPhoneBinding

/**
 * Created by Slingge on 2019/2/20
 */
class ModifyPhoneViewModel:BaseViewModel(){

    @Bindable
    var phone=""
    @Bindable
    var code=""


    var bind: ActivityVerificationPhoneBinding? = null

    private val timerUtil by lazy { TimerUtil(bind?.tvGetcode) }

    private var CODE = ""

    fun getCode() {
        CODE = timerUtil.num
        timerUtil.startTimer()
        ToastUtil.showTopSnackBar(activity, CODE)
    }



}