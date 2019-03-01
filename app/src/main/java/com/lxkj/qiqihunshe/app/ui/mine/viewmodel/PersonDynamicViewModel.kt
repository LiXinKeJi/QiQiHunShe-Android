package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.support.v7.widget.LinearLayoutManager
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.ui.mine.adapter.DynamicAdapter
import com.lxkj.qiqihunshe.app.ui.mine.model.DynamicModel
import com.lxkj.qiqihunshe.databinding.FragmentPersonDynamicBinding

/**
 *
 * Created by Slingge on 2019/2/21
 */
class PersonDynamicViewModel : BaseViewModel() {


    private val adapter by lazy { DynamicAdapter() }

    var bind: FragmentPersonDynamicBinding? = null

    fun initViewModel() {
        bind!!.rvDynamic.layoutManager = LinearLayoutManager(fragment?.context)

        bind!!.rvDynamic.adapter = adapter
        adapter.activity=fragment!!.activity
        val list = ArrayList<DynamicModel>()
        for (i in 0 until 5) {
            val model = DynamicModel()
            list.add(model)
        }
        adapter.upData(list)

    }


}