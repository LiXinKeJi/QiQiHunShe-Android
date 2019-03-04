package com.lxkj.qiqihunshe.app.ui.entrance.viewmodel

import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.RetrofitService
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.util.TimerUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.ActivityForgetPassBinding
import io.reactivex.Single

/**
 * Created by Slingge on 2019/2/16
 */
class ForgetPassViewModel(val retrofit: RetrofitService) : BaseViewModel() {


    var bind: ActivityForgetPassBinding? = null

    private val timerUtil by lazy { TimerUtil(bind?.tvGetCode) }


    fun getCode() {
        timerUtil.startTimer()
    }

    fun getcode(json: String): Single<String> =
        retrofit.getData(json)
            .async().compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(response: String) {
                    ToastUtil.showTopSnackBar(activity!!, "验证码发送成功，请注意查收")
                }
            }, activity))

/*
    fun afe(json: String): Single<String> =
            retrofit*/


}