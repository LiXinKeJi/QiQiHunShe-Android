package com.lxkj.qiqihunshe.app.ui.mine.adapter

import android.content.Context
import com.lxkj.qiqihunshe.app.base.BaseListAdapter
import com.lxkj.qiqihunshe.app.ui.mine.model.ReputationBaoModel
import com.lxkj.qiqihunshe.app.ui.mine.widget.ReputationBaoItemView

/**
 * Created by Slingge on 2019/2/21
 */
class ReputationBaoAdapter : BaseListAdapter<ReputationBaoModel.dataModel, ReputationBaoItemView>() {


    override fun getitemView(context: Context): ReputationBaoItemView {
        return ReputationBaoItemView(context)
    }

    override fun refreshItemView(view: ReputationBaoItemView, itembean: ReputationBaoModel.dataModel) {
        view.setData(itembean)
    }

}