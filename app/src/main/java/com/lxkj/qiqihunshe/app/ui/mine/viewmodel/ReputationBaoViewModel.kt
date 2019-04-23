package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import com.google.gson.Gson
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.ui.dialog.BailRefundAfterDialog
import com.lxkj.qiqihunshe.app.ui.mine.activity.PayActivity
import com.lxkj.qiqihunshe.app.ui.mine.adapter.ReputationBaoAdapter
import com.lxkj.qiqihunshe.app.ui.mine.model.ReputationBaoModel
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.databinding.ActivityReputationBaoBinding
import io.reactivex.Single
import org.json.JSONObject

/**
 * Created by Slingge on 2019/2/21
 */
class ReputationBaoViewModel : BaseViewModel() {

    val adapter by lazy { ReputationBaoAdapter() }

    lateinit var bind: ActivityReputationBaoBinding
    var userId = ""
    var bail = ""// 信誉金 0代表未缴纳
    var page = 1
    var totalPage = 1

    lateinit var reputModel: ReputationBaoModel

    private var price = ""//平台信誉金

    fun getUserCredit(): Single<String> {
        val json = "{\"cmd\":\"getUserCredit\",\"uid\":\"$userId\"}"
        return retrofit.getData(json).async()
            .compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(response: String) {
                    reputModel = Gson().fromJson(response, ReputationBaoModel::class.java)
                    bind.model = reputModel
                    if (reputModel.safe.toDouble() < 12.5) {
                        bind.tvAnquan.setTextColor(activity!!.resources.getColor(R.color.red))
                    }
                    StaticUtil.foul = reputModel.foul

                    if ((reputModel.foul.toInt() == 1 && reputModel.bail.toDouble() < 100)
                        || reputModel.safe.toDouble() < 12.5
                    ) {
                        if (!TextUtils.isEmpty(price)) {
                            getReputationNum()
                        }
                    }

                    if (reputModel.bail.toDouble() == 0.0) {
                        bail = "0"
                        bind.tvPay.text = "缴纳信誉金"
                    } else {
                        bail = reputModel.bail
                        bind.tvPay.text = "申请退还信誉金"
                    }
                }
            }, activity))
    }

    fun getCreditList(): Single<String> {
        val json = "{\"cmd\":\"creditList\",\"uid\":\"$userId\",\"page\":\"$page\"}"
        return retrofit.getData(json).async().compose(SingleCompose.compose(object : SingleObserverInterface {
            override fun onSuccess(response: String) {
                val model = Gson().fromJson(response, ReputationBaoModel::class.java)

                if (page == 1) {
                    totalPage = model.totalPage
                    if (model.totalPage == 1) {
                        adapter.flag = 0
                    }
                    adapter.upData(model.dataList)
                } else {
                    if (page == model.totalPage) {
                        adapter.loadMore(model.dataList, 0)
                    } else {
                        adapter.loadMore(model.dataList, -1)
                    }
                }
            }
        }, activity))
    }


    fun initViewModel() {
        bind.rvJulu.isFocusable = false
        bind.rvJulu.layoutManager = LinearLayoutManager(fragment?.context)

        bind.rvJulu.adapter = adapter
    }

    //获取平台信誉金
    fun getReputationMoney(): Single<String> {
        val json = "{\"cmd\":\"getBail\"" + "}"
        return retrofit.getData(json).async().compose(SingleCompose.compose(object : SingleObserverInterface {
            override fun onSuccess(response: String) {
                val obj = JSONObject(response)
                price = obj.getString("price")
                if ((!bail.isEmpty() && bail.toDouble() < 100 && reputModel.foul.toInt() == 1)
                    || reputModel.safe.toDouble() < 12.5
                ) {//不足100弹出缴纳信誉金，不可关闭
                    if (!TextUtils.isEmpty(price)) {
                        getReputationNum()
                    }
                }
            }
        }, activity))
    }


    //获取信誉金订单号
    fun getReputationNum(): Single<String> {
        val json = "{\"cmd\":\"addBailOrder\",\"uid\":\"" + StaticUtil.uid + "\",\"price\":\"" + price + "\"}"
        return retrofit.getData(json).async().compose(SingleCompose.compose(object : SingleObserverInterface {
            override fun onSuccess(response: String) {
                val obj = JSONObject(response)
                val bundle = Bundle()
                bundle.putDouble("money", price.toDouble())
                bundle.putString("num", obj.getString("orderId"))
                bundle.putInt("flag", 0)
                bundle.putInt("type", 0)
                MyApplication.openActivityForResult(activity, PayActivity::class.java, bundle, 0)
            }
        }, activity))
    }


    //退还信誉金
    fun BailRefund(): Single<String> {
        val json = "{\"cmd\":\"addBailRefund\",\"uid\":\"" + StaticUtil.uid + "\"}"
        return retrofit.getData(json).async().compose(SingleCompose.compose(object : SingleObserverInterface {
            override fun onSuccess(response: String) {
                BailRefundAfterDialog.show(activity!!)
            }
        }, activity))
    }

}