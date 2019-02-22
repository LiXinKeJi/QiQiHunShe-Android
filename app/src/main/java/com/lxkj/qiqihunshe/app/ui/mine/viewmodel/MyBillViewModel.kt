package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.databinding.ObservableField
import android.support.v7.widget.LinearLayoutManager
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.ui.mine.adapter.MyBillAdapter
import com.lxkj.qiqihunshe.app.ui.mine.model.MyBillModel
import com.lxkj.qiqihunshe.databinding.ActivityMybillBinding

/**
 * Created by Slingge on 2019/2/22
 */
class MyBillViewModel : BaseViewModel() {


    val startTime = ObservableField<String>()
    val endTime = ObservableField<String>()


    private val adapter by lazy { MyBillAdapter() }

    var bind: ActivityMybillBinding? = null

    fun initViewModel() {
        startTime.set("开始时间")
        endTime.set("结束时间")

        bind!!.rvBill.isFocusable = false
        bind!!.rvBill.layoutManager = LinearLayoutManager(fragment?.context)

        bind!!.rvBill.adapter = adapter

        val list = ArrayList<MyBillModel>()
        for (i in 0 until 5) {
            val model = MyBillModel()
            list.add(model)
        }
        adapter.upData(list)


    }


}