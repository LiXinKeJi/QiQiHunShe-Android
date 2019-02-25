package com.lxkj.qiqihunshe.app.ui.mine.adapter

import android.content.Context
import com.lxkj.qiqihunshe.app.base.BaseListAdapter
import com.lxkj.qiqihunshe.app.ui.mine.model.DynamicModel
import com.lxkj.qiqihunshe.app.ui.mine.widget.PersonDynamicItemView

/**
 * Created by Slingge on 2019/2/21
 */
class DynamicAdapter : BaseListAdapter<DynamicModel, PersonDynamicItemView>() {


    override fun getitemView(context: Context): PersonDynamicItemView {
        return PersonDynamicItemView(context)
    }

    override fun refreshItemView(view: PersonDynamicItemView, itembean: DynamicModel) {

    }


}