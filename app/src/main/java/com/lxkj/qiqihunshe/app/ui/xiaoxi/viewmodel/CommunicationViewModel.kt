package com.lxkj.qiqihunshe.app.ui.xiaoxi.viewmodel

import android.support.v7.widget.LinearLayoutManager
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.ui.xiaoxi.activity.NewFirendActivity
import com.lxkj.qiqihunshe.app.ui.xiaoxi.adapter.CommunicationAdapter
import com.lxkj.qiqihunshe.app.ui.xiaoxi.adapter.MessageAdapter
import com.lxkj.qiqihunshe.app.ui.xiaoxi.model.MessageModel
import com.lxkj.qiqihunshe.databinding.ActivityRecyvlerviewBinding

/**
 * Created by Slingge on 2019/2/28
 */
class CommunicationViewModel : BaseViewModel() {



    var bind: ActivityRecyvlerviewBinding? = null


    private val adapter by lazy { CommunicationAdapter() }

    fun initViewmodel() {
        bind!!.recycler.isFocusable = false
        bind!!.recycler.layoutManager = LinearLayoutManager(fragment?.context)

        bind!!.recycler.adapter = adapter

        val list = ArrayList<MessageModel>()
        for (i in 0 until 5) {
            val model = MessageModel()
            if (i < 1) {
                model.system = "0"
            }else{
                model.system = "1"
            }
            list.add(model)
        }
        adapter.upData(list)

        adapter.setMyListener { itemBean, position ->
            if(itemBean.system=="0"){
                MyApplication.openActivity(fragment?.context,NewFirendActivity::class.java)
            }
        }
    }


}