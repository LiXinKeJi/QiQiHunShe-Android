package com.lxkj.qiqihunshe.app.ui.mine.adapter

import android.content.Context
import com.lxkj.qiqihunshe.app.base.BaseListAdapter
import com.lxkj.qiqihunshe.app.ui.mine.model.QiQiBlackListModel
import com.lxkj.qiqihunshe.app.ui.mine.widget.QiQiBlackListItemView

/**
 * 黑名单
 * Created by Slingge on 2019/2/22
 */
class QiQiBlackListAdapter : BaseListAdapter<QiQiBlackListModel, QiQiBlackListItemView>() {


    override fun getitemView(context: Context): QiQiBlackListItemView {
        return QiQiBlackListItemView(context)
    }

    override fun refreshItemView(view: QiQiBlackListItemView, itembean: QiQiBlackListModel) {
        view.setData(itembean)
    }


}