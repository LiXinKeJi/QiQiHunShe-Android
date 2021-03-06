package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.TextView
import com.google.gson.Gson
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.retrofitnet.bindLifeCycle
import com.lxkj.qiqihunshe.app.ui.mine.activity.MyDynamicActivity
import com.lxkj.qiqihunshe.app.ui.mine.activity.PayActivity
import com.lxkj.qiqihunshe.app.ui.mine.adapter.DynamicAdapter
import com.lxkj.qiqihunshe.app.ui.mine.model.SpaceDynamicModel
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

    lateinit var adapter: DynamicAdapter
    var page = 1
    var totalPage = 1
    var bind: ActivityRecyvlerviewBinding? = null

    fun initViewModel() {
        bind!!.recycler.isFocusable = false
        bind!!.recycler.layoutManager = LinearLayoutManager(fragment?.context)

        adapter=DynamicAdapter(fragment!!.activity as Activity,ArrayList<SpaceDynamicModel.dataModel>())
        bind!!.recycler.adapter = adapter

        adapter.setMyListener { itemBean, position ->
            val bundle = Bundle()
            bundle.putInt("flag", 1)
            bundle.putSerializable("bean", itemBean)
            MyApplication.openActivityForResult(fragment!!.activity, MyDynamicActivity::class.java, bundle, 0)
        }

        adapter.setZanListener { position, tv ->
            zan(position,tv)
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


    fun zan(position: Int,tv: TextView) {
        val json =
            "{\"cmd\":\"zanDongtai\",\"dongtaiId\":\"${adapter.getAdapterList()[position].dongtaiId}\",\"uid\":\"${StaticUtil.uid}\"}"

        abLog.e("json", json)
        retrofit.getData(json).async()
            .doOnSubscribe {
                var flag = -1//1加，0减
                if (adapter.getAdapterList()[position].zan == "0") {// 0未点赞 1已点
                    adapter.getAdapterList()[position].zanNum =
                        (adapter.getAdapterList()[position].zanNum.toInt() + 1).toString()
                    adapter.getAdapterList()[position].zan = "1"
                    flag = 1
                } else {
                    adapter.getAdapterList()[position].zanNum =
                        (adapter.getAdapterList()[position].zanNum.toInt() - 1).toString()
                    adapter.getAdapterList()[position].zan = "0"
                    flag = 0
                }
                adapter.upZan(adapter.getAdapterList()[position].zanNum, tv, flag)
            }.bindLifeCycle(fragment!!).subscribe({},{toastFailure(it)})

    }


    fun jubao(content: String, position: Int): Single<String> {
        val json =
            "{\"cmd\":\"dongtaiReport\",\"dongtaiId\":\"${adapter.getAdapterList()[position].dongtaiId}\",\"uid\":\"${StaticUtil.uid}\",\"content\":\"${content}\"}"
        abLog.e("举报", json)
        return retrofit.getData(json).async().compose(SingleCompose.compose(object : SingleObserverInterface {
            override fun onSuccess(response: String) {
                ToastUtil.showTopSnackBar(fragment!!.activity, "举报提交成功")
            }
        }, fragment!!.activity))
    }

    fun dashang(money: String, position: Int): Single<String> {
        val json =
            "{\"cmd\":\"dongtaiTip\",\"dongtaiId\":\"${adapter.getAdapterList()[position].dongtaiId}\",\"uid\":\"${StaticUtil.uid}\",\"money\":\"${money}\"}"
        return retrofit.getData(json).async().compose(SingleCompose.compose(object : SingleObserverInterface {
            override fun onSuccess(response: String) {
                val obj = JSONObject(response)
                val bundle = Bundle()
                bundle.putString("num", obj.getString("orderId"))
                bundle.putDouble("money", money.toDouble())
                bundle.putInt("flag", 0)
                MyApplication.openActivityForResult(fragment!!.activity, PayActivity::class.java, bundle, 0)
            }
        }, fragment!!.activity))
    }


}