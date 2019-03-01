package com.lxkj.qiqihunshe.app.ui.xiaoxi.viewmodel

import android.support.v7.widget.LinearLayoutManager
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.ui.xiaoxi.activity.MsgDetailsActivity
import com.lxkj.qiqihunshe.app.ui.xiaoxi.adapter.QiQiRemindAdapter
import com.lxkj.qiqihunshe.app.ui.xiaoxi.model.QiQiRemindModel
import com.lxkj.qiqihunshe.databinding.ActivityRecyvlerviewBinding

/**
 * Created by Slingge on 2019/3/1
 */
class QiQiRemindViewModel : BaseViewModel() {


    var bind: ActivityRecyvlerviewBinding? = null


    private val adapter by lazy { QiQiRemindAdapter() }

    fun initViewmodel() {
        bind!!.recycler.isFocusable = false
        bind!!.recycler.layoutManager = LinearLayoutManager(fragment?.context)

        bind!!.recycler.adapter = adapter

        val list = ArrayList<QiQiRemindModel>()
        for (i in 0 until 5) {
            val model = QiQiRemindModel()
            list.add(model)
        }
        adapter.upData(list)

    }


}