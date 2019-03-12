package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.os.Bundle
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.ui.mine.activity.PayActivity
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil
import io.reactivex.Single
import org.json.JSONObject

/**
 * Created by Slingge on 2019/2/22
 */
class RechargeViewModel : BaseViewModel() {


    fun recharge(money: String): Single<String> {
        val json = "{\"cmd\":\"recharge\",\"uid\":\"" + StaticUtil.uid + "\",\"amount\":\"" + money + "\"}"
        return retrofit.getData(json).async()
            .compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(response: String) {
                    val obj = JSONObject(response)
                    val bundle = Bundle()
                    bundle.putString("num", obj.getString("orderId"))
                    bundle.putDouble("money", money.toDouble())
                    bundle.putInt("flag", 1)
                    MyApplication.openActivity(activity, PayActivity::class.java, bundle)
                }
            }, activity))
    }


}