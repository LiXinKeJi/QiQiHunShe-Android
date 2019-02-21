package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.support.v7.widget.GridLayoutManager
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.ui.mine.adapter.PersonSkillAdapter
import com.lxkj.qiqihunshe.app.ui.mine.model.PersonaSkillModel
import com.lxkj.qiqihunshe.databinding.FragmentPersonSkillBinding

/**
 * 才艺
 * Created by Slingge on 2019/2/21
 */
class PersonSkillViewModel:BaseViewModel(){

    private val adapter by lazy { PersonSkillAdapter() }

    var bind: FragmentPersonSkillBinding? = null

    fun initViewModel() {
        bind!!.rvDynamic.layoutManager = GridLayoutManager(fragment?.context,2)

        bind!!.rvDynamic.adapter = adapter

        val list = ArrayList<PersonaSkillModel>()
        for (i in 0 until 5) {
            val model = PersonaSkillModel()
            list.add(model)
        }
        adapter.upData(list)

    }


}