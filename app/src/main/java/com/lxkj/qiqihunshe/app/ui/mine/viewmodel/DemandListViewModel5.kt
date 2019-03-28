package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.annotation.SuppressLint
import android.databinding.ObservableField
import android.os.Bundle
import com.google.gson.Gson
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.ui.mine.activity.PayActivity
import com.lxkj.qiqihunshe.app.ui.mine.model.PermissionBuyXuQiuModel
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.app.util.abLog
import com.lxkj.qiqihunshe.databinding.ActivityDemandList5Binding
import io.reactivex.Single
import org.json.JSONObject

/**
 * Created by Slingge on 2019/3/28
 */
class DemandListViewModel5 : BaseViewModel() {

    var money = ObservableField<String>()
    var info = ObservableField<String>()

    lateinit var model:PermissionBuyXuQiuModel

    lateinit var bind: ActivityDemandList5Binding

    fun init() {
        activity?.let {
            money.set(it.intent.getStringExtra("money"))
            info.set(it.intent.getStringExtra("info"))
        }
    }


    //定制推荐、牵引安排，获取条件
    @SuppressLint("CheckResult")
    fun getXuqiu(): Single<String> {
        val json = "{\"cmd\":\"getXuqiuList\",\"uid\":\"" + StaticUtil.uid + "\",\"type\":\"" + "5" + "\"}"
        abLog.e("获取条件", json)
        return retrofit.getData(json).async().compose(SingleCompose.compose(object : SingleObserverInterface {
            override fun onSuccess(response: String) {
                abLog.e("获取条件", response)
                model = Gson().fromJson(response, PermissionBuyXuQiuModel::class.java)
                model.type = "5"
                bind.model=model
//                BuyPer(model, money)
            }
        }, activity))
    }


    //购买权限
    @SuppressLint("CheckResult")
    fun BuyPer():Single<String> {
        abLog.e("购买权限", Gson().toJson(model))
     return   retrofit.getData(Gson().toJson(model)).async().compose(SingleCompose.compose(object : SingleObserverInterface {
            override fun onSuccess(response: String) {
                val obj = JSONObject(response)
                val bundle = Bundle()
                bundle.putString("num", obj.getString("orderId"))
                bundle.putDouble("money", money.get()!!.toDouble())
                bundle.putInt("flag", 0)
                MyApplication.openActivityForResult(activity, PayActivity::class.java, bundle, 0)
            }
        }, activity))
    }


}