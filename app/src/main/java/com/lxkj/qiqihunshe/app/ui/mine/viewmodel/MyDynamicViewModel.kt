package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.ui.mine.adapter.CommentAdapter
import com.lxkj.qiqihunshe.app.ui.mine.adapter.ImageAdapter
import com.lxkj.qiqihunshe.app.ui.mine.model.CommentModel
import com.lxkj.qiqihunshe.databinding.ActivityMydynamicBinding
import com.lxkj.qiqihunshe.databinding.ActivityRecyvlerviewBinding

/**
 * Created by Slingge on 2019/2/25
 */
class MyDynamicViewModel : BaseViewModel() {

    private val adapter by lazy { CommentAdapter() }
    private val imageAdapter by lazy { ImageAdapter() }

    var bind: ActivityMydynamicBinding? = null

    fun initViewModel() {
        bind!!.rvComment.layoutManager = LinearLayoutManager(fragment?.context)
        bind!!.rvComment.adapter = adapter

        val list = ArrayList<CommentModel>()
        for (i in 0 until 5) {
            val model = CommentModel()
            list.add(model)
        }
        adapter.upData(list)


        bind!!.rvImage.layoutManager = GridLayoutManager(fragment?.context, 3)
        bind!!.rvImage.adapter = imageAdapter

        val lists = ArrayList<String>()
        for (i in 0 until 5) {
            lists.add("")
        }
        imageAdapter.upData(lists)
        imageAdapter.flag=1
    }

}