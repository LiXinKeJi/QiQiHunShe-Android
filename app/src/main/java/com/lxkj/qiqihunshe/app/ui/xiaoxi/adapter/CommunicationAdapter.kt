package com.lxkj.qiqihunshe.app.ui.xiaoxi.adapter

import android.content.Context
import com.lxkj.qiqihunshe.app.base.BaseListAdapter
import com.lxkj.qiqihunshe.app.ui.xiaoxi.model.MessageModel
import com.lxkj.qiqihunshe.app.ui.xiaoxi.widget.CommunicationItemView
import com.lxkj.qiqihunshe.app.ui.xiaoxi.widget.MessageItemView

/**
 * Created by Slingge on 2019/3/1
 */
class CommunicationAdapter : BaseListAdapter<MessageModel, CommunicationItemView>() {
    override fun refreshItemView(view: CommunicationItemView, itembean: MessageModel, position: Int) {

    }


    override fun getitemView(context: Context): CommunicationItemView {
        return CommunicationItemView(context)
    }

    override fun refreshItemView(view: CommunicationItemView, itembean: MessageModel) {
        view.setData(itembean)
    }



}