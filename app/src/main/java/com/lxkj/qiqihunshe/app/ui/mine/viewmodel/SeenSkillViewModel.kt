package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import com.google.gson.Gson
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.ui.mine.activity.MySkillDetailsActiivity
import com.lxkj.qiqihunshe.app.ui.mine.adapter.SeenSkillAdapter
import com.lxkj.qiqihunshe.app.ui.mine.model.SeenSkillModel
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.app.util.abLog
import com.lxkj.qiqihunshe.databinding.ActivityRecyvlerviewBinding
import io.reactivex.Single

/**
 * Created by Slingge on 2019/2/25
 */
class SeenSkillViewModel : BaseViewModel() {


    val adapter by lazy { SeenSkillAdapter() }

    var page = 1
    var totalPage = 1

    var bind: ActivityRecyvlerviewBinding? = null

    fun initViewModel() {
        bind!!.recycler.layoutManager = GridLayoutManager(fragment?.context, 2)
        bind!!.recycler.adapter = adapter

        adapter.setMyListener { itemBean, position ->
            val bundle = Bundle()
            bundle.putString("id", itemBean.caiyiId)

            MyApplication.openActivity(activity, MySkillDetailsActiivity::class.java, bundle)
        }
    }


    fun getSeenSkill(): Single<String> {
        val json = "{\"cmd\":\"caiyiShowList\",\"uid\":\"" + StaticUtil.uid + "\",\"page\":\"" + page + "\"}"
        abLog.e("我看过的才艺", json)
        return retrofit.getData(json).async().compose(SingleCompose.compose(object : SingleObserverInterface {
            override fun onSuccess(response: String) {
                val model = Gson().fromJson(response, SeenSkillModel::class.java)
                if (page == 1) {
                    totalPage = model.totalPage
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
        }, activity))
    }


}