package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.support.v7.widget.GridLayoutManager
import com.google.gson.Gson
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.ui.mine.adapter.SeenSkillAdapter
import com.lxkj.qiqihunshe.app.ui.mine.model.SeenSkillModel
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.databinding.ActivityRecyvlerviewBinding
import io.reactivex.Single

/**
 * Created by Slingge on 2019/2/25
 */
class SeenSkillViewModel : BaseViewModel() {


    private val adapter by lazy { SeenSkillAdapter() }

    var page = 1

    var bind: ActivityRecyvlerviewBinding? = null

    fun initViewModel() {
        bind!!.recycler.layoutManager = GridLayoutManager(fragment?.context, 2)
        bind!!.recycler.adapter = adapter

        adapter.setMyListener { itemBean, position ->


        }
    }


    fun getSeenSkill(): Single<String> {
        val json = "{\"cmd\":\"caiyiShowList\",\"uid\":\"" + StaticUtil.uid + "\",\"page\":\"" + page + "\"}"
        return retrofit.getData(json).compose(SingleCompose.compose(object : SingleObserverInterface {
            override fun onSuccess(response: String) {
                val model = Gson().fromJson(response, SeenSkillModel::class.java)
                if(page>model.totalPage){
                    return
                }
                if (page == 1) {
                    bind!!.refresh.isRefreshing=false
                    if (model.totalPage == 1) {
                        adapter.flag = 0
                    }
                    adapter.upData(model.dataList)
                } else {
                    if (page==model.totalPage) {
                        adapter.loadMore(model.dataList, 0)
                    } else {
                        adapter.loadMore(model.dataList, -1)
                    }
                }
            }
        }, activity))
    }


}