package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import com.google.gson.Gson
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.ui.mine.model.ModifyPhoneModel
import com.lxkj.qiqihunshe.app.util.TimerUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.ActivityVerificationPhoneBinding
import io.reactivex.Single

/**
 * Created by Slingge on 2019/2/20
 */
class ModifyPhoneViewModel : BaseViewModel() {


    var bind: ActivityVerificationPhoneBinding? = null

    private val timerUtil by lazy { TimerUtil(bind?.tvGetcode) }


    fun getCode(phone: String): Single<String> {
        timerUtil.startTimer()
        val json = "{\"cmd\":\"sendSms\",\"phone\":\"$phone\"}"
        return retrofit.getData(json).async()
            .compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(response: String) {
                    ToastUtil.showTopSnackBar(activity, "验证码已发送，请注意查收")
                }
            }, activity))
    }

    fun motifyPhone(model: ModifyPhoneModel): Single<String> {
        timerUtil.startTimer()
        return retrofit.getData(Gson().toJson(model)).async()
            .compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(response: String) {
                    ToastUtil.showToast("修改成功")
                    activity?.finish()
                }
            }, activity))
    }


}