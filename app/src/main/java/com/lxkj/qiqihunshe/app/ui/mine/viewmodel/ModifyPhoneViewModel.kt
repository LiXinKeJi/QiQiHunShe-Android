package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import com.google.gson.Gson
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.ui.mine.model.ModifyPhoneModel
import com.lxkj.qiqihunshe.app.util.TimerUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.app.util.abLog
import com.lxkj.qiqihunshe.databinding.ActivityModifyPhoneBinding
import io.reactivex.Single

/**
 * Created by Slingge on 2019/2/20
 */
class ModifyPhoneViewModel : BaseViewModel() {

    lateinit var bind: ActivityModifyPhoneBinding

    lateinit var timerUtil: TimerUtil


    fun init() {
        timerUtil = TimerUtil(bind.tvGetcode)
    }

    fun getCode(phone: String): Single<String> {

        val json = "{\"cmd\":\"sendSms\",\"phone\":\"$phone\"}"
        return retrofit.getData(json).async()
            .compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(response: String) {
                    timerUtil.startTimer()
                    ToastUtil.showTopSnackBar(activity, "验证码已发送，请注意查收")
                }
            }, activity))
    }

    fun motifyPhone(model: ModifyPhoneModel): Single<String> {
        abLog.e("修改手机号", Gson().toJson(model))
        return retrofit.getData(Gson().toJson(model)).async()
            .compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(response: String) {
                    ToastUtil.showToast("修改成功")
                    activity?.finish()
                }
            }, activity))
    }


}