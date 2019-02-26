package com.lxkj.qiqihunshe.app.ui.shouye.viewmodel

import android.support.v7.widget.LinearLayoutManager
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.ui.mine.activity.PersonalInfoActivity
import com.lxkj.qiqihunshe.app.ui.shouye.activity.VoiceChatAnswerActivity
import com.lxkj.qiqihunshe.app.ui.shouye.activity.VoiceChatDialActivity
import com.lxkj.qiqihunshe.app.ui.shouye.adapter.MatchingHistoryAdapter
import com.lxkj.qiqihunshe.app.ui.shouye.model.MatchingHistoryModel
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.ActivityMatchHistoryBinding

/**
 * Created by Slingge on 2019/2/26
 */
class MatchingHistoryViewModel : BaseViewModel() {

    private val adapter by lazy { MatchingHistoryAdapter(flag) }

    var bind: ActivityMatchHistoryBinding? = null

    var flag = -1//0聊，1语音

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
            when (flag) {
                0 -> ToastUtil.showToast("跳转聊天")
                1 -> {
                    MyApplication.openActivity(activity, VoiceChatAnswerActivity::class.java)//接听
//                    MyApplication.openActivity(activity, VoiceChatDialActivity::class.java)//拨打
                }
                2 -> MyApplication.openActivity(activity, PersonalInfoActivity::class.java)
            }

        }
    }

}