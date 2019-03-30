package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.google.gson.Gson
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.ui.mine.activity.PersonInvitationDetailsActivity
import com.lxkj.qiqihunshe.app.ui.mine.adapter.AffectiveMarriageAdapter
import com.lxkj.qiqihunshe.app.ui.mine.model.SpaceInvitationModel
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.app.util.abLog
import com.lxkj.qiqihunshe.databinding.ActivityRecyvlerviewBinding
import io.reactivex.Single

/**
 * 情感征婚
 * Created by Slingge on 2019/2/25
 */
class AffectiveMarriageViewModel : BaseViewModel() {

    val adapter by lazy { AffectiveMarriageAdapter() }
    var page = 1
    var totalPage = 1
    var bind: ActivityRecyvlerviewBinding? = null

    fun initViewModel() {
        bind!!.recycler.isFocusable = false
        bind!!.recycler.layoutManager = LinearLayoutManager(fragment?.context)

        bind!!.recycler.adapter = adapter
        adapter.activity = fragment!!.activity
        adapter.setMyListener { itemBean, position ->
            val bundle = Bundle()
            bundle.putString("id", itemBean.yaoyueId)
            MyApplication.openActivity(fragment?.activity, PersonInvitationDetailsActivity::class.java, bundle)
        }
    }


    fun getYaoyue(): Single<String> {
        val json = "{\"cmd\":\"nearbyYaoyue\",\"uid\":\"" + StaticUtil.uid + "\",\"typeId\":\"" + "" +
                "\",\"category\":\"" + "1" + "\",\"page\":\"" + page + "\",\"lon\":\"" + StaticUtil.lng + "\",\"lat\":\"" + StaticUtil.lat + "\"}"
        return retrofit.getData(json).async().compose(SingleCompose.compose(object : SingleObserverInterface {
            override fun onSuccess(response: String) {
                val model = Gson().fromJson(response, SpaceInvitationModel::class.java)
                bind!!.refresh.isRefreshing = false

                if (page == 1) {
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
        }, fragment?.activity))
    }


    fun jubao(content: String, position: Int): Single<String> {
        val json =
            "{\"cmd\":\"yaoyueReport\",\"yaoyueId\":\"${adapter.getList()[position].yaoyueId}\",\"uid\":\"${StaticUtil.uid}\",\"content\":\"${content}\"}"
        abLog.e("举报情感镇魂", json)
        return retrofit.getData(json).async().compose(SingleCompose.compose(object : SingleObserverInterface {
            override fun onSuccess(response: String) {
                ToastUtil.showTopSnackBar(fragment!!.activity, "举报提交成功")
            }
        }, fragment!!.activity))
    }


}