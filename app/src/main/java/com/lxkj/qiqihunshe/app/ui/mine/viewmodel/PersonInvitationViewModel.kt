package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.support.v7.widget.LinearLayoutManager
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.ui.mine.adapter.DynamicAdapter
import com.lxkj.qiqihunshe.app.ui.mine.adapter.PersonInvitationAdapter
import com.lxkj.qiqihunshe.app.ui.mine.model.DynamicModel
import com.lxkj.qiqihunshe.app.ui.mine.model.InvitationModel
import com.lxkj.qiqihunshe.databinding.FragmentPersonInvitationBinding

/**
 * Created by Slingge on 2019/2/21
 */
class PersonInvitationViewModel:BaseViewModel(){


    private val adapter by lazy { PersonInvitationAdapter() }

    var bind: FragmentPersonInvitationBinding? = null

    fun initViewModel() {
        bind!!.rvDynamic.layoutManager = LinearLayoutManager(fragment?.context)

        bind!!.rvDynamic.adapter = adapter

        val list = ArrayList<InvitationModel>()
        for (i in 0 until 5) {
            val model = InvitationModel()
            list.add(model)
        }
        adapter.upData(list)

    }

}