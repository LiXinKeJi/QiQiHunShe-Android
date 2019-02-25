package com.lxkj.qiqihunshe.app.ui.mine.adapter

import android.content.Context
import com.lxkj.qiqihunshe.app.base.BaseListAdapter
import com.lxkj.qiqihunshe.app.ui.mine.model.SpaceDynamicModel
import com.lxkj.qiqihunshe.app.ui.mine.widget.SpaceDynamicItemView

/**
 * 我的空间-我的动态
 * Created by Slingge on 2019/2/22
 */
class SpaceDynamicAdapter : BaseListAdapter<SpaceDynamicModel, SpaceDynamicItemView>() {


    override fun getitemView(context: Context): SpaceDynamicItemView {
        return SpaceDynamicItemView(context)
    }

    override fun refreshItemView(view: SpaceDynamicItemView, itembean: SpaceDynamicModel) {
        view.setData(itembean)
    }


}