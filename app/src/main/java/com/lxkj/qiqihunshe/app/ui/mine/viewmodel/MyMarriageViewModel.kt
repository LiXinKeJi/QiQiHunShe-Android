package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.google.gson.Gson
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.ui.mine.activity.MyInvitationDetailsActivity
import com.lxkj.qiqihunshe.app.ui.mine.adapter.SpaceInvitationAdapter
import com.lxkj.qiqihunshe.app.ui.mine.model.SpaceInvitationModel
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.databinding.ActivityRecyvlerviewBinding
import io.reactivex.Single

/**
 * 我的情感-我的征婚
 * Created by Slingge on 2019/2/26
 */
class MyMarriageViewModel : BaseViewModel() {

    val adapter by lazy { SpaceInvitationAdapter() }

    var bind: ActivityRecyvlerviewBinding? = null

    var page = 1
    var totalPage = 1

    fun initViewModel() {
        bind!!.recycler.layoutManager = LinearLayoutManager(fragment?.context)
        bind!!.recycler.adapter = adapter


        adapter.setMyListener { itemBean, position ->
            val bundle = Bundle()
            bundle.putString("id", itemBean.yaoyueId)
            MyApplication.openActivity(fragment?.context, MyInvitationDetailsActivity::class.java,bundle)
        }
    }

    fun getYaoyue(): Single<String> {
        val json = "{\"cmd\":\"userYaoyue\",\"uid\":\"" + StaticUtil.uid +
                "\",\"category\":\"" + "1" + "\",\"page\":\"" + page + "\"}"
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


    //删除动态
    fun DelInvitation(position: Int): Single<String> {
        val json =
            "{\"cmd\":\"delYaoyue\",\"uid\":\"" + StaticUtil.uid + "\",\"yaoyueId\":\"" + adapter.getList()[position].yaoyueId + "\"}"
        return retrofit.getData(json).async()
            .compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(response: String) {
                    removeItem(position)
                }
            }, fragment!!.activity))
    }
    fun removeItem(position: Int) {
        adapter.removeItem(position)
    }


}