package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.google.gson.Gson
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.ui.mine.activity.MyDynamicActivity
import com.lxkj.qiqihunshe.app.ui.mine.activity.PayActivity
import com.lxkj.qiqihunshe.app.ui.mine.adapter.AffectiveDynamicAdapter
import com.lxkj.qiqihunshe.app.ui.mine.model.SpaceDynamicModel
import com.lxkj.qiqihunshe.app.util.AbStrUtil
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.app.util.abLog
import com.lxkj.qiqihunshe.databinding.ActivityRecyvlerviewBinding
import io.reactivex.Single
import org.json.JSONObject

/**
 * 情感动态
 * Created by Slingge on 2019/2/25
 */
class AffectiveDynamicViewModel : BaseViewModel() {

    val adapter by lazy { AffectiveDynamicAdapter() }
    var page = 1
    var totalPage = 1
    var bind: ActivityRecyvlerviewBinding? = null

    fun initViewModel() {
        bind!!.recycler.isFocusable = false
        bind!!.recycler.layoutManager = LinearLayoutManager(fragment?.context)

        bind!!.recycler.adapter = adapter

        adapter.setMyListener { itemBean, position ->
            val bundle = Bundle()
            bundle.putInt("flag", 1)
            bundle.putSerializable("bean", itemBean)
            MyApplication.openActivityForResult(fragment!!.activity, MyDynamicActivity::class.java, bundle, 0)
        }
    }


    fun getMyDynamic(): Single<String> {
        val json = "{\"cmd\":\"nearbyDongtai\",\"uid\":\"" + StaticUtil.uid + "\",\"typeId\":\"" + "" +
                "\",\"category\":\"" + "1" + "\",\"page\":\"" + page + "\",\"lon\":\"" + StaticUtil.lng + "\",\"lat\":\"" + StaticUtil.lat + "\"}"
        abLog.e("情感动态", json)
        return retrofit.getData(json)
            .async()
            .compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(response: String) {
                    bind!!.refresh.isRefreshing = false
                    val model = Gson().fromJson(response, SpaceDynamicModel::class.java)
                    if (page == 1) {
                        totalPage = model.totalPage
                        if (model.totalPage == 1 || model.dataList.isEmpty()) {
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
            }, fragment!!.activity))
    }


    fun zan(position: Int): Single<String> {
        val json =
            "{\"cmd\":\"zanDongtai\",\"dongtaiId\":\"${adapter.getList()[position].dongtaiId}\",\"uid\":\"${StaticUtil.uid}\"}"

        abLog.e("json", json)
        return retrofit.getData(json).async()
            .doOnSubscribe {


                /* if (adapter.getList()[position].zan == "0") {
                     adapter.getList()[position].zanNum = (adapter.getList()[position].zanNum.toInt() + 1).toString()
                     adapter.getList()[position].zan = "1"
                 } else {
                     adapter.getList()[position].zanNum = (adapter.getList()[position].zanNum.toInt() - 1).toString()
                     adapter.getList()[position].zan = "0"
                 }
                 adapter.notifyItemChanged(position, false)*/
            }

    }


    fun jubao(content: String, position: Int): Single<String> {
        val json =
            "{\"cmd\":\"dongtaiReport\",\"dongtaiId\":\"${adapter.getList()[position].dongtaiId}\",\"uid\":\"${StaticUtil.uid}\",\"content\":\"${content}\"}"
        abLog.e("举报", json)
        return retrofit.getData(json).async().compose(SingleCompose.compose(object : SingleObserverInterface {
            override fun onSuccess(response: String) {
                ToastUtil.showTopSnackBar(fragment!!.activity, "举报提交成功")
            }
        }, fragment!!.activity))
    }

    fun dashang(money: String, position: Int): Single<String> {
        val json =
            "{\"cmd\":\"dongtaiTip\",\"dongtaiId\":\"${adapter.getList()[position].dongtaiId}\",\"uid\":\"${StaticUtil.uid}\",\"money\":\"${money}\"}"
        return retrofit.getData(json).async().compose(SingleCompose.compose(object : SingleObserverInterface {
            override fun onSuccess(response: String) {
                val obj = JSONObject(response)
                ToastUtil.showTopSnackBar(fragment!!.activity, obj.getString("orderId"))
                val bundle = Bundle()
                bundle.putString("num", obj.getString("orderId"))
                bundle.putDouble("money", money.toDouble())
                bundle.putInt("flag", 0)
                MyApplication.openActivityForResult(activity, PayActivity::class.java, bundle, 0)
            }
        }, fragment!!.activity))
    }


}