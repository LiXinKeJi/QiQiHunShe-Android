package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.support.v7.widget.LinearLayoutManager
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.ui.mine.activity.MyInvitationDetailsActivity
import com.lxkj.qiqihunshe.app.ui.mine.adapter.SpaceInvitationAdapter
import com.lxkj.qiqihunshe.app.ui.mine.model.SpaceInvitationModel
import com.lxkj.qiqihunshe.databinding.ActivityRecyvlerviewBinding

/**
 * 我的情感-我的征婚
 * Created by Slingge on 2019/2/26
 */
class MyMarriageViewModel:BaseViewModel() {

    private val adapter by lazy { SpaceInvitationAdapter() }

    var bind: ActivityRecyvlerviewBinding? = null

    fun initViewModel() {
        bind!!.recycler.layoutManager = LinearLayoutManager(fragment?.context)
        bind!!.recycler.adapter = adapter

        val list = ArrayList<SpaceInvitationModel>()
        for (i in 0 until 5) {
            val model = SpaceInvitationModel()
            list.add(model)
        }
        adapter.upData(list)

        adapter.setMyListener { itemBean, position ->
            MyApplication.openActivity(fragment?.context, MyInvitationDetailsActivity::class.java)
        }
    }


}