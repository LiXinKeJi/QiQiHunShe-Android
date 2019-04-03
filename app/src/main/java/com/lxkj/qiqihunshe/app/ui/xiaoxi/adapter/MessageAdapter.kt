package com.lxkj.qiqihunshe.app.ui.xiaoxi.adapter

import android.app.Activity
import android.content.Context
import com.lxkj.qiqihunshe.app.base.BaseListAdapter
import com.lxkj.qiqihunshe.app.ui.xiaoxi.model.FindUserRelationshipModel
import com.lxkj.qiqihunshe.app.ui.xiaoxi.widget.MessageItemView

/**
 * Created by Slingge on 2019/3/1
 */
class MessageAdapter(val activity: Activity) : BaseListAdapter<FindUserRelationshipModel.dataModel, MessageItemView>() {


    override fun refreshItemView(view: MessageItemView, itembean: FindUserRelationshipModel.dataModel, position: Int) {
        view.setData(itembean, position)
    }


    override fun getitemView(context: Context): MessageItemView {
        return MessageItemView(context, activity)
    }

    override fun refreshItemView(view: MessageItemView, itembean: FindUserRelationshipModel.dataModel) {

    }


}