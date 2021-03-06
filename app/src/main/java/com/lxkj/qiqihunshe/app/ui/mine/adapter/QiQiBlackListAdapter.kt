package com.lxkj.qiqihunshe.app.ui.mine.adapter

import android.content.Context
import com.lxkj.qiqihunshe.app.base.BaseListAdapter
import com.lxkj.qiqihunshe.app.ui.mine.model.QiQiBlackListModel
import com.lxkj.qiqihunshe.app.ui.mine.widget.QiQiBlackListItemView

/**
 * 黑名单
 * Created by Slingge on 2019/2/22
 */
class QiQiBlackListAdapter(val type: Int) : BaseListAdapter<QiQiBlackListModel.dataModel, QiQiBlackListItemView>() {

    override fun refreshItemView(view: QiQiBlackListItemView, itembean: QiQiBlackListModel.dataModel, position: Int) {
        view.setData(itembean, position)
    }


    override fun getitemView(context: Context): QiQiBlackListItemView {
        return QiQiBlackListItemView(context, type)
    }

    override fun refreshItemView(view: QiQiBlackListItemView, itembean: QiQiBlackListModel.dataModel) {

    }


}