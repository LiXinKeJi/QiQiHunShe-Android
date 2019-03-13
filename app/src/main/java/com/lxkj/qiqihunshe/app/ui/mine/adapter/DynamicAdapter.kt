package com.lxkj.qiqihunshe.app.ui.mine.adapter

import android.app.Activity
import android.content.Context
import com.lxkj.qiqihunshe.app.base.BaseListAdapter
import com.lxkj.qiqihunshe.app.ui.mine.model.DynamicModel
import com.lxkj.qiqihunshe.app.ui.mine.model.SpaceDynamicModel
import com.lxkj.qiqihunshe.app.ui.mine.widget.PersonDynamicItemView

/**
 * Created by Slingge on 2019/2/21
 */
class DynamicAdapter : BaseListAdapter<SpaceDynamicModel.dataModel, PersonDynamicItemView>() {
    override fun refreshItemView(view: PersonDynamicItemView, itembean: SpaceDynamicModel.dataModel, position: Int) {
        view.setData(itembean, position)
    }

    var activity: Activity? = null


    override fun getitemView(context: Context): PersonDynamicItemView {
        return PersonDynamicItemView(activity!!, context)
    }

    override fun refreshItemView(view: PersonDynamicItemView, itembean: SpaceDynamicModel.dataModel) {

    }


}