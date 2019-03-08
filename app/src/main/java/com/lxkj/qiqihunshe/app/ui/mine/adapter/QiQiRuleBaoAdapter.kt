package com.lxkj.qiqihunshe.app.ui.mine.adapter

import android.content.Context
import com.lxkj.qiqihunshe.app.base.BaseListAdapter
import com.lxkj.qiqihunshe.app.ui.mine.model.QiQiRuleModel
import com.lxkj.qiqihunshe.app.ui.mine.widget.QiQiRuleItemView

/**
 * Created by Slingge on 2019/2/21
 */
class QiQiRuleBaoAdapter : BaseListAdapter<QiQiRuleModel, QiQiRuleItemView>() {
    override fun refreshItemView(view: QiQiRuleItemView, itembean: QiQiRuleModel, position: Int) {

    }


    override fun getitemView(context: Context): QiQiRuleItemView {
        return QiQiRuleItemView(context)
    }

    override fun refreshItemView(view: QiQiRuleItemView, itembean: QiQiRuleModel) {
        view.setData(itembean)
    }

}