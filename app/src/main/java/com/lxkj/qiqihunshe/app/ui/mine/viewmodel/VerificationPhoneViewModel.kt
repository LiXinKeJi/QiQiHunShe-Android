package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.os.Bundle
import android.text.TextUtils
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.ui.entrance.model.ForgetPassModel
import com.lxkj.qiqihunshe.app.ui.mine.activity.ModifyPassActivity
import com.lxkj.qiqihunshe.app.util.TimerUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.ActivityVerificationPhoneBinding
import io.reactivex.Single

/**
 * Created by Slingge on 2019/2/20
 */
class VerificationPhoneViewModel : BaseViewModel() {

    var bind: ActivityVerificationPhoneBinding? = null

    val model by lazy { ForgetPassModel() }

    private val timerUtil by lazy { TimerUtil(bind?.tvGetcode) }

    var flag = 0//1登录密码,2支付密码

    fun getCode(): Single<String> {
        val json = "{\"cmd\":\"sendSms\",\"phone\":\"" + model.phone + "\"}"
        timerUtil.startTimer()
        return retrofit.getData(json).async()
            .compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(response: String) {
                    ToastUtil.showTopSnackBar(activity, "验证码已发送，请注意查收")
                }
            }, activity))
    }


    fun jump() {
        val bundle = Bundle()
        bundle.putInt("flag", flag)
        bundle.putString("phone", model.phone)
        bundle.putString("code", model.code)
        MyApplication.openActivity(activity, ModifyPassActivity::class.java, bundle)
    }


}