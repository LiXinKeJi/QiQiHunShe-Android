package com.lxkj.qiqihunshe.app.ui.mine.adapter

import android.content.Context
import com.lxkj.qiqihunshe.app.base.BaseListAdapter
import com.lxkj.qiqihunshe.app.ui.mine.model.ActivityRecordModel
import com.lxkj.qiqihunshe.app.ui.mine.widget.ActivityRecordItemView

/**
 * 活动记录
 * Created by Slingge on 2019/2/22
 */
class ActivityRecordAdapter : BaseListAdapter<ActivityRecordModel, ActivityRecordItemView>() {


    override fun getitemView(context: Context): ActivityRecordItemView {
        return ActivityRecordItemView(context)
    }

    override fun refreshItemView(view: ActivityRecordItemView, itembean: ActivityRecordModel) {
        view.setData(itembean)
    }


}