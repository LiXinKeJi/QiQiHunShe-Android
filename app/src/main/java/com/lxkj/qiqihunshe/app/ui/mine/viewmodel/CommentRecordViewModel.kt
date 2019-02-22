package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.support.v7.widget.LinearLayoutManager
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.ui.mine.adapter.CommentRecordAdapter
import com.lxkj.qiqihunshe.app.ui.mine.model.CommentRecordModel
import com.lxkj.qiqihunshe.databinding.ActivityRecyvlerviewBinding

/**
 *  点评记录
 * Created by Slingge on 2019/2/22
 */
class CommentRecordViewModel : BaseViewModel() {


    private val adapter by lazy { CommentRecordAdapter() }

    var bind: ActivityRecyvlerviewBinding? = null

    fun initViewModel() {
        bind!!.recycler.isFocusable = false
        bind!!.recycler.layoutManager = LinearLayoutManager(fragment?.context)

        bind!!.recycler.adapter = adapter

        val list = ArrayList<CommentRecordModel>()
        for (i in 0 until 5) {
            val model = CommentRecordModel()
            list.add(model)
        }
        adapter.upData(list)

        adapter.setMyListener { itemBean, position ->

        }
    }


}