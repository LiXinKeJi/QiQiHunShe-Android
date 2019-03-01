package com.lxkj.qiqihunshe.app.ui.xiaoxi.viewmodel

import android.support.v7.widget.LinearLayoutManager
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.ui.mine.activity.InteractiveNotificationActivity
import com.lxkj.qiqihunshe.app.ui.xiaoxi.activity.QiQiRemindActivity
import com.lxkj.qiqihunshe.app.ui.xiaoxi.adapter.MessageAdapter
import com.lxkj.qiqihunshe.app.ui.xiaoxi.model.MessageModel
import com.lxkj.qiqihunshe.databinding.ActivityRecyvlerviewBinding

/**
 * Created by Slingge on 2019/2/28
 */
class XiangShiViewModel : BaseViewModel() {


    var bind: ActivityRecyvlerviewBinding? = null


    private val adapter by lazy { MessageAdapter() }

    fun initViewmodel() {
        bind!!.recycler.isFocusable = false
        bind!!.recycler.layoutManager = LinearLayoutManager(fragment?.context)

        bind!!.recycler.adapter = adapter

        val list = ArrayList<MessageModel>()
        for (i in 0 until 5) {
            val model = MessageModel()
            if (i < 2) {
                model.system = "0"
                if (i == 0) {
                    model.title = "互动通知"
                } else if (i == 1) {
                    model.title = "小七提醒"
                }
            } else {
                model.system = "1"
            }
            list.add(model)
        }
        adapter.upData(list)

        adapter.setMyListener { itemBean, position ->
            if (itemBean.title == "互动通知") {
                MyApplication.openActivity(fragment?.context, InteractiveNotificationActivity::class.java)
            } else if (itemBean.title == "小七提醒") {
                MyApplication.openActivity(fragment?.context, QiQiRemindActivity::class.java)
            }
        }
    }


}