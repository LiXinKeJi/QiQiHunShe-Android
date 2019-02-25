package com.lxkj.qiqihunshe.app.ui.mine.adapter

import android.content.Context
import com.lxkj.qiqihunshe.app.base.BaseListAdapter
import com.lxkj.qiqihunshe.app.ui.mine.model.InvitationModel
import com.lxkj.qiqihunshe.app.ui.mine.widget.PersonInvitationItemView

/**
 * Created by Slingge on 2019/2/21
 */
class AffectiveMarriageAdapter : BaseListAdapter<InvitationModel, PersonInvitationItemView>() {


    override fun getitemView(context: Context): PersonInvitationItemView {
        return PersonInvitationItemView(context)
    }

    override fun refreshItemView(view: PersonInvitationItemView, itembean: InvitationModel) {
    }


}