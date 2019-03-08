package com.lxkj.qiqihunshe.app.ui.mine.adapter

import android.content.Context
import com.lxkj.qiqihunshe.app.base.BaseListAdapter
import com.lxkj.qiqihunshe.app.ui.mine.model.ActivityRecordModel
import com.lxkj.qiqihunshe.app.ui.mine.model.SeenSkillModel
import com.lxkj.qiqihunshe.app.ui.mine.widget.ActivityRecordItemView
import com.lxkj.qiqihunshe.app.ui.mine.widget.SeenSkillItemView

/**
 * 活动记录
 * Created by Slingge on 2019/2/22
 */
class SeenSkillAdapter : BaseListAdapter<SeenSkillModel.dataModel, SeenSkillItemView>() {


    override fun getitemView(context: Context): SeenSkillItemView {
        return SeenSkillItemView(context)
    }

    override fun refreshItemView(view: SeenSkillItemView, itembean: SeenSkillModel.dataModel) {
        view.setData(itembean)
    }


}