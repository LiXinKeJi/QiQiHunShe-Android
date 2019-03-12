package com.lxkj.qiqihunshe.app.ui.mine.adapter

import android.content.Context
import com.lxkj.qiqihunshe.app.base.BaseListAdapter
import com.lxkj.qiqihunshe.app.ui.mine.model.MyInvitationDetailsModel
import com.lxkj.qiqihunshe.app.ui.mine.widget.MyInvitationDetailsItemView

/**
 * Created by Slingge on 2019/2/25
 */
class MyInvitationDetailsAdapter(val type: Int) :
    BaseListAdapter<MyInvitationDetailsModel.dataModel, MyInvitationDetailsItemView>() {
    override fun refreshItemView(
        view: MyInvitationDetailsItemView,
        itembean: MyInvitationDetailsModel.dataModel,
        position: Int
    ) {
        view.setData(itembean, position)
    }


    override fun getitemView(context: Context): MyInvitationDetailsItemView {
        return MyInvitationDetailsItemView(context, type)
    }

    override fun refreshItemView(view: MyInvitationDetailsItemView, itembean: MyInvitationDetailsModel.dataModel) {

    }


}