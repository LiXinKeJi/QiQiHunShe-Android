package com.lxkj.qiqihunshe.app.ui.mine.adapter

import android.content.Context
import com.lxkj.qiqihunshe.app.base.BaseListAdapter
import com.lxkj.qiqihunshe.app.ui.mine.model.SpaceInvitationModel
import com.lxkj.qiqihunshe.app.ui.mine.widget.SpaceInvitationitenView

/**
 * 我的空间-我的动态
 * Created by Slingge on 2019/2/22
 */
class SpaceInvitationAdapter : BaseListAdapter<SpaceInvitationModel.dataModel, SpaceInvitationitenView>() {
    override fun refreshItemView(
        view: SpaceInvitationitenView,
        itembean: SpaceInvitationModel.dataModel,
        position: Int
    ) {
        view.setData(itembean, position)
    }


    override fun getitemView(context: Context): SpaceInvitationitenView {
        return SpaceInvitationitenView(context)
    }

    override fun refreshItemView(view: SpaceInvitationitenView, itembean: SpaceInvitationModel.dataModel) {

    }


}