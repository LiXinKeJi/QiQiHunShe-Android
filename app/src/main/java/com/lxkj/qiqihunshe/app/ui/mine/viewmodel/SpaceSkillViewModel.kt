package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.google.gson.Gson
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.ui.mine.activity.MySkillActivity
import com.lxkj.qiqihunshe.app.ui.mine.adapter.SpaceSkillAdapter
import com.lxkj.qiqihunshe.app.ui.mine.model.SpaceSkillModel
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.databinding.FragmentSpaceSkillBinding
import io.reactivex.Single

/**
 * 我的空间- 我的才艺
 * Created by Slingge on 2019/2/25
 */
class SpaceSkillViewModel : BaseViewModel() {

    var page = 1
    var totalPage = 1
    val adapter by lazy { SpaceSkillAdapter() }

    var bind: FragmentSpaceSkillBinding? = null

    fun initViewModel() {
        bind!!.recycler.layoutManager = LinearLayoutManager(fragment?.context)
        bind!!.recycler.adapter = adapter

        adapter.setMyListener { itemBean, position ->

            val bundle = Bundle()
            bundle.putString("image", itemBean.image)
            bundle.putString("video", itemBean.video)
            MyApplication.openActivityForResult(fragment!!.activity, MySkillActivity::class.java, bundle, 1)
        }
    }


    fun getSkill(): Single<String> {
        val json = "{\"cmd\":\"userCaiyiList\",\"uid\":\"" + StaticUtil.uid + "\",\"page\":\"" + page + "\"}"
        return retrofit.getData(json).async()
            .compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(response: String) {
                    val model = Gson().fromJson(response, SpaceSkillModel::class.java)

                    if (page == 1) {
                        totalPage=model.totalPage
                        bind!!.refresh.isRefreshing = false
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


    //删除才艺
    fun DelSkill(position: Int): Single<String> {
        val json =
            "{\"cmd\":\"delCaiyi\",\"uid\":\"" + StaticUtil.uid + "\",\"dongtaiId\":\"" + adapter.getList()[position].caiyiId + "\"}"
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