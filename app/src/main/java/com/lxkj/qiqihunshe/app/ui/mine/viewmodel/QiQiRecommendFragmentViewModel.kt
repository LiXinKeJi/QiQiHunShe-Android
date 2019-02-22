package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.support.v7.widget.LinearLayoutManager
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.ui.mine.adapter.QiQiRecommendAdapter
import com.lxkj.qiqihunshe.app.ui.mine.model.QiQiRecommendModel
import com.lxkj.qiqihunshe.databinding.ActivityRecyvlerviewBinding

/**
 * 七七推荐
 * Created by Slingge on 2019/2/22
 */
class QiQiRecommendFragmentViewModel :BaseViewModel(){



    private val adapter by lazy { QiQiRecommendAdapter() }

    var bind: ActivityRecyvlerviewBinding? = null

    fun initViewModel() {
        bind!!.recycler.isFocusable = false
        bind!!.recycler.layoutManager = LinearLayoutManager(fragment?.context)

        bind!!.recycler.adapter = adapter

        val list = ArrayList<QiQiRecommendModel>()
        for (i in 0 until 5) {
            val model = QiQiRecommendModel()
            list.add(model)
        }
        adapter.upData(list)

        adapter.setMyListener { itemBean, position ->

        }
    }



}