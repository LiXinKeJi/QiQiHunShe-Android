package com.lxkj.qiqihunshe.app.ui.xiaoxi.adapter

import android.content.Context
import com.lxkj.qiqihunshe.app.base.BaseListAdapter
import com.lxkj.qiqihunshe.app.ui.xiaoxi.model.MessageModel
import com.lxkj.qiqihunshe.app.ui.xiaoxi.widget.NewFriendItemView

/**
 * Created by Slingge on 2019/3/1
 */
class NewFriendAdapter : BaseListAdapter<MessageModel, NewFriendItemView>() {


    override fun getitemView(context: Context): NewFriendItemView {
        return NewFriendItemView(context)
    }

    override fun refreshItemView(view: NewFriendItemView, itembean: MessageModel) {
        view.setData(itembean)
    }


}