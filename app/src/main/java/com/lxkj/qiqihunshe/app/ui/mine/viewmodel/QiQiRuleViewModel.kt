package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.ui.mine.activity.WebViewActivity
import com.lxkj.qiqihunshe.app.ui.mine.adapter.QiQiRuleBaoAdapter
import com.lxkj.qiqihunshe.app.ui.mine.model.QiQiRuleModel
import com.lxkj.qiqihunshe.app.ui.mine.model.ReputationBaoModel
import com.lxkj.qiqihunshe.databinding.ActivityQiqiRuleBinding

/**
 * Created by Slingge on 2019/2/21
 */
class QiQiRuleViewModel : BaseViewModel() {


    private val adapter1 by lazy { QiQiRuleBaoAdapter() }
    private val adapter2 by lazy { QiQiRuleBaoAdapter() }
    private val adapter3 by lazy { QiQiRuleBaoAdapter() }

    var bind: ActivityQiqiRuleBinding? = null

    fun initViewModel() {

        initData(bind!!.rvGuard, adapter1)
        initData(bind!!.rvRule, adapter2)
        initData(bind!!.rvGuide, adapter3)
    }


    private fun initData(rv: RecyclerView, adapter: QiQiRuleBaoAdapter) {
        rv.isFocusable = false
        rv.layoutManager = LinearLayoutManager(fragment?.context)

        rv.adapter = adapter

        val list = ArrayList<QiQiRuleModel>()
        for (i in 0 until 5) {
            val model = QiQiRuleModel()
            list.add(model)
        }
        adapter.upData(list)

        adapter.setMyListener { bean, position ->
            MyApplication.openActivity(activity,WebViewActivity::class.java)
        }
    }


}