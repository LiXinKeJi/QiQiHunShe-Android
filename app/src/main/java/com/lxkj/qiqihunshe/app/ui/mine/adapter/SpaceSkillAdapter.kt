package com.lxkj.qiqihunshe.app.ui.mine.adapter

import android.content.Context
import com.lxkj.qiqihunshe.app.base.BaseListAdapter
import com.lxkj.qiqihunshe.app.ui.mine.model.SpaceSkillModel
import com.lxkj.qiqihunshe.app.ui.mine.widget.SpaceSkillItemView

/**
 * 我的空间-我的动态
 * Created by Slingge on 2019/2/22
 */
class SpaceSkillAdapter : BaseListAdapter<SpaceSkillModel.dataModel, SpaceSkillItemView>() {


    override fun getitemView(context: Context): SpaceSkillItemView {
        return SpaceSkillItemView(context)
    }

    override fun refreshItemView(view: SpaceSkillItemView, itembean: SpaceSkillModel.dataModel) {
        view.setData(itembean,position)
    }


}