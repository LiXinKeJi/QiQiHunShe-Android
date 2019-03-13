package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.support.v7.widget.GridLayoutManager
import com.google.gson.Gson
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.ui.mine.adapter.PersonSkillAdapter
import com.lxkj.qiqihunshe.app.ui.mine.model.SpaceSkillModel
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.databinding.FragmentPersonSkillBinding
import io.reactivex.Single

/**
 * 才艺
 * Created by Slingge on 2019/2/21
 */
class PersonSkillViewModel : BaseViewModel() {

    var userId = ""

    val adapter by lazy { PersonSkillAdapter() }

    var bind: FragmentPersonSkillBinding? = null

    var page = 1
    var totalPage = 1

    fun initViewModel() {
        bind!!.rvDynamic.layoutManager = GridLayoutManager(fragment?.context, 2)
        bind!!.rvDynamic.adapter = adapter
    }

    fun getSkill(): Single<String> {
        val json = "{\"cmd\":\"userCaiyiList\",\"uid\":\"" + userId + "\",\"page\":\"" + page + "\"}"
        return retrofit.getData(json).async()
            .compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(response: String) {
                    val model = Gson().fromJson(response, SpaceSkillModel::class.java)
                    if (page == 1) {
                        totalPage = model.totalPage
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