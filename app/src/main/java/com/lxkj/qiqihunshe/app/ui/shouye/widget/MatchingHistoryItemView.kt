package com.lxkj.qiqihunshe.app.ui.shouye.widget

import android.content.Context
import android.view.View
import android.widget.RelativeLayout
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.ui.shouye.model.MatchingHistoryModel
import kotlinx.android.synthetic.main.item_matching_history.view.*

/**
 * Created by Slingge on 2019/2/21
 */
class MatchingHistoryItemView : RelativeLayout {

    private var type = -1//2配，显示匹配度

    constructor(context: Context, type: Int) : super(context) {
        this.type = type
    }

    /**
     * 初始化方法
     * */
    init {
        View.inflate(context, R.layout.item_matching_history, this)
    }

    fun setData(bean: MatchingHistoryModel) {
        if (type == 2) {
            tv_degree.visibility = View.VISIBLE
        }

    }


}