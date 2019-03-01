package com.lxkj.qiqihunshe.app.ui.xiaoxi.viewmodel

import android.support.v7.widget.LinearLayoutManager
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.ui.xiaoxi.adapter.LookupResultAdapter
import com.lxkj.qiqihunshe.app.ui.xiaoxi.model.LookResultModel
import com.lxkj.qiqihunshe.databinding.ActivityLookupResultBinding

/**
 * Created by Slingge on 2019/3/1
 */
class LookupResultViewModel : BaseViewModel() {


    var bind: ActivityLookupResultBinding? = null


    private val adapter by lazy { LookupResultAdapter() }

    fun initViewmodel() {
        bind!!.recycler.isFocusable = false
        bind!!.recycler.layoutManager = LinearLayoutManager(fragment?.context)

        bind!!.recycler.adapter = adapter

        val list = ArrayList<LookResultModel>()
        for (i in 0 until 5) {
            val model = LookResultModel()
            list.add(model)
        }
        adapter.upData(list)


    }


}