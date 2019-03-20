package com.lxkj.qiqihunshe.app.ui.shouye.adapter

import android.content.Context
import com.lxkj.qiqihunshe.app.base.BaseListAdapter
import com.lxkj.qiqihunshe.app.ui.shouye.model.SetupProblemModel
import com.lxkj.qiqihunshe.app.ui.shouye.widget.CheckBoxItemView

/**
 * 活动记录
 * Created by Slingge on 2019/2/22
 */
class CheckBoxAdapter : BaseListAdapter<SetupProblemModel.answerModel, CheckBoxItemView>() {
    override fun refreshItemView(view: CheckBoxItemView, itembean: SetupProblemModel.answerModel, position: Int) {
        view.setData(itembean, position)
    }


    override fun getitemView(context: Context): CheckBoxItemView {
        return CheckBoxItemView(context)
    }

    override fun refreshItemView(view: CheckBoxItemView, itembean: SetupProblemModel.answerModel) {

    }


}