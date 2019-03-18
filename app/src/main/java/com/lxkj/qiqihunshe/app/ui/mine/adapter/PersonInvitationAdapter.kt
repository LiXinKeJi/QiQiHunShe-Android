package com.lxkj.qiqihunshe.app.ui.mine.adapter

import android.app.Activity
import android.content.Context
import com.lxkj.qiqihunshe.app.base.BaseListAdapter
import com.lxkj.qiqihunshe.app.ui.mine.model.InvitationModel
import com.lxkj.qiqihunshe.app.ui.mine.model.SpaceInvitationModel
import com.lxkj.qiqihunshe.app.ui.mine.widget.PersonInvitationItemView

/**
 * Created by Slingge on 2019/2/21
 */
class PersonInvitationAdapter : BaseListAdapter<SpaceInvitationModel.dataModel, PersonInvitationItemView>() {
    override fun refreshItemView(
        view: PersonInvitationItemView,
        itembean: SpaceInvitationModel.dataModel,
        position: Int
    ) {
        view.setData(itembean, position)
    }

    var activity: Activity? = null

    override fun getitemView(context: Context): PersonInvitationItemView {
        return PersonInvitationItemView(activity!!, context)
    }

    override fun refreshItemView(view: PersonInvitationItemView, itembean: SpaceInvitationModel.dataModel) {
    }


}