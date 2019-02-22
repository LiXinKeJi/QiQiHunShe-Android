package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.support.v7.widget.LinearLayoutManager
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.ui.mine.activity.QiQiDynamicDetailsActivity
import com.lxkj.qiqihunshe.app.ui.mine.adapter.QiQiDynamicAdapter
import com.lxkj.qiqihunshe.app.ui.mine.model.QiQiDynamicModel
import com.lxkj.qiqihunshe.databinding.ActivityQiqiDynamicBinding

/**
 * 活动报名记录
 * Created by Slingge on 2019/2/21
 */
class QiQiDynamicViewModel : BaseViewModel() {


    private val adapter by lazy { QiQiDynamicAdapter() }

    var bind: ActivityQiqiDynamicBinding? = null

    fun initViewModel() {
        bind!!.rvDyna.isFocusable = false
        bind!!.rvDyna.layoutManager = LinearLayoutManager(fragment?.context)

        bind!!.rvDyna.adapter = adapter

        val list = ArrayList<QiQiDynamicModel>()
        for (i in 0 until 5) {
            val model = QiQiDynamicModel()
            list.add(model)
        }
        adapter.upData(list)

        adapter.setMyListener { itemBean, position ->
            MyApplication.openActivity(activity, QiQiDynamicDetailsActivity::class.java)
        }
    }


}