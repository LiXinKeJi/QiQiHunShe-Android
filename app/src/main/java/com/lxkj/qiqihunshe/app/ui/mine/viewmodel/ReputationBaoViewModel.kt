package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.support.v7.widget.LinearLayoutManager
import com.google.gson.Gson
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.ui.mine.adapter.ReputationBaoAdapter
import com.lxkj.qiqihunshe.app.ui.mine.model.ReputationBaoModel
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.databinding.ActivityReputationBaoBinding
import io.reactivex.Single

/**
 * Created by Slingge on 2019/2/21
 */
class ReputationBaoViewModel : BaseViewModel() {


    val adapter by lazy { ReputationBaoAdapter() }

    var bind: ActivityReputationBaoBinding? = null

    var page = 1
    var totalPage = 1

    fun getUserCredit(): Single<String> {
        val json = "{\"cmd\":\"getUserCredit\",\"uid\":\"" + StaticUtil.uid + "\"}"
        return retrofit.getData(json).async()
            .compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(response: String) {
                    val model = Gson().fromJson(response, ReputationBaoModel::class.java)
                    bind!!.model = model
                }
            }, activity))
    }

    fun getCreditList(): Single<String> {
        val json = "{\"cmd\":\"creditList\",\"uid\":\"" + StaticUtil.uid + "\",\"page\":\"" + page + "\"}"
        return retrofit.getData(json).async().compose(SingleCompose.compose(object : SingleObserverInterface {
            override fun onSuccess(response: String) {
                val model = Gson().fromJson(response, ReputationBaoModel::class.java)

                if (page == 1) {
                    totalPage=model.totalPage
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
        bind!!.rvJulu.isFocusable = false
        bind!!.rvJulu.layoutManager = LinearLayoutManager(fragment?.context)

        bind!!.rvJulu.adapter = adapter
    }


}