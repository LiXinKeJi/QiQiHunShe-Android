package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.support.v7.widget.GridLayoutManager
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.ui.mine.adapter.SeenSkillAdapter
import com.lxkj.qiqihunshe.app.ui.mine.model.SeenSkillModel
import com.lxkj.qiqihunshe.databinding.ActivityRecyvlerviewBinding

/**
 * Created by Slingge on 2019/2/25
 */
class SeenSkillViewModel :BaseViewModel(){


    private val adapter by lazy { SeenSkillAdapter() }

    var bind: ActivityRecyvlerviewBinding? = null

    fun initViewModel() {
        bind!!.recycler.layoutManager = GridLayoutManager(fragment?.context,2)
        bind!!.recycler.adapter = adapter

        val list = ArrayList<SeenSkillModel>()
        for (i in 0 until 5) {
            val model = SeenSkillModel()
            list.add(model)
        }
        adapter.upData(list)

    }



}