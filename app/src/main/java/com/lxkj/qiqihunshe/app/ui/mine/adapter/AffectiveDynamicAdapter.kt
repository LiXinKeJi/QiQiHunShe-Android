package com.lxkj.qiqihunshe.app.ui.mine.adapter

import android.content.Context
import com.lxkj.qiqihunshe.app.base.BaseListAdapter
import com.lxkj.qiqihunshe.app.ui.mine.model.DynamicModel
import com.lxkj.qiqihunshe.app.ui.mine.model.SpaceDynamicModel
import com.lxkj.qiqihunshe.app.ui.mine.widget.AffectiveDynamicItemView
import com.lxkj.qiqihunshe.app.ui.mine.widget.SpaceDynamicItemView

/**
 * 我的空间-我的动态
 * Created by Slingge on 2019/2/22
 */
class AffectiveDynamicAdapter : BaseListAdapter<DynamicModel, AffectiveDynamicItemView>() {


    override fun getitemView(context: Context): AffectiveDynamicItemView {
        return AffectiveDynamicItemView(context)
    }

    override fun refreshItemView(view: AffectiveDynamicItemView, itembean: DynamicModel) {
        view.setData(itembean)
    }


}