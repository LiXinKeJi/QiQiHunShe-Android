package com.lxkj.qiqihunshe.app.ui.xiaoxi.viewmodel

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.ui.xiaoxi.adapter.MessageAdapter
import com.lxkj.qiqihunshe.app.ui.xiaoxi.model.MessageModel
import com.lxkj.qiqihunshe.app.util.RecyclerItemTouchListener
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.ActivityRecyvlerviewBinding
import io.rong.imkit.RongIM

/**
 * Created by Slingge on 2019/3/1
 */
class YueHuiVieModel : BaseViewModel() {


    var bind: ActivityRecyvlerviewBinding? = null


    private val adapter by lazy { MessageAdapter() }

    fun initViewmodel() {
        bind!!.recycler.isFocusable = false
        bind!!.recycler.layoutManager = LinearLayoutManager(fragment?.context)

        bind!!.recycler.adapter = adapter

        val list = ArrayList<MessageModel>()
        for (i in 0 until 5) {
            val model = MessageModel()
            if (i < 3) {
                model.flag = "1"
            }
            list.add(model)
        }
        adapter.upData(list)

        bind!!.recycler.addOnItemTouchListener(object :RecyclerItemTouchListener(bind!!.recycler){
            override fun onItemClick(vh: RecyclerView.ViewHolder?) {
                RongIM.getInstance().startPrivateChat(fragment?.activity, "9527", "标题")
            }
        })
    }

}