package com.lxkj.qiqihunshe.app.ui.mine.widget

import android.content.Context
import android.view.View
import android.widget.RelativeLayout
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.ui.mine.model.ActivityRecordModel
import com.lxkj.qiqihunshe.app.ui.mine.model.CommentRecordModel
import com.lxkj.qiqihunshe.app.ui.mine.model.QiQiBlackListModel

/**
 * Created by Slingge on 2019/2/21
 */
class QiQiBlackListItemView : RelativeLayout  {


    constructor(context: Context) : super(context)

    /**
     * 初始化方法
     * */
    init {
        View.inflate(context, R.layout.item_blacklist, this)
    }

    fun setData(bean: QiQiBlackListModel) {


    }


}