package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.support.v7.widget.LinearLayoutManager
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.ui.mine.adapter.QiQiBlackListAdapter
import com.lxkj.qiqihunshe.app.ui.mine.model.QiQiBlackListModel
import com.lxkj.qiqihunshe.databinding.ActivityRecyvlerviewBinding

/**
 *
 * Created by Slingge on 2019/2/26
 */
class QiQiBlackListViewModel : BaseViewModel() {

    private val adapter by lazy { QiQiBlackListAdapter() }

    var bind: ActivityRecyvlerviewBinding? = null

    fun initViewModel() {
        bind!!.recycler.layoutManager = LinearLayoutManager(fragment?.context)

        bind!!.recycler.adapter = adapter

        val list = ArrayList<QiQiBlackListModel>()
        for (i in 0 until 5) {
            val model = QiQiBlackListModel()
            list.add(model)
        }
        adapter.upData(list)

    }


}