package com.lxkj.qiqihunshe.app.ui.fujin.viewmodel

import android.support.v7.widget.LinearLayoutManager
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.ui.mine.adapter.CommentAdapter
import com.lxkj.qiqihunshe.app.ui.mine.model.CommentModel
import com.lxkj.qiqihunshe.databinding.FragmentFujinSkillBinding

/**
 * Created by Slingge on 2019/2/27
 */
class FuJinSkillViewModel:BaseViewModel() {

    private val adapter by lazy { CommentAdapter() }

    var bind: FragmentFujinSkillBinding? = null

    fun initViewModel() {
        bind!!.rvComment.layoutManager = LinearLayoutManager(fragment?.context)
        bind!!.rvComment.adapter = adapter

        val list = ArrayList<CommentModel>()
        for (i in 0 until 5) {
            val model = CommentModel()
            list.add(model)
        }
        adapter.upData(list)

    }



}