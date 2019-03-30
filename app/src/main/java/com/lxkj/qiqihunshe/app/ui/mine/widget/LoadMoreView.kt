package com.lxkj.qiqihunshe.app.ui.mine.widget

import android.content.Context
import android.view.View
import android.widget.RelativeLayout
import com.lxkj.qiqihunshe.R
import kotlinx.android.synthetic.main.item_loadmore.view.*

/**
 * Create Slingge by 2018/12/13 0013
 */
class LoadMoreView : RelativeLayout {

    constructor(context: Context) : super(context) {

    }


    init {
        View.inflate(context, R.layout.item_loadmore, this)
    }

    fun Loaded() {
        fl_load.visibility = View.VISIBLE
        progress.visibility = View.GONE
        text.text = "已经到底了"
    }

    fun Done() {
        fl_load.visibility = View.GONE
    }

    fun loading() {
        progress.visibility = View.VISIBLE
        text.text = "加载中"
    }

}