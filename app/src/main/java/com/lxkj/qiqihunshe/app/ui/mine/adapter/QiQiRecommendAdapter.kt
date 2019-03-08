package com.lxkj.qiqihunshe.app.ui.mine.adapter

import android.content.Context
import com.lxkj.qiqihunshe.app.base.BaseListAdapter
import com.lxkj.qiqihunshe.app.ui.mine.model.QiQiRecommendModel
import com.lxkj.qiqihunshe.app.ui.mine.widget.QiQiRecommendItemView

/**
 * Created by Slingge on 2019/2/21
 */
class QiQiRecommendAdapter : BaseListAdapter<QiQiRecommendModel, QiQiRecommendItemView>() {
    override fun refreshItemView(view: QiQiRecommendItemView, itembean: QiQiRecommendModel, position: Int) {

    }


    override fun getitemView(context: Context): QiQiRecommendItemView {
        return QiQiRecommendItemView(context)
    }

    override fun refreshItemView(view: QiQiRecommendItemView, itembean: QiQiRecommendModel) {
    }


}