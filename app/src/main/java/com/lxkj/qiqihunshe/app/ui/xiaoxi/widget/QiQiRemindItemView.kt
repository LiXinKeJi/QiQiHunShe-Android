package com.lxkj.qiqihunshe.app.ui.xiaoxi.widget

import android.content.Context
import android.view.View
import android.widget.RelativeLayout
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.ui.xiaoxi.activity.MsgDetailsActivity
import com.lxkj.qiqihunshe.app.ui.xiaoxi.model.QiQiRemindModel
import com.lxkj.qiqihunshe.app.util.ToastUtil
import kotlinx.android.synthetic.main.item_remind.view.*

/**
 * Created by Slingge on 2019/2/21
 */
class QiQiRemindItemView : RelativeLayout {


    constructor(context: Context) : super(context)

    /**
     * 初始化方法
     * */
    init {
        View.inflate(context, R.layout.item_remind, this)
    }

    fun setData(bean: QiQiRemindModel) {

        cl_message.setOnClickListener {
            MyApplication.openActivity(context, MsgDetailsActivity::class.java)
        }

    }


}