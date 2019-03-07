package com.lxkj.qiqihunshe.app.ui.mine.adapter

import android.content.Context
import com.lxkj.qiqihunshe.app.base.BaseListAdapter
import com.lxkj.qiqihunshe.app.ui.mine.model.QiQiDynamicModel
import com.lxkj.qiqihunshe.app.ui.mine.model.QiQiRuleModel
import com.lxkj.qiqihunshe.app.ui.mine.widget.QiQiDynamicItemView
import com.lxkj.qiqihunshe.app.ui.mine.widget.QiQiRuleItemView

/**
 * Created by Slingge on 2019/2/21
 */
class QiQiDynamicAdapter : BaseListAdapter<QiQiDynamicModel, QiQiDynamicItemView>() {


    override fun getitemView(context: Context): QiQiDynamicItemView {
        return QiQiDynamicItemView(context)
    }

    override fun refreshItemView(view: QiQiDynamicItemView, itembean: QiQiDynamicModel) {
        view.setData(itembean)
    }

}