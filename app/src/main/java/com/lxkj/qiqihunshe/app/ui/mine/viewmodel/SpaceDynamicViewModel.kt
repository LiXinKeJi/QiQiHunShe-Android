package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.ui.mine.activity.MyDynamicActivity
import com.lxkj.qiqihunshe.app.ui.mine.adapter.SpaceDynamicAdapter
import com.lxkj.qiqihunshe.app.ui.mine.model.SpaceDynamicModel
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.ActivityRecyvlerviewBinding

/**
 * Created by Slingge on 2019/2/25
 */
class SpaceDynamicViewModel : BaseViewModel() {


    private val adapter by lazy { SpaceDynamicAdapter() }

    var bind: ActivityRecyvlerviewBinding? = null

    fun initViewModel() {
        bind!!.recycler.layoutManager = LinearLayoutManager(fragment?.context)

        bind!!.recycler.adapter = adapter

        val list = ArrayList<SpaceDynamicModel>()
        for (i in 0 until 5) {
            val model = SpaceDynamicModel()
            list.add(model)
        }
        adapter.upData(list)

        adapter.setMyListener { itemBean, position ->
          MyApplication.openActivity(fragment!!.context,MyDynamicActivity::class.java)
        }
    }


}