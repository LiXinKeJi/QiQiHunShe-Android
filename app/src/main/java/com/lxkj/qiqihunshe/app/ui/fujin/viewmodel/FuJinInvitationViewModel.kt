package com.lxkj.qiqihunshe.app.ui.fujin.viewmodel

import android.support.v7.widget.LinearLayoutManager
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.ui.mine.activity.PersonInvitationDetailsActivity
import com.lxkj.qiqihunshe.app.ui.mine.adapter.PersonInvitationAdapter
import com.lxkj.qiqihunshe.app.ui.mine.model.InvitationModel
import com.lxkj.qiqihunshe.databinding.FragmentFujinInvitationBinding

/**
 * Created by Slingge on 2019/2/26
 */
class FuJinInvitationViewModel : BaseViewModel() {

    private val adapter by lazy { PersonInvitationAdapter() }

    var bind: FragmentFujinInvitationBinding? = null

    fun initViewModel() {
        bind!!.recycler.layoutManager = LinearLayoutManager(fragment?.context)

        bind!!.recycler.adapter = adapter

        val list = ArrayList<InvitationModel>()
        for (i in 0 until 5) {
            val model = InvitationModel()
            list.add(model)
        }
        adapter.upData(list)

        adapter.setMyListener { itemBean, position ->
            MyApplication.openActivity(fragment?.context, PersonInvitationDetailsActivity::class.java)
        }

    }

}