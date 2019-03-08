package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.support.v7.widget.LinearLayoutManager
import com.google.gson.Gson
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.ui.mine.activity.MyInvitationDetailsActivity
import com.lxkj.qiqihunshe.app.ui.mine.adapter.SpaceInvitationAdapter
import com.lxkj.qiqihunshe.app.ui.mine.model.SpaceInvitationModel
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.databinding.ActivityRecyvlerviewBinding
import io.reactivex.Single

/**
 * 我的空间- 我的邀约
 * Created by Slingge on 2019/2/25
 */
class SpaceInvitationViewModel : BaseViewModel() {


    private val adapter by lazy { SpaceInvitationAdapter() }

    var page = 1

    var bind: ActivityRecyvlerviewBinding? = null

    fun initViewModel() {
        bind!!.recycler.layoutManager = LinearLayoutManager(fragment?.context)
        bind!!.recycler.adapter = adapter

        adapter.setMyListener { itemBean, position ->
            MyApplication.openActivity(fragment?.context, MyInvitationDetailsActivity::class.java)
        }
    }


    fun getYaoyue(): Single<String> {
        val json = "{\"cmd\":\"userYaoyue\",\"uid\":\"" + StaticUtil.uid + "\",\"category\":\"" + "0" +
                "\",\"page\":\"" + page + "\"}"
        return retrofit.getData(json).compose(SingleCompose.compose(object : SingleObserverInterface {
            override fun onSuccess(response: String) {

                val model = Gson().fromJson(response, SpaceInvitationModel::class.java)

                if (page == 1) {
                    if (model.totalPage == 1) {
                        adapter.flag = 0
                    }
                    adapter.upData(model.dataList)
                } else {
                    if (page >= model.totalPage) {
                        adapter.loadMore(model.dataList, 0)
                    } else {
                        adapter.loadMore(model.dataList, -1)
                    }
                }
            }
        }, activity))
    }


}