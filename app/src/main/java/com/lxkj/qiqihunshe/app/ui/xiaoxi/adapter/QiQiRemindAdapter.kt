package com.lxkj.qiqihunshe.app.ui.xiaoxi.adapter

import android.content.Context
import com.lxkj.qiqihunshe.app.base.BaseListAdapter
import com.lxkj.qiqihunshe.app.ui.xiaoxi.model.QiQiRemindModel
import com.lxkj.qiqihunshe.app.ui.xiaoxi.widget.QiQiRemindItemView

/**
 * Created by Slingge on 2019/3/1
 */
class QiQiRemindAdapter : BaseListAdapter<QiQiRemindModel, QiQiRemindItemView>() {
    override fun refreshItemView(view: QiQiRemindItemView, itembean: QiQiRemindModel, position: Int) {

    }


    override fun getitemView(context: Context): QiQiRemindItemView {
        return QiQiRemindItemView(context)
    }

    override fun refreshItemView(view: QiQiRemindItemView, itembean: QiQiRemindModel) {
        view.setData(itembean)
    }


}