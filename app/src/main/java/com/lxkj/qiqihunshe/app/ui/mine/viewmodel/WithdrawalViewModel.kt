package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.databinding.ObservableField
import android.view.Gravity
import com.google.gson.Gson
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.ui.dialog.StringSelectPop
import com.lxkj.qiqihunshe.app.ui.mine.model.WithdrawalModel
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.app.util.abLog
import com.lxkj.qiqihunshe.databinding.ActivityWithdrawalBinding
import io.reactivex.Single
import org.json.JSONObject
import java.util.ArrayList

/**
 * Created by Slingge on 2019/2/22
 */
class WithdrawalViewModel : BaseViewModel(), StringSelectPop.StringCallBack {


    //提现费率
    val rate = ObservableField<String>()

    var bind: ActivityWithdrawalBinding? = null

    //获取佣金比例,1提现
    fun getProportion(): Single<String> {
        val json = "{\"cmd\":\"proportion\",\"type\":\"" + "1" + "\"}"
        return retrofit.getData(json).async().compose(SingleCompose.compose(object : SingleObserverInterface {
            override fun onSuccess(response: String) {
                val obj = JSONObject(response)
                rate.set("注：本次提现平台需扣取佣金：" + obj.getString("point"))
            }
        }, activity))
    }


    private var stringPop: StringSelectPop? = null
    private val list by lazy { ArrayList<String>() }
    //选择状态
    fun selectState() {
        if (list.isEmpty()) {
            list.addAll(fragment!!.context!!.resources.getStringArray(R.array.payType))
        }
        stringPop = StringSelectPop(activity, list, this)

        if (!stringPop!!.isShowing) {
            stringPop!!.showAtLocation(bind?.clMain, Gravity.CENTER or Gravity.BOTTOM, 0, 0)
        }
    }

    // 支付类型
    override fun position(position1: Int) {

    }

    fun Withdrawal(model: WithdrawalModel): Single<String> {
        abLog.e("提现", Gson().toJson(model))
        return retrofit.getData(Gson().toJson(model)).async()
            .compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(response: String) {
                    ToastUtil.showToast("提现申请成功")
                    activity?.finish()
                }
            }, activity))
    }


}