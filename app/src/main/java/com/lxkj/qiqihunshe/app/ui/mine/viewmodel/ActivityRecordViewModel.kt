package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.support.v7.widget.LinearLayoutManager
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.ui.mine.adapter.ActivityRecordAdapter
import com.lxkj.qiqihunshe.app.ui.mine.model.ActivityRecordModel
import com.lxkj.qiqihunshe.databinding.ActivityRecyvlerviewBinding

/**
 * 活动记录
 * Created by Slingge on 2019/2/22
 */
class ActivityRecordViewModel : BaseViewModel() {


    private val adapter by lazy { ActivityRecordAdapter() }

    var bind: ActivityRecyvlerviewBinding? = null

    fun initViewModel() {
        bind!!.recycler.isFocusable = false
        bind!!.recycler.layoutManager = LinearLayoutManager(fragment?.context)

        bind!!.recycler.adapter = adapter

        val list = ArrayList<ActivityRecordModel>()
        for (i in 0 until 5) {
            val model = ActivityRecordModel()
            list.add(model)
        }
        adapter.upData(list)

        adapter.setMyListener { itemBean, position ->

        }
    }


}