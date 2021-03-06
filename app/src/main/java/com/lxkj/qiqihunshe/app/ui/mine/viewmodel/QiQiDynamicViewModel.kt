package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.ui.mine.activity.QiQiDynamicDetailsActivity
import com.lxkj.qiqihunshe.app.ui.mine.adapter.QiQiDynamicAdapter
import com.lxkj.qiqihunshe.app.ui.mine.model.QiQiBlackListModel
import com.lxkj.qiqihunshe.app.ui.mine.model.QiQiDynamicModel
import com.lxkj.qiqihunshe.app.ui.mine.model.QiQiRuleModel
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.ActivityQiqiDynamicBinding
import io.reactivex.Single
import org.json.JSONObject

/**
 * 活动报名记录
 * Created by Slingge on 2019/2/21
 */
class QiQiDynamicViewModel : BaseViewModel() {

    val adapter by lazy { QiQiDynamicAdapter() }

    var bind: ActivityQiqiDynamicBinding? = null

    var page = 1
    var totalPage = 1

    fun initViewModel() {
        bind!!.rvDyna.isFocusable = false
        bind!!.rvDyna.layoutManager = LinearLayoutManager(fragment?.context)

        bind!!.rvDyna.adapter = adapter

        adapter.setMyListener { itemBean, position ->
            var bundle = Bundle()
            bundle.putSerializable("id", itemBean.activityId)
            MyApplication.openActivity(activity, QiQiDynamicDetailsActivity::class.java, bundle)
        }
    }

    fun getData(): Single<String> {
        val json = "{\"cmd\":\"activityList" +
                "\",\"page\":\"" + page +
                "\"}"
        return retrofit.getData(json)
            .async().compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(response: String) {
                    val model = Gson().fromJson(response, QiQiDynamicModel::class.java)
                    if (page == 1) {
                        totalPage = model.totalPage
                        if (totalPage == 1) {
                            adapter.loadMore(model.dataList, 1)
                        } else {
                            if (page == totalPage) {
                                adapter.loadMore(model.dataList, 1)
                            } else {
                                adapter.loadMore(model.dataList, -1)
                            }
                        }
                    }
                }

            }, activity))
    }


}