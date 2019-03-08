package com.lxkj.qiqihunshe.app.ui.shouye.adapter

import android.content.Context
import com.lxkj.qiqihunshe.app.base.BaseListAdapter
import com.lxkj.qiqihunshe.app.ui.shouye.model.MatchingHistoryModel
import com.lxkj.qiqihunshe.app.ui.shouye.widget.MatchingHistoryItemView

/**
 * 活动记录
 * Created by Slingge on 2019/2/22
 */
class MatchingHistoryAdapter(val type: Int) : BaseListAdapter<MatchingHistoryModel, MatchingHistoryItemView>() {
    override fun refreshItemView(view: MatchingHistoryItemView, itembean: MatchingHistoryModel, position: Int) {

    }


    override fun getitemView(context: Context): MatchingHistoryItemView {
        return MatchingHistoryItemView(context, type)
    }

    override fun refreshItemView(view: MatchingHistoryItemView, itembean: MatchingHistoryModel) {
        view.setData(itembean)
    }


}