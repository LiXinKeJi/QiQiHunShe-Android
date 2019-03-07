package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.support.v7.widget.LinearLayoutManager
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.ui.mine.adapter.ReputationBaoAdapter
import com.lxkj.qiqihunshe.app.ui.mine.model.ReputationBaoModel
import com.lxkj.qiqihunshe.databinding.ActivityReputationBaoBinding

/**
 * Created by Slingge on 2019/2/21
 */
class ReputationBaoViewModel : BaseViewModel() {


    private val adapter by lazy { ReputationBaoAdapter() }

    var bind: ActivityReputationBaoBinding? = null

    fun initViewModel() {
        bind!!.rvJulu.isFocusable=false
        bind!!.rvJulu.layoutManager = LinearLayoutManager(fragment?.context)

        bind!!.rvJulu.adapter = adapter

        val list = ArrayList<ReputationBaoModel>()
        for (i in 0 until 5) {
            val model = ReputationBaoModel()
            list.add(model)
        }
        adapter.upData(list)

    }


}