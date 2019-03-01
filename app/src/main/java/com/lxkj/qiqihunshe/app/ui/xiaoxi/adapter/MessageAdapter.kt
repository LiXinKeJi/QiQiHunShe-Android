package com.lxkj.qiqihunshe.app.ui.xiaoxi.adapter

import android.content.Context
import com.lxkj.qiqihunshe.app.base.BaseListAdapter
import com.lxkj.qiqihunshe.app.ui.xiaoxi.model.MessageModel
import com.lxkj.qiqihunshe.app.ui.xiaoxi.widget.MessageItemView

/**
 * Created by Slingge on 2019/3/1
 */
class MessageAdapter : BaseListAdapter<MessageModel, MessageItemView>() {


    override fun getitemView(context: Context): MessageItemView {
        return MessageItemView(context)
    }

    override fun refreshItemView(view: MessageItemView, itembean: MessageModel) {
        view.setData(itembean)
    }


}