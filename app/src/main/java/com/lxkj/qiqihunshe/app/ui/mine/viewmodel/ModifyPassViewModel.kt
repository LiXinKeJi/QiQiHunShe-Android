package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.ui.mine.model.ModifyPassModel
import com.lxkj.qiqihunshe.app.util.Md5Util
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil
import io.reactivex.Single

/**
 * Created by Slingge on 2019/2/20
 */
class ModifyPassViewModel : BaseViewModel() {

    val model by lazy { ModifyPassModel() }

    var flag = 0//1登录密码，2支付密码
    var code = ""
    var phone = ""


    fun modify(): Single<String> {
        val json = "{\"cmd\":\"updatePassword\",\"uid\":\"" + StaticUtil.uid + "\",\"phone\":\"" + phone +
                "\",\"validate\":\"" + code + "\",\"type\":\"" + flag + "\",\"newPassword\":\"" + Md5Util.md5Encode(
            model.newPass
        ) + "\"}"
        return retrofit.getData(json).async().compose(SingleCompose.compose(object : SingleObserverInterface {
            override fun onSuccess(response: String) {
                ToastUtil.showToast("修改成功")
                activity!!.finish()
            }
        }, activity))
    }

}