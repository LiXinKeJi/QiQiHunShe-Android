package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.support.v7.widget.LinearLayoutManager
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.ui.mine.adapter.MyInvitationDetailsAdapter
import com.lxkj.qiqihunshe.app.ui.mine.model.MyInvitationDetailsModel
import com.lxkj.qiqihunshe.databinding.ActivityMyinvitationDetailsBinding

/**
 * Created by Slingge on 2019/2/25
 */
class MyInvitationDetailsViewModel : BaseViewModel() {

    private val adapterDai by lazy { MyInvitationDetailsAdapter(0) }
    private val adapterNow by lazy { MyInvitationDetailsAdapter(1) }
    private val adapterNo by lazy { MyInvitationDetailsAdapter(2) }


      var bind: ActivityMyinvitationDetailsBinding? = null

    fun init() {
        bind!!.rvDai.isFocusable=false
        bind!!.rvDai.layoutManager = LinearLayoutManager(fragment?.context)
        bind!!.rvDai.adapter = adapterDai

        var list = ArrayList<MyInvitationDetailsModel>()
        for (i in 0 until 5) {
            val model = MyInvitationDetailsModel()
            list.add(model)
        }
        adapterDai.upData(list)

        bind!!.rvNow.isFocusable=false
        bind!!.rvNow.layoutManager = LinearLayoutManager(fragment?.context)
        bind!!.rvNow.adapter = adapterNow

        list = ArrayList<MyInvitationDetailsModel>()
        for (i in 0 until 5) {
            val model = MyInvitationDetailsModel()
            list.add(model)
        }
        adapterNow.upData(list)

        bind!!.rvNo.isFocusable=false
        bind!!.rvNo.layoutManager = LinearLayoutManager(fragment?.context)
        bind!!.rvNo.adapter = adapterNo

        list = ArrayList<MyInvitationDetailsModel>()
        for (i in 0 until 5) {
            val model = MyInvitationDetailsModel()
            list.add(model)
        }
        adapterNo.upData(list)

    }


}