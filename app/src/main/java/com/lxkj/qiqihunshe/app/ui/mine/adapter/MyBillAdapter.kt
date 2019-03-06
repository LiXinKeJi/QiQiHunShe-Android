package com.lxkj.qiqihunshe.app.ui.mine.adapter

import android.content.Context
import com.lxkj.qiqihunshe.app.base.BaseListAdapter
import com.lxkj.qiqihunshe.app.ui.mine.model.MyBillModel
import com.lxkj.qiqihunshe.app.ui.mine.widget.MyBillItemView

/**
 * 活动记录
 * Created by Slingge on 2019/2/22
 */
class MyBillAdapter : BaseListAdapter<MyBillModel.dataModel, MyBillItemView>() {


    override fun getitemView(context: Context): MyBillItemView {
        return MyBillItemView(context)
    }

    override fun refreshItemView(view: MyBillItemView, itembean: MyBillModel.dataModel) {
        view.setData(itembean)
    }


}