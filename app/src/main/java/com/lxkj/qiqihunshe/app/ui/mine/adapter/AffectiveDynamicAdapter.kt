package com.lxkj.qiqihunshe.app.ui.mine.adapter

import android.content.Context
import com.lxkj.qiqihunshe.app.base.BaseListAdapter
import com.lxkj.qiqihunshe.app.ui.mine.model.SpaceDynamicModel
import com.lxkj.qiqihunshe.app.ui.mine.widget.AffectiveDynamicItemView

/**
 * 我的空间-我的动态
 * Created by Slingge on 2019/2/22
 */
class AffectiveDynamicAdapter : BaseListAdapter<SpaceDynamicModel.dataModel, AffectiveDynamicItemView>() {


    override fun getitemView(context: Context): AffectiveDynamicItemView {
        return AffectiveDynamicItemView(context)
    }

    override fun refreshItemView(view: AffectiveDynamicItemView, itembean: SpaceDynamicModel.dataModel) {

    }
    override fun refreshItemView(view: AffectiveDynamicItemView, itembean: SpaceDynamicModel.dataModel, position: Int) {
        view.setData(itembean, position)
    }


}