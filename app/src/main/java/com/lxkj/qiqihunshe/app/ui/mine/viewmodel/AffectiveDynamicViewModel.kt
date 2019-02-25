package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.support.v7.widget.LinearLayoutManager
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.ui.mine.adapter.AffectiveDynamicAdapter
import com.lxkj.qiqihunshe.app.ui.mine.model.DynamicModel
import com.lxkj.qiqihunshe.databinding.ActivityRecyvlerviewBinding

/**
 * 情感动态
 * Created by Slingge on 2019/2/25
 */
class AffectiveDynamicViewModel:BaseViewModel() {


    private val adapter by lazy { AffectiveDynamicAdapter() }

    var bind: ActivityRecyvlerviewBinding? = null

    fun initViewModel() {
        bind!!.recycler.isFocusable = false
        bind!!.recycler.layoutManager = LinearLayoutManager(fragment?.context)

        bind!!.recycler.adapter = adapter

        val list = ArrayList<DynamicModel>()
        for (i in 0 until 5) {
            val model = DynamicModel()
            list.add(model)
        }
        adapter.upData(list)

        adapter.setMyListener { itemBean, position ->

        }
    }


}