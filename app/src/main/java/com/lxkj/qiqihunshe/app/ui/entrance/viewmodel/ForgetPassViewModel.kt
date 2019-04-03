package com.lxkj.qiqihunshe.app.ui.entrance.viewmodel

import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.RetrofitService
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.util.TimerUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.app.util.abLog
import com.lxkj.qiqihunshe.databinding.ActivityForgetPassBinding
import io.reactivex.Single

/**
 * Created by Slingge on 2019/2/16
 */
class ForgetPassViewModel : BaseViewModel() {


    var bind: ActivityForgetPassBinding? = null

    private val timerUtil by lazy { TimerUtil(bind?.tvGetCode) }


    fun getcode(phone: String): Single<String> {
        timerUtil.startTimer()
        val json = "{\"cmd\":\"sendSms\",\"phone\":\"$phone\"}"
        return retrofit.getData(json)
            .async().compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(response: String) {
                    ToastUtil.showTopSnackBar(activity!!, "验证码已发送，请注意查收")
                }
            }, activity))
    }


    fun ForgetPass(json: String): Single<String> =
        retrofit.getData(json).async()
            .compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(response: String) {
                    ToastUtil.showTopSnackBar(activity, "密码设置成功")
                }
            }, activity))


}