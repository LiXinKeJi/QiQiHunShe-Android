package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.support.v7.widget.LinearLayoutManager
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.ui.mine.activity.QiQiDynamicDetailsActivity
import com.lxkj.qiqihunshe.app.ui.mine.adapter.QiQiDynamicSignUpRecordAdapter
import com.lxkj.qiqihunshe.app.ui.mine.model.QiQiDynamicModel
import com.lxkj.qiqihunshe.databinding.ActivitySignupRecordBinding

/**
 * Created by Slingge on 2019/2/22
 */
class SignUpRecordViewModel:BaseViewModel(){



    private val adapter by lazy { QiQiDynamicSignUpRecordAdapter() }

    var bind: ActivitySignupRecordBinding? = null

    fun initViewModel() {
        bind!!.rvRecord.isFocusable = false
        bind!!.rvRecord.layoutManager = LinearLayoutManager(fragment?.context)

        bind!!.rvRecord.adapter = adapter

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