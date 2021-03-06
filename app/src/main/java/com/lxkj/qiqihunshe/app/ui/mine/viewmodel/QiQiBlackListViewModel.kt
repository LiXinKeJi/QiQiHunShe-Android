package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.support.v7.widget.LinearLayoutManager
import com.google.gson.Gson

import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.async

import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.rongrun.RongYunUtil
import com.lxkj.qiqihunshe.app.ui.mine.adapter.QiQiBlackListAdapter
import com.lxkj.qiqihunshe.app.ui.mine.model.QiQiBlackListModel
import com.lxkj.qiqihunshe.app.util.StaticUtil

import com.lxkj.qiqihunshe.databinding.ActivityRecyvlerviewBinding

import io.reactivex.Single

/**
 *
 * Created by Slingge on 2019/2/26
 */
class QiQiBlackListViewModel : BaseViewModel() {

    val adapter by lazy { QiQiBlackListAdapter(flag) }

    var bind: ActivityRecyvlerviewBinding? = null

    var page = 1
    var totalpage = 1

    var flag = -1//0七七黑名单，1我的黑名单

    fun initViewModel() {
        bind!!.recycler.layoutManager = LinearLayoutManager(fragment?.context)
        bind!!.recycler.adapter = adapter
    }


    fun getBlackData(): Single<String> {
        var json: String
        if (flag == 0) {
            json = "{\"cmd\":\"blacklist\",\"page\":\"$page\"}"
        } else {
            json =
                "{\"cmd\":\"myblacklist\",\"uid\":\"" + StaticUtil.uid + "\",\"page\":\"" + page + "\"}"
        }
        return retrofit.getData(json)
            .async().compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(response: String) {
                    val model = Gson().fromJson(response, QiQiBlackListModel::class.java)

                    if (page == 1) {
                        bind!!.refresh.isRefreshing = false
                        totalpage = model.totalPage
                        if (totalpage == 1) {
                            adapter.flag = 1
                        }
                        adapter.upData(model.dataList)

                        for (m in model.dataList) {
                            RongYunUtil.removeBlackList(m.userId)
                        }

                    } else {
                        if (page == totalpage) {
                            adapter.loadMore(model.dataList, 1)
                        } else {
                            adapter.loadMore(model.dataList, -1)
                        }
                    }
                }
            }, activity))
    }


}