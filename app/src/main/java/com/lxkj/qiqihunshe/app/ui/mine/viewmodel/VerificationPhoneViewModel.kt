package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.os.Bundle
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.ui.mine.activity.ModifyPassActivity
import com.lxkj.qiqihunshe.app.util.TimerUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.ActivityVerificationPhoneBinding

/**
 * Created by Slingge on 2019/2/20
 */
class VerificationPhoneViewModel : BaseViewModel() {

    var bind: ActivityVerificationPhoneBinding? = null

    private val timerUtil by lazy { TimerUtil(bind?.tvGetcode) }

    var CODE = ""
    private var flag = 0//1支付密码，2登录密码

    fun getCode() {
        CODE = timerUtil.num
        timerUtil.startTimer()
        ToastUtil.showTopSnackBar(activity, CODE)

        flag = activity!!.intent.getIntExtra("flag", 0)
        ToastUtil.showToast(flag.toString())
    }


    fun jump(phone:String,code:String) {
        val bundle = Bundle()
        bundle.putInt("flag", flag)
        bundle.putString("phone",phone)
        bundle.putString("code",CODE)
        MyApplication.openActivity(activity, ModifyPassActivity::class.java, bundle)
    }


}