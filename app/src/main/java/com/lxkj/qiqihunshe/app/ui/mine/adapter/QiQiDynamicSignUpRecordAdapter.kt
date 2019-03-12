package com.lxkj.qiqihunshe.app.ui.mine.adapter

import android.content.Context
import com.lxkj.qiqihunshe.app.base.BaseListAdapter
import com.lxkj.qiqihunshe.app.ui.mine.model.QiQiDynamicModel
import com.lxkj.qiqihunshe.app.ui.mine.model.QiQiRuleModel
import com.lxkj.qiqihunshe.app.ui.mine.widget.QiQiDynamicItemView
import com.lxkj.qiqihunshe.app.ui.mine.widget.QiQiDynamicSignUpRecordItemView
import com.lxkj.qiqihunshe.app.ui.mine.widget.QiQiRuleItemView

/**
 * Created by Slingge on 2019/2/21
 */
class QiQiDynamicSignUpRecordAdapter : BaseListAdapter<QiQiDynamicModel.dataModel, QiQiDynamicSignUpRecordItemView>() {
    override fun refreshItemView(
        view: QiQiDynamicSignUpRecordItemView,
        itembean: QiQiDynamicModel.dataModel,
        position: Int
    ) {
        view.setData(itembean,position)
    }


    override fun getitemView(context: Context): QiQiDynamicSignUpRecordItemView {
        return QiQiDynamicSignUpRecordItemView(context)
    }

    override fun refreshItemView(view: QiQiDynamicSignUpRecordItemView, itembean: QiQiDynamicModel.dataModel) {
    }

}