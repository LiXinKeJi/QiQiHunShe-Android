package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.support.v7.widget.LinearLayoutManager
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.ui.mine.adapter.AffectiveMarriageAdapter
import com.lxkj.qiqihunshe.app.ui.mine.model.InvitationModel
import com.lxkj.qiqihunshe.databinding.ActivityRecyvlerviewBinding

/**
 * 情感征婚
 * Created by Slingge on 2019/2/25
 */
class AffectiveMarriageViewModel:BaseViewModel() {

    private val adapter by lazy { AffectiveMarriageAdapter() }

    var bind: ActivityRecyvlerviewBinding? = null

    fun initViewModel() {
        bind!!.recycler.isFocusable = false
        bind!!.recycler.layoutManager = LinearLayoutManager(fragment?.context)

        bind!!.recycler.adapter = adapter

        val list = ArrayList<InvitationModel>()
        for (i in 0 until 5) {
            val model = InvitationModel()
            list.add(model)
        }
        adapter.upData(list)

        adapter.setMyListener { itemBean, position ->

        }
    }


}