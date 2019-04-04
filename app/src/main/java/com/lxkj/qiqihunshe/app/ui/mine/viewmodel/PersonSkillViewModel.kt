package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import com.google.gson.Gson
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.ui.mine.activity.MySkillActivity
import com.lxkj.qiqihunshe.app.ui.mine.adapter.PersonSkillAdapter
import com.lxkj.qiqihunshe.app.ui.mine.model.SpaceSkillModel
import com.lxkj.qiqihunshe.databinding.FragmentPersonSkillBinding
import io.reactivex.Single

/**
 * 才艺
 * Created by Slingge on 2019/2/21
 */
class PersonSkillViewModel : BaseViewModel() {

    var userId = ""

    val adapter by lazy { PersonSkillAdapter(fragment!!.activity!!, list) }
    private val list by lazy { ArrayList<SpaceSkillModel.dataModel>() }
    var bind: FragmentPersonSkillBinding? = null

    var page = 1
    var totalPage = 1

    fun initViewModel() {
        bind!!.rvDynamic.layoutManager = GridLayoutManager(fragment?.context, 2)
        bind!!.rvDynamic.adapter = adapter

        adapter.setMyListener { itemBean, position ->
            val bundle = Bundle()
            bundle.putString("image", itemBean.image)
            bundle.putString("video", itemBean.video)
            bundle.putInt("position", position)
            bundle.putString("id", itemBean.caiyiId)
            MyApplication.openActivity(fragment!!.activity, MySkillActivity::class.java, bundle)
        }
    }

    fun getSkill(): Single<String> {
        val json = "{\"cmd\":\"userCaiyiList\",\"uid\":\"$userId\",\"page\":\"$page\"}"
        return retrofit.getData(json).async()
            .compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(response: String) {
                    val model = Gson().fromJson(response, SpaceSkillModel::class.java)
                    list.addAll(model.dataList)
                    if (page == 1) {
                        totalPage = model.totalPage
                    }
                    adapter.notifyDataSetChanged()
                }
            }, fragment?.activity))
    }


    fun removeItem(position: Int) {
        adapter.removeItem(position)
    }


}