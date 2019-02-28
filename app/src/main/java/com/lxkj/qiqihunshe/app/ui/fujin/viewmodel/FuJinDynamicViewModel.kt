package com.lxkj.qiqihunshe.app.ui.fujin.viewmodel

import android.support.v7.widget.LinearLayoutManager
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.ui.mine.adapter.DynamicAdapter
import com.lxkj.qiqihunshe.app.ui.mine.model.DynamicModel
import com.lxkj.qiqihunshe.databinding.ActivityRecyvlerviewBinding

/**
 * Created by Slingge on 2019/2/26
 */
class FuJinDynamicViewModel : BaseViewModel() {


    private val adapter by lazy { DynamicAdapter() }

    var bind: ActivityRecyvlerviewBinding? = null

    fun initViewModel() {
        bind!!.recycler.layoutManager = LinearLayoutManager(fragment?.context)

        adapter.activity = fragment!!.activity
        bind!!.recycler.adapter = adapter

        val list = ArrayList<DynamicModel>()
        for (i in 0 until 5) {
            val model = DynamicModel()
            list.add(model)
        }
        adapter.upData(list)
        adapter.flag = 0
    }

}