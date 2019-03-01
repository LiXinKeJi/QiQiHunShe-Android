package com.lxkj.qiqihunshe.app.ui.xiaoxi.adapter

import android.content.Context
import com.lxkj.qiqihunshe.app.base.BaseListAdapter
import com.lxkj.qiqihunshe.app.ui.xiaoxi.model.LookResultModel
import com.lxkj.qiqihunshe.app.ui.xiaoxi.model.MessageModel
import com.lxkj.qiqihunshe.app.ui.xiaoxi.viewmodel.LookupResultViewModel
import com.lxkj.qiqihunshe.app.ui.xiaoxi.widget.LookupResultItemView
import com.lxkj.qiqihunshe.app.ui.xiaoxi.widget.MessageItemView

/**
 * Created by Slingge on 2019/3/1
 */
class LookupResultAdapter : BaseListAdapter<LookResultModel, LookupResultItemView>() {


    override fun getitemView(context: Context): LookupResultItemView {
        return LookupResultItemView(context)
    }

    override fun refreshItemView(view: LookupResultItemView, itembean: LookResultModel) {
        view.setData(itembean)
    }


}