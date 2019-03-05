package com.lxkj.qiqihunshe.app.ui.entrance.viewmodel

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.RetrofitService
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.ui.entrance.model.SignInModel
import com.lxkj.qiqihunshe.app.util.TimerUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.ActivityRegisterBinding
import io.reactivex.Single

/**
 * Created by Slingge on 2019/2/16
 */
class RegisterViewModel  : BaseViewModel() {


    var bind: ActivityRegisterBinding? = null

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


    fun register(json: String): Single<String> = retrofit.getData(json)
        .async().compose(SingleCompose.compose(object : SingleObserverInterface {
            override fun onSuccess(response: String) {
                ToastUtil.showTopSnackBar(activity, "注册成功")
                activity?.finish()
            }
        }, activity))


}