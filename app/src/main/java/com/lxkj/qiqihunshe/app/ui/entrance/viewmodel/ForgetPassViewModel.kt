package com.lxkj.qiqihunshe.app.ui.entrance.viewmodel

import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.util.TimerUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.ActivityForgetPassBinding

/**
 * Created by Slingge on 2019/2/16
 */
class ForgetPassViewModel : BaseViewModel() {


    var bind: ActivityForgetPassBinding? = null

    private val timerUtil by lazy { TimerUtil(bind?.tvGetCode) }

    private var CODE = ""

    fun getCode() {
        CODE = timerUtil.num
        timerUtil.startTimer()
        ToastUtil.showTopSnackBar(activity, CODE)
    }


}