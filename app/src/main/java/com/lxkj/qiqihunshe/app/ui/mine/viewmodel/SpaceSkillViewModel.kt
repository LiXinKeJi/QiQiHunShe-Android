package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.support.v7.widget.LinearLayoutManager
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.ui.mine.adapter.SpaceSkillAdapter
import com.lxkj.qiqihunshe.app.ui.mine.model.SpaceSkillModel
import com.lxkj.qiqihunshe.databinding.FragmentSpaceSkillBinding

/**
 * 我的空间- 我的才艺
 * Created by Slingge on 2019/2/25
 */
class SpaceSkillViewModel:BaseViewModel(){


    private val adapter by lazy { SpaceSkillAdapter() }

    var bind: FragmentSpaceSkillBinding? = null

    fun initViewModel() {
        bind!!.recycler.layoutManager = LinearLayoutManager(fragment?.context)
        bind!!.recycler.adapter = adapter

        val list = ArrayList<SpaceSkillModel>()
        for (i in 0 until 5) {
            val model = SpaceSkillModel()
            list.add(model)
        }
        adapter.upData(list)

    }


}