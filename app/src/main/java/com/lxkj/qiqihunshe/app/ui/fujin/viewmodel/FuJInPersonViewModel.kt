package com.lxkj.qiqihunshe.app.ui.fujin.viewmodel

import android.support.v7.widget.LinearLayoutManager
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.ui.mine.activity.PersonalInfoActivity
import com.lxkj.qiqihunshe.app.ui.shouye.activity.VoiceChatAnswerActivity
import com.lxkj.qiqihunshe.app.ui.shouye.adapter.MatchingHistoryAdapter
import com.lxkj.qiqihunshe.app.ui.shouye.model.MatchingHistoryModel
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.ActivityMatchHistoryBinding
import com.lxkj.qiqihunshe.databinding.ActivityRecyvlerviewBinding

/**
 * Created by Slingge on 2019/2/26
 */
class FuJInPersonViewModel:BaseViewModel() {

    private val adapter by lazy { MatchingHistoryAdapter(-1) }

    var bind: ActivityRecyvlerviewBinding? = null


    fun initViewModel() {
        bind!!.recycler.isFocusable = false
        bind!!.recycler.layoutManager = LinearLayoutManager(fragment?.context)

        bind!!.recycler.adapter = adapter

        val list = ArrayList<MatchingHistoryModel>()
        for (i in 0 until 5) {
            val model = MatchingHistoryModel()
            list.add(model)
        }
        adapter.upData(list)

        adapter.setMyListener { itemBean, position ->

        }
    }


}